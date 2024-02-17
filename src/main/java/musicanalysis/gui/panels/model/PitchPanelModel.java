package musicanalysis.gui.panels.model;

import musicanalysis.gui.SavedSong;
import musicanalysis.structure.PitchPluginLoader;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.io.LoadData;

import java.nio.file.Path;
import java.util.ArrayList;

public class PitchPanelModel extends PanelModel
{

    @Override
    public ArrayList<AnalysisAlgorithm> loadAlgorithms()
    {
        PitchPluginLoader loader = new PitchPluginLoader();
        return loader.importAlgorithms();
    }

    @Override
    protected boolean analyse(SavedSong selectedSong, AnalysisAlgorithm chosenAlgorithm)
    {
        Path vocalsFile = selectedSong.getVocalsFile();
        AnalysisData analysisData = chosenAlgorithm.analyse(vocalsFile);

        int[] pitchData = (int[]) analysisData.getData();
        Path pitchDataFile = selectedSong.getPitchDataFile();
        LoadData.writePitchData(pitchData, pitchDataFile);

        selectedSong.setHasPitchData(true);
        return true;
    }

    @Override
    protected AnalysisData getAnalysisData(SavedSong selectedSong)
    {
        Path pitchDataFile = selectedSong.getPitchDataFile();
        int[] pitchData = LoadData.readPitchData(pitchDataFile);
        return new AnalysisData(pitchData);
    }
}
