package musicanalysis;

import be.tarsos.dsp.pitch.PitchProcessor;
import java.nio.file.Path;

public class TarsosPitchAlgorithm implements PitchDetectionAlgorithm
{
	private int size = 1024;
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
		int hopSize = size - overlap;
		return hopSize;
	}

	@Override
	public float getSampleRate()
	{
		return sampleRate;
	}

	@Override
	public int[] getPitchArray(Path audioFile)
	{
		return AnalyseMusic.detectPitch(audioFile, algorithm);
	}

	@Override
	public String toString()
	{
		return algorithm.toString();
	}
}