package musicanalysis.io;

import java.nio.file.Files;
import java.nio.file.Path;

public class SavedSong
{
	private String songName;
	private Path storageDirectory;
	private Path songFile;

	private Path beatDataFile;
	private Path pitchDataFile;

	private boolean hasBeatData;
	private boolean hasPitchData;


	public SavedSong(String songName, Path storageDirectory, Path songFile)
	{
		this.songName = songName;
		this.storageDirectory = storageDirectory;
		this.songFile = songFile;

		this.beatDataFile = storageDirectory.resolve("beatData.csv");
		if(Files.exists(beatDataFile)){this.hasBeatData = true;}
		else {this.hasBeatData = false;}

		this.pitchDataFile = storageDirectory.resolve("pitchData.csv");
		if(Files.exists(pitchDataFile)){this.hasPitchData = true;}
		else {this.hasBeatData = false;}
	}

	@Override
	public String toString()
	{
		return songName;
	}
}