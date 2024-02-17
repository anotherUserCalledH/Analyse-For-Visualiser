package musicanalysis.gui.panels.model;

import musicanalysis.gui.SavedSong;
import musicanalysis.structure.BeatPluginLoader;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.io.LoadData;

import java.nio.file.Path;
import java.util.ArrayList;

public class BeatPanelModel extends PanelModel
{

    @Override
    public ArrayList<AnalysisAlgorithm> loadAlgorithms()
    {
        BeatPluginLoader loader = new BeatPluginLoader();
        return loader.importAlgorithms();
    }

    @Override
    public boolean analyse(SavedSong selectedSong, AnalysisAlgorithm chosenAlgorithm)
    {
        Path selectedSongFile = selectedSong.getSongFile();
        AnalysisData analysisData = chosenAlgorithm.analyse(selectedSongFile);
        float[] beatData = (float[]) analysisData.getData();

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
