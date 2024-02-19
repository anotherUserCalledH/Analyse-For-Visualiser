package musicanalysis.gui.loadsong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.nio.file.Path;
import java.util.List;

import musicanalysis.structure.ManageDirectories;
import musicanalysis.gui.SavedSong;

public class LoadSongModel
{
	private ObservableList<SavedSong> savedSongs;

	private SavedSong selectedSong;

	public LoadSongModel()
	{
		savedSongs = FXCollections.observableArrayList();
	}

	public ObservableList<SavedSong> getSavedSongs()
	{
		List<Path> storageDirectories = ManageDirectories.listFilesWithCondition(ManageDirectories.DATA_DIRECTORY, new ManageDirectories.DirectoryPredicate());

		for(Path storageDirectory : storageDirectories)
		{
			String songName = storageDirectory.getFileName().toString();

			Path songFile = findSongFile(storageDirectory);

			if(songFile != null)
			{
				SavedSong currentSavedSong = new SavedSong(songName, storageDirectory, songFile);
				savedSongs.add(currentSavedSong);
			}
		}

		return savedSongs;
	}

	private Path findSongFile(Path storageDirectory)
	{
		return ManageDirectories.findFileWithCondition(storageDirectory, new ManageDirectories.AudioFilePredicate());
	}

	public void setSelectedSong(SavedSong selectedSong)
	{
		this.selectedSong = selectedSong;
	}

	public SavedSong getSelectedSong()
	{
		return selectedSong;
	}

	public void addNewSong(SavedSong newSong)
	{
		savedSongs.add(newSong);
		setSelectedSong(newSong);
	}
}
