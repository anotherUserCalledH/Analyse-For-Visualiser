package musicanalysis.gui.panels.model;

import musicanalysis.gui.SavedSong;

public class AnalysisData
{
    Object analysisData;

    public AnalysisData(Object analysisData)
    {
        this.analysisData = analysisData;
    }

    public Object getData()
    {
        return analysisData;
    }

}
