package musicanalysis.gui;

import musicanalysis.io.LoadFile;
import musicanalysis.io.LoadData;
import musicanalysis.io.SavedSong;
import musicanalysis.AnalyseMusic;
import musicanalysis.SeparateTracks;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.List;
import java.net.URL;


public class Model
{
	private Path dataDirectory;
	private Path demucsPath;

	private ObservableList<SavedSong> savedSongs;
	private SavedSong selectedSong;

	public Model()
	{
		savedSongs = FXCollections.observableArrayList(new ArrayList<SavedSong>());
		Path currentDirectory = Paths.get(System.getProperty("user.dir"));
		this.dataDirectory = LoadFile.getDirectory(currentDirectory,"data");

		try
		{
			URL demucsPathURL = SeparateTracks.class.getResource("RunDemucs/RunDemucs.exe");
			this.demucsPath = Paths.get(demucsPathURL.toURI());
			if(!Files.exists(demucsPath)){ this.demucsPath = null; }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public SavedSong getSelectedSong()
	{
		return selectedSong;
	}

	public void setSelectedSong(SavedSong selectedSong)
	{
		this.selectedSong = selectedSong;
	}

	public ObservableList<SavedSong> getSavedSongs()
	{
		List<Path> storageDirectories = LoadFile.listStorageDirectories(dataDirectory);
		for(Path storageDirectory : storageDirectories)
		{
			String songName = storageDirectory.getFileName().toString();
			Path songFile = LoadFile.findSongFile(storageDirectory);

			if(songFile != null)
			{
				SavedSong currentSavedSong = new SavedSong(songName, storageDirectory, songFile);
				savedSongs.add(currentSavedSong);
			}
		}

		return savedSongs;
	}

	public boolean saveFile(File chosenFile)
	{
		Path chosenFilePath = chosenFile.toPath();
		String fullFileName = chosenFilePath.getFileName().toString();
		String[] splitFileName = fullFileName.split("\\.");

		String fileName = splitFileName[0];
		Path storageDirectory = LoadFile.getDirectory(dataDirectory, fileName);
		Path savedFile = LoadFile.storeFile(chosenFilePath, storageDirectory, fullFileName);

		boolean saveSuccessful = false;
		if(savedFile != null)
		{
			SavedSong currentSavedSong = new SavedSong(fileName, storageDirectory, savedFile);
			savedSongs.add(currentSavedSong);
			saveSuccessful = true;
		}


		return saveSuccessful;
	}

	public boolean runSourceSeparation()
	{
		Path selectedSongFile = selectedSong.getSongFile();
		boolean separationSuccessful = false;

		if(demucsPath != null)
		{
			separationSuccessful = SeparateTracks.separateAudioFile(demucsPath, selectedSongFile, dataDirectory);
			if(!separationSuccessful)
			{
				System.err.println("Error in source separation");
			}
			else
			{
				selectedSong.setHasSeparatedAudio(true);
			}
		}
		else
		{
			System.err.println("Demucs source separation model not found.");
		}

		return separationSuccessful;
	}

	public void analysePitch()
	{
		// saveFile();
		// int[] pitchData = AnalyseMusic.analysePitch(savedFile);
		// pitchDataFile = storageDirectory.resolve("pitchData.csv");
		// LoadData.writePitchData(pitchData, pitchDataFile);
	}

	public boolean analyseBeat()
	{
		Path selectedSongFile = selectedSong.getSongFile();
		float[] beatData = AnalyseMusic.detectBeat(selectedSongFile);
		boolean detectionSuccessful = false;

		if(beatData != null)
		{
			Path beatDataFile = selectedSong.getBeatDataFile();
			LoadData.writeBeatData(beatData, beatDataFile);
			//Should probably in boolean return value to LoadData to check for errors
			selectedSong.setBeatDataFile(beatDataFile);
			detectionSuccessful = true;
		}

		return detectionSuccessful;
	}
}
