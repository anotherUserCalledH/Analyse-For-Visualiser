package musicanalysis.gui.loadsong;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import musicanalysis.gui.SavedSong;

public class LoadSongComponent extends VBox
{
    @FXML
    private ListView<SavedSong> savedSongsListView;

    private ObjectProperty<EventHandler<ActionEvent>> propertyOnAction = new SimpleObjectProperty<EventHandler<ActionEvent>>();
    private LoadSongModel loadSongModel1 = new LoadSongModel();

    public LoadSongComponent() throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("load_song_component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void initialize()
    {
        savedSongsListView.setItems(loadSongModel1.getSavedSongs());

        savedSongsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SavedSong>()
        {
            @Override
            public void changed(ObservableValue<? extends SavedSong> observable, SavedSong oldValue, SavedSong newValue)
            {
                if (newValue != null)
                {
                    SavedSong selectedSong = savedSongsListView.getSelectionModel().getSelectedItem();
                    loadSongModel1.setSelectedSong(selectedSong);
                    onActionProperty().get().handle(new ActionEvent());
                }
            }
        });
    }

    public SavedSong getSelectedSong()
    {
        return loadSongModel1.getSelectedSong();
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty()
    {
        return propertyOnAction;
    }

    public final void setOnAction(EventHandler<ActionEvent> handler)
    {
        propertyOnAction.set(handler);
    }

    public final EventHandler<ActionEvent> getOnAction()
    {
        return propertyOnAction.get();

    }

    public void addNewSong(SavedSong newSong)
    {
        loadSongModel1.addNewSong(newSong);
    }

}
