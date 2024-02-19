package musicanalysis.gui.panels.model;

import javafx.collections.ObservableList;
import musicanalysis.gui.SavedSong;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.gui.windows.AnalysisData;

public abstract class PanelModel<T extends AnalysisAlgorithm>
{
    private AnalysisData analysisData;

    private SavedSong selectedSong;

    private T chosenAlgorithm;

    public void setChosenAlgorithm(T chosenAlgorithm)
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

    protected abstract boolean analyse(SavedSong selectedSong, T chosenAlgorithm);

    protected abstract AnalysisData getAnalysisData(SavedSong savedSong);

	public abstract ObservableList<T> getAlgorithms();
}
