package musicanalysis.gui.panels;

import musicanalysis.gui.SavedSong;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

public abstract class AnalysisPanel extends HBox
{
	private static Image defaultIcon;
	private static Image tickIcon;
	private static Image crossIcon;
	private static Image noticeIcon;

	static
	{
		try
		{
			defaultIcon = new Image(AnalysisPanel.class.getResource("background_icon.png").toString());
			tickIcon = new Image(AnalysisPanel.class.getResource("tick_icon.png").toString());
			crossIcon = new Image(AnalysisPanel.class.getResource("cross_icon.png").toString());
			noticeIcon = new Image(AnalysisPanel.class.getResource("notice_icon.png").toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@FXML
	protected Label headerLabel;

	@FXML
	protected ChoiceBox algorithmsChoiceBox;

	@FXML
	protected Button analyseButton;

	@FXML
	protected ImageView statusIcon;

	@FXML
	protected Label statusLabel;

	@FXML
	protected Button previewButton;

	@FXML
	protected HBox column0;

	@FXML
	protected HBox column1;

	protected SavedSong selectedSong;
	protected ReadOnlyDoubleProperty[] bindingProperty;

	public AnalysisPanel() throws Exception
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("analysis_panel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		initialiseChoiceBox();
		initialiseStatus();
		this.bindingProperty = new ReadOnlyDoubleProperty[] { column0.widthProperty(), column1.widthProperty() };
	}

	protected void setHeaderLabel(String panelName)
	{
		headerLabel.setText(panelName);
	}

	public void setSelectedSong(SavedSong selectedSong)
	{
		this.selectedSong = selectedSong;
		updateAnalysisStatus();
	}

	protected abstract void initialiseChoiceBox();

	public abstract void updateAnalysisStatus();

	public ReadOnlyDoubleProperty[] getBindingProperty()
	{
		return bindingProperty;
	}

	public void bindPanel(ReadOnlyDoubleProperty[] partnerBindingProperty)
	{
		column0.minWidthProperty().bind(partnerBindingProperty[0]);
		column1.minWidthProperty().bind(partnerBindingProperty[1]);
	}

	protected void initialiseStatus()
	{
		statusIcon.setImage(defaultIcon);
		statusIcon.setFitWidth(25);
		statusIcon.setPreserveRatio(true);
		statusIcon.setCache(true);
		analyseButton.setDisable(true);
		previewButton.setVisible(false);
	}

	protected void setNotReady()
	{
		statusIcon.setImage(noticeIcon);
		analyseButton.setDisable(true);
		statusLabel.setText("Source Separation Required");
		previewButton.setVisible(false);
	}

	protected void setReady()
	{
		statusIcon.setImage(defaultIcon);
		analyseButton.setDisable(false);
		statusLabel.setText("Ready");
		previewButton.setVisible(false);
	}

	protected void setFailed()
	{
		statusIcon.setImage(crossIcon);
		analyseButton.setDisable(false);
		statusLabel.setText("Analysis Failed");
		previewButton.setVisible(false);
	}

	protected void setComplete()
	{
		statusIcon.setImage(tickIcon);
		analyseButton.setDisable(true);
		statusLabel.setText("Analysis Complete");
		previewButton.setVisible(true);
	}

	@FXML
	protected abstract void analyse(ActionEvent event);

	@FXML
	protected abstract void preview(ActionEvent event);
}
