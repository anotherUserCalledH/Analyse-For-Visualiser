package musicanalysis.algorithms;

import java.nio.file.Path;

public interface OnsetAlgorithm extends AnalysisAlgorithm
{
    public int getHopSize();
    public int getSampleRate();

	public float[] analyse(Path audioFile);
}
