package musicanalysis.gui;

//import be.tarsos.dsp.pitch.PitchProcessor;
import musicanalysis.gui.loadsong.LoadSongComponent;
import musicanalysis.gui.panels.BeatAnalysisPanel;
import musicanalysis.gui.panels.PitchAnalysisPanel;
import musicanalysis.gui.panels.OnsetAnalysisPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
//import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
//import javafx.beans.property.ReadOnlyDoubleProperty;
//import javafx.application.Platform;

import java.io.File;
//import java.lang.Process;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
//import javafx.concurrent.Task;

public class MainController
{

	@FXML
	private Label fileNameLabel;

	@FXML
	private Button confirmButton;

	@FXML
	private VBox sourceSeparationMenu;

	@FXML
	private Button sourceSeparationButton;

	@FXML
	private ProgressBar separationProgressBar;

	@FXML
	private BeatAnalysisPanel beatPanel;

	@FXML
	private PitchAnalysisPanel pitchPanel;

	@FXML
	private OnsetAnalysisPanel onsetPanel;

	@FXML
	private LoadSongComponent loadSongComponent;

	private File chosenFile;

	private Label separationProgressLabel;

	private Model model1 = new Model();


	public void initialize()
	{
		initialisePanels();

		separationProgressLabel = new Label("Source Separation Successful! :)");
		separationProgressBar.setProgress(0);
	}

	private void setFileNameLabel(String labelString)
	{
		fileNameLabel.setText("Chosen Song: " + labelString);
	}

	private void updatePanelSong(SavedSong selectedSong)
	{
		beatPanel.setSelectedSong(selectedSong);
		pitchPanel.setSelectedSong(selectedSong);
		onsetPanel.setSelectedSong(selectedSong);
	}

	private void updatePanelStatus()
	{
		beatPanel.updateAnalysisStatus();
		pitchPanel.updateAnalysisStatus();
		onsetPanel.updateAnalysisStatus();
	}

	private void initialisePanels()
	{
		beatPanel.bindPanel(onsetPanel.getBindingProperty());
		pitchPanel.bindPanel(beatPanel.getBindingProperty());
		onsetPanel.bindPanel(pitchPanel.getBindingProperty());

		beatPanel.setPluginDirectory(model1.getPluginDirectory());
		pitchPanel.setPluginDirectory(model1.getPluginDirectory());
		onsetPanel.setPluginDirectory(model1.getPluginDirectory());
	}

	@FXML
	private void selectFile(ActionEvent event)
	{
		SavedSong selectedSong = loadSongComponent.getSelectedSong();
		updatePanelSong(selectedSong);
	}

	@FXML
	private void chooseNewFile(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		chosenFile = fileChooser.showOpenDialog(null);

		if (chosenFile != null)
		{
			setFileNameLabel(chosenFile.getName());
			confirmButton.setDisable(false);
		}
		else { confirmButton.setDisable(true); }
	}

	@FXML
	private void confirmFile(ActionEvent event)
	{
		SavedSong newSong = model1.saveFile(chosenFile);

		if(newSong != null)
		{
			loadSongComponent.addNewSong(newSong);
			updatePanelSong(newSong);
		}
	}

	@FXML
	private void beginSourceSeparation(ActionEvent event)
	{
		final SavedSong SONG_FOR_SEPARATION = loadSongComponent.getSelectedSong();
		ProgressUpdater sourceSeparationTask = model1.runSourceSeparation(SONG_FOR_SEPARATION);

		if(sourceSeparationTask != null)
		{
			separationProgressBar.progressProperty().bind(sourceSeparationTask.progressProperty());
			sourceSeparationTask.setOnSucceeded(new EventHandler<WorkerStateEvent>()
			{
				@Override
				public void handle(WorkerStateEvent succeeded)
				{
					Model.checkSourceSeparation(SONG_FOR_SEPARATION);
					updatePanelStatus();
				}
			});

			new Thread(sourceSeparationTask).start();
		}
	}
}