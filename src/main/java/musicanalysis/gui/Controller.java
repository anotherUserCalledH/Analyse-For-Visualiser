package musicanalysis.gui;

import musicanalysis.io.SavedSong;

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
import javafx.scene.text.Text;


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
	private ImageView beatStatusIcon;

	@FXML
	private ImageView pitchStatusIcon;

	@FXML
	private ImageView onsetStatusIcon;

	@FXML
	private Button analyseBeatButton;

	@FXML
	private Button analysePitchButton;

	@FXML
	private Button analyseOnsetButton;

	private File chosenFile;
	private Label separationProgressLabel;

	private Model model1 = new Model();

	private static Image backgroundIconImage;
	private static Image tickIconImage;
	private static Image crossIconImage;

	static
	{
		try
		{
			backgroundIconImage = new Image(GUI.class.getResource("background_icon.png").toString());
			tickIconImage = new Image(GUI.class.getResource("tick_icon.png").toString());
			crossIconImage = new Image(GUI.class.getResource("cross_icon.png").toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void initialize()
	{
		initialiseIcon(beatStatusIcon);
		initialiseIcon(pitchStatusIcon);
		initialiseIcon(onsetStatusIcon);

		initialiseButtons();

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
					updateAnalyseStatus(selectedSong);
				}
            }
        });
	}

	private void initialiseIcon(ImageView currentIcon)
	{
		currentIcon.setImage(backgroundIconImage);
		currentIcon.setFitWidth(25);
		currentIcon.setPreserveRatio(true);
		currentIcon.setCache(true);
	}

	private void initialiseButtons()
	{
		Text buttonSizeText = new Text("Complete");
		buttonSizeText.setFont(analyseBeatButton.getFont());
		double textWidth = buttonSizeText.getBoundsInLocal().getWidth();
		double widthWithPadding = textWidth + 20;
		analyseBeatButton.setMinWidth(widthWithPadding);
		analyseBeatButton.setMaxWidth(widthWithPadding);
		analysePitchButton.setMinWidth(widthWithPadding);
		analysePitchButton.setMaxWidth(widthWithPadding);
		analyseOnsetButton.setMinWidth(widthWithPadding);
		analyseOnsetButton.setMaxWidth(widthWithPadding);
	}

	private void setFileNameLabel(String labelString)
	{
		fileNameLabel.setText("Chosen Song: " + labelString);
	}

	private void updateAnalyseStatus(SavedSong selectedSong)
	{
		boolean sourceSeparated = false;

		if(selectedSong.checkHasBeatData() == true)
		{
			beatStatusIcon.setImage(tickIconImage);
			analyseBeatButton.setDisable(true);
			analyseBeatButton.setText("Complete");
		}
		else
		{
			beatStatusIcon.setImage(backgroundIconImage);
			analyseBeatButton.setDisable(false);
			analyseBeatButton.setText("Analyse");
		}

		if(selectedSong.checkHasSeparatedAudio() == true)
		{
			sourceSeparationMenu.getChildren().clear();
			sourceSeparationMenu.getChildren().add(separationProgressLabel);
			sourceSeparationButton.setDisable(true);

			if(selectedSong.checkHasPitchData() == true)
			{
				pitchStatusIcon.setImage(tickIconImage);
				analysePitchButton.setDisable(true);
				analysePitchButton.setText("Complete");
			}
			else
			{
				pitchStatusIcon.setImage(backgroundIconImage);
				analysePitchButton.setDisable(false);
				analysePitchButton.setText("Analyse");
			}
			// if(selectedSong.checkHasOnsetData() == true) { OnsetStatusIcon.setImage(tickIconImage); }
			// else { analyseOnsetButton.setDisable(false); }
		}
		else
		{
			sourceSeparationMenu.getChildren().clear();
			sourceSeparationMenu.getChildren().add(separationProgressBar);
			sourceSeparationButton.setDisable(false);
			separationProgressBar.setProgress(0);

			analysePitchButton.setDisable(true);
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
			updateAnalyseStatus(chosenSong);
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
		if(detectionSuccessful) { beatStatusIcon.setImage(tickIconImage); }
		else {beatStatusIcon.setImage(crossIconImage); }
	}

	@FXML
	private void beginSourceSeparation(ActionEvent event)
	{
		boolean detectionSuccessful = model1.runSourceSeparation();
		if(detectionSuccessful)
		{
			updateAnalyseStatus(model1.getSelectedSong());
		}
	}
}

