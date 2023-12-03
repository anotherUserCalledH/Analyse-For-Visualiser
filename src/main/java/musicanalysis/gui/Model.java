package musicanalysis.gui;

import musicanalysis.io.LoadFile;
import musicanalysis.io.LoadData;
import musicanalysis.io.SavedSong;
import musicanalysis.AnalyseMusic;
import musicanalysis.SeparateTracks;

import be.tarsos.dsp.pitch.PitchProcessor;

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
		savedSongs = FXCollections.observableArrayList();//new ArrayList<SavedSong>());
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
			setSelectedSong(currentSavedSong);
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

	public void analysePitch(PitchProcessor.PitchEstimationAlgorithm pitchAlgorithm)
	{
		Path selectedSongFile = selectedSong.getSongFile();
		int[] pitchData = AnalyseMusic.detectPitch(selectedSongFile, pitchAlgorithm);

		Path pitchDataFile = selectedSong.getPitchDataFile();
		LoadData.writePitchData(pitchData, pitchDataFile);
		selectedSong.setHasPitchData(true);
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
			selectedSong.setHasBeatData(true);
			detectionSuccessful = true;
		}

		return detectionSuccessful;
	}

	public float[] getBeatData()
	{
		float[] beatData = null;

		if(selectedSong.checkHasBeatData())
		{
			Path beatDataFile = selectedSong.getBeatDataFile();
			beatData = LoadData.readBeatData(beatDataFile);
		}

		return beatData;
	}
}
