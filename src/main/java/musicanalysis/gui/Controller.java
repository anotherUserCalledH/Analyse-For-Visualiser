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


import java.io.File;
import java.util.ArrayList;

public class Controller
{

	@FXML
	private Label fileNameLabel;

	@FXML
	private VBox sourceSeparationMenu;

	@FXML
	private ArrayList<Button> analyseButtons;

	@FXML
	private ListView<SavedSong> savedSongsListView;

	private ObservableList<SavedSong> savedSongs;

	private Model model1 = new Model();

	public void initialize()
	{
		model1.populateLoadList();
		savedSongs = FXCollections.observableArrayList(model1.getSavedSongs());
		savedSongsListView.setItems(savedSongs);

		savedSongsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SavedSong>()
		{
			@Override
			public void changed(ObservableValue<? extends SavedSong> observable, SavedSong oldValue, SavedSong newValue)
			{
			if (newValue != null)
			{
				SavedSong selectedSong = savedSongsListView.getSelectionModel().getSelectedItem();
				setFileNameLabel(selectedSong.toString());
				enableAnalyseButtons();
				showSourceSeparationMenu();
			}
            }
        });
	}

	private void setFileNameLabel(String labelString)
	{
		fileNameLabel.setText("Chosen Song: " + labelString);
	}

	private void enableAnalyseButtons()
	{
		for(Button analysisButton : analyseButtons)
		{
			analysisButton.setDisable(false);
		}
	}

	private void showSourceSeparationMenu()
	{
		sourceSeparationMenu.setVisible(true);
	}

	@FXML
	private void chooseFile(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null)
		{
			model1.setChosenFile(selectedFile);
			setFileNameLabel(selectedFile.getName());
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
		model1.analyseBeat();
	}
}
