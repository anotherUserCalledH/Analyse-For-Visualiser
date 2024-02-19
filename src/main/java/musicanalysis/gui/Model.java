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

		//Apply predicate to check file is audio file
		boolean isAudioFile = new ManageDirectories.AudioFilePredicate().test(chosenFilePath);

		SavedSong newSong = null;
		if(isAudioFile)
		{
			String fullFileName = chosenFilePath.getFileName().toString();
			String[] splitFileName = fullFileName.split("\\.");
			String fileName = splitFileName[0];

			//Create a directory with the same name as the file
			Path storageDirectory = ManageDirectories.getDirectory(dataDirectory, fileName);

			Path savedFile = ManageDirectories.copyFileToNewLocation(chosenFilePath, storageDirectory);

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
		String filenameCommandPart1 = "--filename";
		String filenameCommandPart2 = "{stem}.{ext}";

		ProcessBuilder demucsPB = new ProcessBuilder();
		demucsPB.command(demucsPathString, inputPathString, outputCommand, filenameCommandPart1, filenameCommandPart2);

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
		Path outputDirectory = songForSeparation.getStorageDirectory();
		ProgressUpdater task = null;

		if(Files.exists(demucsPath))
		{
			Process demucs = buildDemucsProcess(demucsPath, selectedSongFile, outputDirectory);
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
