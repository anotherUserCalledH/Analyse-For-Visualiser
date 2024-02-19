package musicanalysis.gui.panels.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import musicanalysis.algorithms.BeatAlgorithm;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.structure.BeatPluginLoader;
import musicanalysis.io.LoadData;

import java.nio.file.Path;

public class BeatPanelModel extends PanelModel<BeatAlgorithm>
{
	private ObservableList<BeatAlgorithm> algorithms;
	public BeatPanelModel()
	{
		BeatPluginLoader loader = new BeatPluginLoader();
		this.algorithms = FXCollections.observableArrayList(loader.getAlgorithms());
	}

	@Override
	public ObservableList<BeatAlgorithm> getAlgorithms()
	{
		return algorithms;
	}

    @Override
    public boolean analyse(SavedSong selectedSong, BeatAlgorithm chosenAlgorithm)
    {
        Path selectedSongFile = selectedSong.getSongFile();
        float[] beatData = chosenAlgorithm.analyse(selectedSongFile);

        boolean detectionSuccessful = false;
        if(beatData != null)
        {
            Path beatDataFile = selectedSong.getBeatDataFile();
            LoadData.writeBeatData(beatData, beatDataFile);
            //Should probably in boolean return value to LoadData to check for errors
            selectedSong.setHasBeatData(true);
            detectionSuccessful = true;
        }
        return detectionSuccessful;
    }

    @Override
    public AnalysisData getAnalysisData(SavedSong selectedSong)
    {
        Path beatDataFile = selectedSong.getBeatDataFile();
        float[] beatData = LoadData.readOnsetData(beatDataFile);

        return new AnalysisData(beatData);
    }
}
