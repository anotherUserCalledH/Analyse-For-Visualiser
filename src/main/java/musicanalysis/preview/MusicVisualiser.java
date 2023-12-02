package musicanalysis.preview;

import javafx.scene.canvas.GraphicsContext;

//Packages for animation
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

//Packages for input/output
import java.nio.file.Path;

//Packages for playing audio
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public abstract class MusicVisualiser
{
	protected final int WIDTH;
	protected final int HEIGHT;

	protected double frameLengthSeconds;

	protected GraphicsContext context;
	protected Timeline timeline1;

	protected MediaPlayer mediaPlayer1 = null;

	public MusicVisualiser(GraphicsContext context, int canvasWidth, int canvasHeight, int idealFrameRate)
	{
		this.context = context;
		this.WIDTH = canvasWidth;
		this.HEIGHT = canvasHeight;
		this.frameLengthSeconds = 1d/idealFrameRate;

		this.timeline1 = new Timeline();
		timeline1.setCycleCount(Animation.INDEFINITE);
		KeyFrame animationFrame = new KeyFrame(Duration.seconds(frameLengthSeconds), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				float audioTime = (float) (mediaPlayer1.getCurrentTime()).toSeconds();
				createGraphics(audioTime);
			}
		});
		timeline1.getKeyFrames().add(animationFrame);
	}

	public void setSong(Path inputPath)
	{
		try
		{
			Media audioFile = new Media(inputPath.toUri().toString());
			mediaPlayer1 = new MediaPlayer(audioFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void playVisualiser()
	{
		mediaPlayer1.play();
		timeline1.play();
	}
	
	public abstract void createGraphics(float audioTime);
}