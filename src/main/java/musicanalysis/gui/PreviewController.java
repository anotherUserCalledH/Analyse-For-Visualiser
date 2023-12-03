package musicanalysis.gui;

import musicanalysis.preview.PreviewVisualiser;

import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class PreviewController
{
	@FXML
	private Button playPauseButton;

	private boolean isPaused;

	private PreviewVisualiser previewVisualiser1;

	public PreviewController()
	{
		isPaused = false;
	}

	@FXML
	private void togglePlayPause()
	{
		if(isPaused)
		{
			previewVisualiser1.playVisualiser();
			isPaused = false;
			playPauseButton.setText("Pause");
		}
		else
		{
			previewVisualiser1.pauseVisualiser();
			isPaused = true;
			playPauseButton.setText("Play");
		}
	}

	public void setPreviewVisualiser(PreviewVisualiser previewVisualiser1)
	{
		this.previewVisualiser1 = previewVisualiser1;
	}
}