package musicanalysis.gui.panels.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import musicanalysis.gui.SavedSong;
import musicanalysis.algorithms.AnalysisAlgorithm;

import java.util.ArrayList;

public abstract class PanelModel
{
    private ObservableList<AnalysisAlgorithm> algorithms;

    private AnalysisData analysisData;

    private SavedSong selectedSong;

    private AnalysisAlgorithm chosenAlgorithm;
    public PanelModel()
    {
        algorithms = FXCollections.observableArrayList(loadAlgorithms());
    }

    public ObservableList<AnalysisAlgorithm> getAlgorithms()
    {
        return algorithms;
    }

    public void setChosenAlgorithm(AnalysisAlgorithm chosenAlgorithm)
    {
        this.chosenAlgorithm = chosenAlgorithm;
    }

    public SavedSong getSelectedSong()
    {
        return selectedSong;
    }

    public void setSelectedSong(SavedSong selectedSong)
    {
        this.selectedSong = selectedSong;
    }

    public boolean analyse()
    {
        return analyse(selectedSong, chosenAlgorithm);
    }

    public AnalysisData getAnalysisData()
    {
        return getAnalysisData(selectedSong);
    }

    protected abstract boolean analyse(SavedSong selectedSong, AnalysisAlgorithm chosenAlgorithm);

    protected abstract AnalysisData getAnalysisData(SavedSong savedSong);

    public abstract ArrayList<AnalysisAlgorithm> loadAlgorithms();
}
