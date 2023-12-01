package musicanalysis.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class AnalysisStatus
{
	private static Image defaultIcon;
	private static Image tickIcon;
	private static Image crossIcon;

	private Button analyseButton;
	private ImageView statusIcon;
	private Label statusLabel;

	static
	{
		try
		{
			defaultIcon = new Image(GUI.class.getResource("background_icon.png").toString());
			tickIcon = new Image(GUI.class.getResource("tick_icon.png").toString());
			crossIcon = new Image(GUI.class.getResource("cross_icon.png").toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public AnalysisStatus(Button analyseButton, ImageView statusIcon, Label statusLabel)
	{
		this.analyseButton = analyseButton;
		this.statusIcon = statusIcon;
		this.statusLabel = statusLabel;
		initialise(analyseButton, statusIcon);
	}

	public void initialise(Button analyseButton, ImageView statusIcon)
	{
		statusIcon.setImage(defaultIcon);
		statusIcon.setFitWidth(25);
		statusIcon.setPreserveRatio(true);
		statusIcon.setCache(true);
		analyseButton.setDisable(true);	
	}

	public void setNotReady()
	{
		statusIcon.setImage(defaultIcon);
		analyseButton.setDisable(true);
		statusLabel.setText("Source Separation Required");
	}

	public void setReady()
	{
		statusIcon.setImage(defaultIcon);
		analyseButton.setDisable(false);
		statusLabel.setText("Ready");
	}

	public void setFailed()
	{
		statusIcon.setImage(crossIcon);
		analyseButton.setDisable(false);
		statusLabel.setText("Analysis Failed");
	}

	public void setComplete()
	{
		statusIcon.setImage(tickIcon);
		analyseButton.setDisable(true);
		statusLabel.setText("Analysis Complete");
	}
}