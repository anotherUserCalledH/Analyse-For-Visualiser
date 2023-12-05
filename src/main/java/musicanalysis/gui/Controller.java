package musicanalysis.gui;

import be.tarsos.dsp.pitch.PitchProcessor;
import musicanalysis.gui.panels.BeatAnalysisPanel;
import musicanalysis.gui.panels.PitchAnalysisPanel;
import musicanalysis.gui.panels.OnsetAnalysisPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.application.Platform;

import java.io.File;
import java.lang.Process;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.concurrent.Task;

public class Controller
{

	@FXML
	private Label fileNameLabel;

	@FXML
	private Button confirmButton;

	@FXML
	private ListView<SavedSong> savedSongsListView;

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

	private File chosenFile;

	private Label separationProgressLabel;

	private Model model1 = new Model();


	public void initialize()
	{
		beatPanel.bindPanel(onsetPanel.getBindingProperty());
		pitchPanel.bindPanel(beatPanel.getBindingProperty());
		onsetPanel.bindPanel(pitchPanel.getBindingProperty());

		separationProgressLabel = new Label("Source Separation Successful! :)");
		separationProgressBar.setProgress(0);

		savedSongsListView.setItems(model1.getSavedSongs());

		savedSongsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SavedSong>()
		{
			@Override
			public void changed(ObservableValue<? extends SavedSong> observable, SavedSong oldValue, SavedSong newValue)
			{
			if (newValue != null)
				{
					SavedSong selectedSong = savedSongsListView.getSelectionModel().getSelectedItem();
					model1.setSelectedSong(selectedSong);
					beatPanel.setSelectedSong(selectedSong);
					pitchPanel.setSelectedSong(selectedSong);
					onsetPanel.setSelectedSong(selectedSong);
				}
			}
		});
	}

	private void setFileNameLabel(String labelString)
	{
		fileNameLabel.setText("Chosen Song: " + labelString);
	}

	@FXML
	private void chooseFile(ActionEvent event)
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
		boolean saveSuccessful = model1.saveFile(chosenFile);

		if(saveSuccessful)
		{
			SavedSong chosenSong = model1.getSelectedSong();
			beatPanel.setSelectedSong(chosenSong);
			pitchPanel.setSelectedSong(chosenSong);
			onsetPanel.setSelectedSong(chosenSong);
		}
		else
		{
			System.err.println("Error with file");
		}
	}

	@FXML
	private void beginSourceSeparation(ActionEvent event)
	{
		final SavedSong SONG_FOR_SEPARATION = model1.getSelectedSong();
		Process demucs = model1.runSourceSeparation();

		if(demucs != null)
		{
			ProgressUpdater task = new ProgressUpdater(demucs);
			separationProgressBar.progressProperty().bind(task.progressProperty());
			task.setOnSucceeded(new EventHandler<WorkerStateEvent>()
			{
				@Override
				public void handle(WorkerStateEvent succeeded)
				{
					model1.checkSourceSeparation(SONG_FOR_SEPARATION);
					pitchPanel.updateAnalysisStatus();
					onsetPanel.updateAnalysisStatus();
				}
			});

			new Thread(task).start();
		}
	}
}