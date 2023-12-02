package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;

public class PreviewVisualiser extends MusicVisualiser
{
	VisualiserEffect visualiserEffect1;

	public PreviewVisualiser(GraphicsContext context, int canvasWidth, int canvasHeight)
	{
		super(context, canvasWidth, canvasHeight, 32);
	}

	public void pauseVisualiser()
	{
		timeline1.pause();
		mediaPlayer1.pause();
	}

	public void setVisualiserEffect(VisualiserEffect visualiserEffect1)
	{
		this.visualiserEffect1 = visualiserEffect1;
	} 

	@Override
	public void createGraphics(float audioTime)
	{
		visualiserEffect1.update(audioTime);
	}
}