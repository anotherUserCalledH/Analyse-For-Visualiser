package musicanalysis.gui;

import musicanalysis.io.LoadFile;
import musicanalysis.io.LoadData;
import musicanalysis.io.SavedSong;
import musicanalysis.AnalyseMusic;

import java.util.ArrayList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.List;


public class Model
{
	private Path dataDirectory;
	private ArrayList<SavedSong> savedSongs;
	private Path sourceFile;

	public Model()
	{
		savedSongs = new ArrayList<SavedSong>();
		Path currentDirectory = Paths.get(System.getProperty("user.dir"));
		this.dataDirectory = LoadFile.getDirectory(currentDirectory,"data");
	}

	public File getChosenFile()
	{
		return sourceFile.toFile();
	}

	public ArrayList<SavedSong> getSavedSongs()
	{
		return savedSongs;
	}

	public void setChosenFile(File chosenFile)
	{
		this.sourceFile = chosenFile.toPath();
	}

	public void saveFile()
	{
		String fullFileName = sourceFile.getFileName().toString();
		String[] splitFileName = fullFileName.split("\\.");

		String fileName = splitFileName[0];
		Path storageDirectory = LoadFile.getDirectory(dataDirectory, fileName);
		Path savedFile = LoadFile.storeFile(sourceFile, storageDirectory, fullFileName);

		//If savedFile isn't null, add to List of Songs
	}

	public void populateLoadList()
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
	}

	public void getLoadList()
	{

	}

	public void analysePitch()
	{
		// saveFile();
		// int[] pitchData = AnalyseMusic.analysePitch(savedFile);
		// pitchDataFile = storageDirectory.resolve("pitchData.csv");
		// LoadData.writePitchData(pitchData, pitchDataFile);
	}

	public void analyseBeat()
	{
		// saveFile();
		// float[] beatData = AnalyseMusic.analyseBeat(savedFile);
		// beatDataFile = storageDirectory.resolve("beatData.csv");

		// if(beatData != null)
		// {
		// 	LoadData.writeBeatData(beatData, beatDataFile);
		// }
	}
}
