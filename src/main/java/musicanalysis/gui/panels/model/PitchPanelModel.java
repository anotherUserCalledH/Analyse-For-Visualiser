package musicanalysis.gui.panels.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import musicanalysis.algorithms.PitchAlgorithm;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.structure.PitchPluginLoader;
import musicanalysis.io.LoadData;

import java.nio.file.Path;

public class PitchPanelModel extends PanelModel<PitchAlgorithm>
{
	private ObservableList<PitchAlgorithm> algorithms;

	public PitchPanelModel()
	{
		PitchPluginLoader loader = new PitchPluginLoader();
		this.algorithms = FXCollections.observableArrayList(loader.getAlgorithms());
	}

	@Override
	public ObservableList<PitchAlgorithm> getAlgorithms()
	{
		return algorithms;
	}

    @Override
    protected boolean analyse(SavedSong selectedSong, PitchAlgorithm chosenAlgorithm)
    {
        Path vocalsFile = selectedSong.getVocalsFile();

        int[] pitchData = chosenAlgorithm.analyse(vocalsFile);
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
