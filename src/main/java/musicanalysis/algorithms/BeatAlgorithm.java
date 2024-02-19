package musicanalysis.algorithms;

import java.nio.file.Path;

public interface BeatAlgorithm extends AnalysisAlgorithm
{
    public int getHopSize();
    public int getSampleRate();

	public float[] analyse(Path audioFile);
}
