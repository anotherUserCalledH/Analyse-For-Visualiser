package musicanalysis.gui;

import musicanalysis.structure.ManageDirectories;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;

import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.IOException;

public class Model
{
	private Path dataDirectory;

	private Path demucsPath;


	public Model()
	{
		dataDirectory = ManageDirectories.DATA_DIRECTORY;
		demucsPath = ManageDirectories.DEMUCS_DIRECTORY;
	}

	public SavedSong saveFile(File chosenFile)
	{
		Path chosenFilePath = chosenFile.toPath();
		String fullFileName = chosenFilePath.getFileName().toString();
		String[] splitFileName = fullFileName.split("\\.");

		String fileName = splitFileName[0];
		Path storageDirectory = ManageDirectories.getDirectory(dataDirectory, fileName);
		Path savedFile = ManageDirectories.storeFile(chosenFilePath, storageDirectory, fullFileName);

		SavedSong newSong = null;
		if(savedFile != null)
		{
			newSong = new SavedSong(fileName, storageDirectory, savedFile);
		}
		else
		{
			System.err.println("Error with file");
		}

		return newSong;
	}

	private Process buildDemucsProcess(Path demucsPath, Path inputPath, Path outputPath)
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

	public ProgressUpdater runSourceSeparation(SavedSong songForSeparation)
	{
		Path selectedSongFile = songForSeparation.getSongFile();
		ProgressUpdater task = null;

		if(Files.exists(demucsPath))
		{
			Process demucs = buildDemucsProcess(demucsPath, selectedSongFile, dataDirectory);
			task = new ProgressUpdater(demucs);
		}
		else
		{
			System.err.println("Demucs source separation model not found.");
		}

		return task;
	}

	public static void checkSourceSeparation(SavedSong songForSeparation)
	{
		Path vocalsFile = songForSeparation.getVocalsFile();
		if(Files.exists(vocalsFile))
		{
			songForSeparation.setHasSeparatedAudio(true);
		}
	}
}
