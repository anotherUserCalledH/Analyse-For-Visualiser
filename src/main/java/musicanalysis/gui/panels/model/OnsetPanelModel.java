package musicanalysis.gui.panels.model;

import musicanalysis.gui.SavedSong;
import musicanalysis.structure.OnsetPluginLoader;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.io.LoadData;

import java.nio.file.Path;
import java.util.ArrayList;

public class OnsetPanelModel extends PanelModel
{

    @Override
    public ArrayList<AnalysisAlgorithm> loadAlgorithms()
    {
        OnsetPluginLoader loader = new OnsetPluginLoader();
        return loader.importAlgorithms();
    }


    @Override
    public boolean analyse(SavedSong selectedSong, AnalysisAlgorithm chosenAlgorithm)
    {
        Path selectedSongFile = selectedSong.getSongFile();
        AnalysisData analysisData = chosenAlgorithm.analyse(selectedSongFile);

        float[] onsetData = (float[]) analysisData.getData();
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
