package musicanalysis.algorithms;

import musicanalysis.evaluate.Evaluator;
import musicanalysis.evaluate.PitchEvaluator;

import java.nio.file.Path;

public interface PitchAlgorithm extends AnalysisAlgorithm
{
    public int getHopSize();
    public int getSampleRate();

	public int[] analyse(Path audioFile);
}