package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class BeatMarker extends TimestampMarker
{
	public BeatMarker(GraphicsContext context, int canvasWidth, int canvasHeight)
	{
		super(context, canvasWidth, canvasHeight);

		float location1 = canvasWidth/4f;
		float location2 = 3*location1; 
		locationsX = new float[] { location1, location2, location2, location1 };
		locationsY = new float[] { location1, location1, location2, location2 };
	}
}