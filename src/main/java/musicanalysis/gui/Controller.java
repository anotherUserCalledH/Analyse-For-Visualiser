package musicanalysis.gui;

import musicanalysis.io.SavedSong;
import musicanalysis.preview.PreviewType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.ProgressBar;

import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;

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
	private Button analyseBeatButton;

	@FXML
	private Button analysePitchButton;

	@FXML
	private Button analyseOnsetButton;

	@FXML
	private ImageView beatStatusIcon;

	@FXML
	private ImageView pitchStatusIcon;

	@FXML
	private ImageView onsetStatusIcon;

	@FXML
	private Label beatStatusLabel;

	@FXML
	private Label pitchStatusLabel;

	@FXML
	private Label onsetStatusLabel;

	private File chosenFile;
	private Label separationProgressLabel;
	private AnalysisStatus beatStatus;
	private AnalysisStatus pitchStatus;
	private AnalysisStatus onsetStatus;

	private Model model1 = new Model();


	public void initialize()
	{
		beatStatus = new AnalysisStatus(analyseBeatButton, beatStatusIcon, beatStatusLabel);
		pitchStatus = new AnalysisStatus(analysePitchButton, pitchStatusIcon, pitchStatusLabel);
		onsetStatus = new AnalysisStatus(analyseOnsetButton, onsetStatusIcon, onsetStatusLabel);

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
					updateAnalysisStatus(selectedSong);
				}
            }
        });
	}

	private void setFileNameLabel(String labelString)
	{
		fileNameLabel.setText("Chosen Song: " + labelString);
	}

	private void updateAnalysisStatus(SavedSong selectedSong)
	{
		boolean sourceSeparated = false;

		if(selectedSong.checkHasBeatData() == true)
		{
			beatStatus.setComplete();
		}
		else
		{
			beatStatus.setReady();
		}

		if(selectedSong.checkHasSeparatedAudio() == true)
		{
			sourceSeparationMenu.getChildren().clear();
			sourceSeparationMenu.getChildren().add(separationProgressLabel);
			sourceSeparationButton.setDisable(true);

			if(selectedSong.checkHasPitchData() == true)
			{
				pitchStatus.setComplete();
			}
			else
			{
				pitchStatus.setReady();
			}
			// if(selectedSong.checkHasOnsetData() == true) { OnsetStatusIcon.setImage(tickIconImage); }
			// else { analyseOnsetButton.setDisable(false); }
		}
		else
		{
			sourceSeparationMenu.getChildren().clear();
			sourceSeparationMenu.getChildren().add(separationProgressBar);
			sourceSeparationButton.setDisable(false);

			pitchStatus.setNotReady();
		}
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
			updateAnalysisStatus(chosenSong);
			model1.setSelectedSong(chosenSong);
		}
		else
		{
			System.err.println("Error with file");
		}
	}

	@FXML
	private void analysePitch(ActionEvent event)
	{
		model1.analysePitch();
	}

	@FXML
	private void analyseBeat(ActionEvent event)
	{
		boolean detectionSuccessful = model1.analyseBeat();
		if(detectionSuccessful) { beatStatus.setComplete(); }
		else { beatStatus.setFailed(); }
	}

	@FXML
	private void beginSourceSeparation(ActionEvent event)
	{
		boolean detectionSuccessful = model1.runSourceSeparation();
		if(detectionSuccessful)
		{
			updateAnalysisStatus(model1.getSelectedSong());
		}
	}

	@FXML
	private void launchBeatPreview(ActionEvent event)
	{
		Path songPath = model1.getSelectedSong().getSongFile();
		float[] beatData = model1.getBeatData();

		try
		{
			LaunchPreviewWindow.launch(songPath, PreviewType.BEAT, beatData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
