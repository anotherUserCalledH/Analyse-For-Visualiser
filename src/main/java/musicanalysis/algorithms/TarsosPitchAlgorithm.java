package musicanalysis.algorithms;

import be.tarsos.dsp.pitch.PitchProcessor;
import musicanalysis.AnalyseMusic;
import musicanalysis.gui.panels.model.AnalysisData;

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
	public float getSampleRate()
	{
		return sampleRate;
	}

	@Override
	public AnalysisData analyse(Path audioFile)
	{
		return new AnalysisData(AnalyseMusic.detectPitch(audioFile, algorithm));
	}

	@Override
	public String toString()
	{
		return algorithm.toString();
	}
}