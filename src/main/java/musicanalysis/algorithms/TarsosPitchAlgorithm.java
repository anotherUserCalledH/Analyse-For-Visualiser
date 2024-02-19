package musicanalysis.algorithms;

import be.tarsos.dsp.pitch.PitchProcessor;
import musicanalysis.AnalyseMusic;

import java.nio.file.Path;

public class TarsosPitchAlgorithm implements PitchAlgorithm
{
	private int windowSize = 1024;
	private int overlap = 0;
	private int sampleRate = 16000;

	private PitchProcessor.PitchEstimationAlgorithm algorithm;

	public TarsosPitchAlgorithm(PitchProcessor.PitchEstimationAlgorithm algorithm)
	{
		this.algorithm = algorithm;
	}

	@Override
	public int getHopSize()
	{
		int hopSize = windowSize - overlap;
		return hopSize;
	}

	@Override
	public int getSampleRate()
	{
		return sampleRate;
	}

	@Override
	public int[] analyse(Path audioFile)
	{
		return AnalyseMusic.detectPitch(audioFile, algorithm);
	}

	@Override
	public String toString()
	{
		return algorithm.toString();
	}
}