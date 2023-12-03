package musicanalysis.io;

import java.nio.file.Files;
import java.nio.file.Path;

public class SavedSong
{
	private String songName;
	private Path storageDirectory;
	private Path songFile;

	private Path separatedAudioDirectory;
	private Path beatDataFile;
	private Path pitchDataFile;

	private boolean hasBeatData;
	private boolean hasPitchData;
	private boolean hasSeparatedAudio;


	public SavedSong(String songName, Path storageDirectory, Path songFile) //will be less error prone if I pass in demucs output folder as argument
	{
		this.songName = songName;
		this.storageDirectory = storageDirectory;
		this.songFile = songFile;

		this.separatedAudioDirectory = storageDirectory.resolve("../htdemucs/" + songName);
		if(Files.exists(separatedAudioDirectory.resolve("vocals.wav"))){this.hasSeparatedAudio = true;}
		else {this.hasSeparatedAudio = false;}		

		this.beatDataFile = storageDirectory.resolve("beatData.csv");
		if(Files.exists(beatDataFile)){this.hasBeatData = true;}
		else {this.hasBeatData = false;}

		this.pitchDataFile = storageDirectory.resolve("pitchData.csv");
		if(Files.exists(pitchDataFile)){this.hasPitchData = true;}
		else {this.hasPitchData = false;}
	}

	public Path getSongFile()
	{
		return songFile;
	}

	public Path getStorageDirectory()
	{
		return storageDirectory;
	}

	public Path getBeatDataFile()
	{
		return beatDataFile;
	}

	public Path getPitchDataFile()
	{
		return pitchDataFile;
	}

	public void setHasBeatData(boolean hasBeatData)
	{
		this.hasBeatData = hasBeatData;
	}

	public void setHasPitchData(boolean hasPitchData)
	{
		this.hasPitchData = hasPitchData;
	}

	public void setHasSeparatedAudio(boolean hasSeparatedAudio)
	{
		this.hasSeparatedAudio = true;
	}

	public boolean checkHasBeatData()
	{
		return hasBeatData;
	}

	public boolean checkHasPitchData()
	{
		return hasPitchData;
	}

	public boolean checkHasSeparatedAudio()
	{
		return hasSeparatedAudio;
	}

	@Override
	public String toString()
	{
		return songName;
	}
}