package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;
import java.nio.file.Path;

public class PreviewVisualiser extends MusicVisualiser
{
	VisualiserEffect effect;

	public PreviewVisualiser(GraphicsContext context, int canvasWidth, int canvasHeight, Path songPath, VisualiserEffect effect)
	{
		super(context, canvasWidth, canvasHeight, 32);
		this.effect = effect;
		setSong(songPath);
	}

	public void pauseVisualiser()
	{
		timeline1.pause();
		mediaPlayer1.pause();
	}

	@Override
	public void createGraphics(float audioTime)
	{
		effect.update(audioTime);
	}
}