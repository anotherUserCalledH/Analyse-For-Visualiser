package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class TimestampMarker implements VisualiserEffect
{
	protected final int WIDTH;
	protected final int HEIGHT;

	protected float[] timestamps;
	protected int timestampIndex;

	protected int noLocations = 4;
	protected int locationIndex;
	protected float[] locationsX;
	protected float[] locationsY;

	protected GraphicsContext context;

	protected float radius = 20;
	protected Color markerColour = Color.RED;
	protected Color backgroundColour = Color.BLACK;

	protected Image backgroundImage = null;

	public TimestampMarker(GraphicsContext context, int canvasWidth, int canvasHeight)
	{
		this.context = context;
		this.WIDTH = canvasWidth;
		this.HEIGHT = canvasHeight;
		this.timestamps = timestamps;

		float lineWidth = 1.5f;
		float diameter = 2 * radius;
		float locationY = canvasHeight/2f;
		float location1 = canvasWidth/12f + diameter + lineWidth;
		float location2 = location1 + diameter + lineWidth;
		float location3 = location2 + diameter + lineWidth;
		float location4 = location3 + diameter + lineWidth;

		this.locationsX = new float[] { location1, location2, location3, location4 };
		this.locationsY = new float[] { locationY, locationY, locationY, locationY };

		this.timestampIndex = 0;
		this.locationIndex = 0;

		try
		{
			backgroundImage = new Image(getClass().getResource("background.png").toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		refreshCanvas();
	}

	public void setData(float[] timestamps)
	{
		this.timestamps = timestamps;
	}

	public void refreshCanvas()
	{
		context.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT);

		// context.save();
		// context.setFill(backgroundColour);
		// context.fillRect(0, 0, WIDTH, HEIGHT);
		// context.restore();
	}

	public void drawMarker(float posX, float posY)
	{
		context.save();
		context.setFill(markerColour);
		context.translate(posX, posY);
		context.beginPath();
		context.arc(0, 0, radius, radius, 0, 360);
		context.fill();
		context.closePath();
		context.restore();
	}

	public void renderMarker(float audioTime)
	{
		if(timestampIndex < timestamps.length)
		{
			if(audioTime > timestamps[timestampIndex])
			{
				refreshCanvas();

				drawMarker(locationsX[locationIndex], locationsY[locationIndex]);

				locationIndex++;
				if(locationIndex >= noLocations){locationIndex = 0;}

				while(audioTime > timestamps[timestampIndex])
				{
					timestampIndex++;
					if(timestampIndex >= timestamps.length)
					{
						break;
					}
				}
			}
		}
	}

	@Override
	public void update(float audioTime)
	{
		renderMarker(audioTime);
	}
}