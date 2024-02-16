package musicanalysis.gui.loadsong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import musicanalysis.gui.SavedSong;
import musicanalysis.io.LoadFile;


public class LoadSongModel
{
    private Path dataDirectory;

    private ObservableList<SavedSong> savedSongs;

    private SavedSong selectedSong;

    public LoadSongModel()
    {
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        this.dataDirectory = LoadFile.getDirectory(currentDirectory, "data");
        savedSongs = FXCollections.observableArrayList();
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
