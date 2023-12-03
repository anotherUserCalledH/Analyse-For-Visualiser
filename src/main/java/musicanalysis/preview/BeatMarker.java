package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class BeatMarker implements VisualiserEffect
{
	protected final int WIDTH;
	protected final int HEIGHT;

	private float[] beatTimes;
	private int beatIndex;

	private int noLocations = 4;
	private int locationIndex;
	private float[] locationsX;
	private float[] locationsY;

	private GraphicsContext context;

	private float radius = 20;
	private Color markerColour = Color.RED;
	private Color backgroundColour = Color.BLACK;

	private Image backgroundImage = null;

	public BeatMarker(GraphicsContext context, int canvasWidth, int canvasHeight)
	{
		this.context = context;
		this.WIDTH = canvasWidth;
		this.HEIGHT = canvasHeight;
		this.beatTimes = beatTimes;

		float location1 = canvasWidth/4f;
		float location2 = 3*location1; 
		this.locationsX = new float[] { location1, location2, location2, location1 };
		this.locationsY = new float[] { location1, location1, location2, location2 };

		this.beatIndex = 0;
		this.locationIndex = 0;

		try
		{
			backgroundImage = new Image(BeatMarker.class.getResource("background.png").toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setData(float[] beatTimes)
	{
		this.beatTimes = beatTimes;
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
		if(beatIndex < beatTimes.length)
		{
			if(audioTime > beatTimes[beatIndex])
			{
				refreshCanvas();

				drawMarker(locationsX[locationIndex], locationsY[locationIndex]);

				locationIndex++;
				if(locationIndex >= noLocations){locationIndex = 0;}

				while(audioTime > beatTimes[beatIndex])
				{
					beatIndex++;
					if(beatIndex >= beatTimes.length)
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