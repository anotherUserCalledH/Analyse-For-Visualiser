package musicanalysis.gui.panels.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import musicanalysis.algorithms.OnsetAlgorithm;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.structure.OnsetPluginLoader;
import musicanalysis.io.LoadData;

import java.nio.file.Path;

public class OnsetPanelModel extends PanelModel<OnsetAlgorithm>
{
	private ObservableList<OnsetAlgorithm> algorithms;

	public OnsetPanelModel()
    {
        OnsetPluginLoader loader = new OnsetPluginLoader();
		this.algorithms = FXCollections.observableArrayList(loader.getAlgorithms());
    }

	@Override
	public ObservableList<OnsetAlgorithm> getAlgorithms()
	{
		return algorithms;
	}

    @Override
    public boolean analyse(SavedSong selectedSong, OnsetAlgorithm chosenAlgorithm)
    {
        Path selectedSongFile = selectedSong.getSongFile();
        float[] onsetData = chosenAlgorithm.analyse(selectedSongFile);
        Path onsetDataFile = selectedSong.getOnsetDataFile();
        LoadData.writeOnsetData(onsetData, onsetDataFile);

        selectedSong.setHasOnsetData(true);
        return true;
    }

    @Override
    public AnalysisData getAnalysisData(SavedSong selectedSong)
    {
        Path onsetDataFile = selectedSong.getOnsetDataFile();
        float[] onsetData = LoadData.readOnsetData(onsetDataFile);

        return new AnalysisData(onsetData);
    }
}
