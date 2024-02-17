package musicanalysis.algorithms;

import musicanalysis.gui.panels.model.AnalysisData;

import java.nio.file.Path;

public interface AnalysisAlgorithm
{
    public AnalysisData analyse(Path songPath);
}
