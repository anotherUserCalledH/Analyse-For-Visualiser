package musicanalysis.gui;

import musicanalysis.io.LoadFile;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.util.List;

import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.IOException;

public class Model
{
	private Path dataDirectory;
	private Path demucsPath;
	private Path pluginDirectory;

	private ObservableList<SavedSong> savedSongs;
	private SavedSong selectedSong;

	public Model()
	{
		savedSongs = FXCollections.observableArrayList();
		Path currentDirectory = Paths.get(System.getProperty("user.dir"));
		this.dataDirectory = LoadFile.getDirectory(currentDirectory, "data");
		this.pluginDirectory = LoadFile.getDirectory(currentDirectory, "plugins");
		this.demucsPath = pluginDirectory.resolve("demucs/RunDemucs.exe");
		if(!Files.exists(demucsPath)){ this.demucsPath = null; }
	}

	public Path getPluginDirectory()
	{
		return pluginDirectory;
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

	private Process separateAudioFile(Path demucsPath, Path inputPath, Path outputPath)
	{
		String demucsPathString = demucsPath.toAbsolutePath().toString();
		String inputPathString = inputPath.toAbsolutePath().toString();
		String outputPathString = outputPath.toAbsolutePath().toString();
		String outputCommand = "-o" + outputPathString;

		ProcessBuilder demucsPB = new ProcessBuilder();
		demucsPB.command(demucsPathString, inputPathString, outputCommand);

		Process demucs = null;		

		try
		{
			demucs = demucsPB.start();
		}		
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return demucs;
	}

	public Process runSourceSeparation()
	{
		Path selectedSongFile = selectedSong.getSongFile();
		Process demucs = null;

		if(demucsPath != null)
		{

			demucs = separateAudioFile(demucsPath, selectedSongFile, dataDirectory);
		}
		else
		{
			System.err.println("Demucs source separation model not found.");
		}

		return demucs;
	}

	public static void checkSourceSeparation(SavedSong songForSeparation)
	{
		Path vocalsFile = songForSeparation.getVocalsFile();
		boolean separationSuccessful = false;
		if(Files.exists(vocalsFile))
		{
			separationSuccessful = true;
			songForSeparation.setHasSeparatedAudio(true);
		}
	}
}
