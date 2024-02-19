package musicanalysis.gui.panels;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.beans.property.ReadOnlyDoubleProperty;

import musicanalysis.gui.SavedSong;

import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.gui.panels.model.PanelModel;

import java.nio.file.Path;

public abstract class AnalysisPanel<T extends AnalysisAlgorithm> extends HBox
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
	protected ChoiceBox<T> algorithmsChoiceBox;

	@FXML
	protected Button analyseButton;

	@FXML
	protected Button optionsButton;

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

	@FXML
	protected ContextMenu options;

	protected PanelModel<T> placeholderModel;

	private String panelName;

	protected ReadOnlyDoubleProperty[] bindingProperty;

	public AnalysisPanel(PanelModel<T> childModel, String panelName) throws Exception
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("analysis_panel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
		this.placeholderModel = childModel;
		this.panelName = panelName;
		initialiseChoiceBox();
	}

	public void initialize()
	{
		initialiseStatus();
		this.bindingProperty = new ReadOnlyDoubleProperty[] { column0.widthProperty(), column1.widthProperty() };
		headerLabel.setText(panelName);
	}

	protected void initialiseChoiceBox()
	{
		ObservableList<T> algorithms = placeholderModel.getAlgorithms();

		algorithmsChoiceBox.setItems(algorithms);

		if(!algorithms.isEmpty())
		{
			algorithmsChoiceBox.getSelectionModel().select(algorithms.get(0));
			placeholderModel.setChosenAlgorithm(algorithms.get(0));
		}

		algorithmsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AnalysisAlgorithm>()
		{
			@Override
			public void changed(ObservableValue<? extends AnalysisAlgorithm> observable, AnalysisAlgorithm oldValue, AnalysisAlgorithm newValue)
			{
				if (newValue != null)
				{
					placeholderModel.setChosenAlgorithm(algorithmsChoiceBox.getSelectionModel().getSelectedItem());
				}
			}
		});
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

	public ReadOnlyDoubleProperty[] getBindingProperty()
	{
		return bindingProperty;
	}

	public void bindPanel(ReadOnlyDoubleProperty[] partnerBindingProperty)
	{
		column0.minWidthProperty().bind(partnerBindingProperty[0]);
		column1.minWidthProperty().bind(partnerBindingProperty[1]);
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
	protected void analyse(ActionEvent event)
	{
		boolean analysisSuccessful = placeholderModel.analyse();
		if(analysisSuccessful) { setComplete(); }
		else { setFailed(); }
	}

	@FXML
	protected void preview(ActionEvent event)
	{
		AnalysisData dataToPreview = placeholderModel.getAnalysisData();
		Path songPath = placeholderModel.getSelectedSong().getSongFile();
		buildPreview(dataToPreview, songPath);
	}

	@FXML
	protected void showOptions(ActionEvent event)
	{
		options.show(optionsButton, Side.BOTTOM, 0, 0);
	}
	@FXML
	protected void evaluate(ActionEvent event)
	{

	}

	public void setSelectedSong(SavedSong selectedSong)
	{
		placeholderModel.setSelectedSong(selectedSong);
		updateAnalysisStatus(selectedSong);
	}

	protected abstract void buildPreview(AnalysisData dataToPreview, Path songPath);

	public abstract void updateAnalysisStatus(SavedSong selectedSong);
}
