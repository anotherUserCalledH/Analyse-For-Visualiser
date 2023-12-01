package musicanalysis;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.pitch.PitchProcessor;

import java.util.Arrays;
import java.nio.file.Path;


public class AnalyseMusic
{
	public static float[] detectBeat(Path audioFile)
	{
		int size = 512;
		int overlap = 256;
		int sampleRate = 16000;

		String stringPath = audioFile.toAbsolutePath().toString();

		AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(stringPath, sampleRate, size, overlap);

		BeatRetriever handler = new BeatRetriever();
		ComplexOnsetDetector detector = new ComplexOnsetDetector(size); //the audio processor
		detector.setHandler(handler);
		dispatcher.addAudioProcessor(detector);
		dispatcher.run();

		boolean analysisSuccessful = handler.beatInduction();
		float[] beats = null;

		if(analysisSuccessful == true)
		{
			beats = handler.getBeatArray();
		}

		return beats;
	}

	public static int[] detectPitch(Path audioFile)
	{
		int size = 1024;
		int overlap = 0;
		int sampleRate = 16000;
		PitchProcessor.PitchEstimationAlgorithm algorithm = PitchProcessor.PitchEstimationAlgorithm.FFT_YIN;

		String stringPath = audioFile.toAbsolutePath().toString();

		AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(stringPath, sampleRate, size, overlap);

		PitchRetriever handler = new PitchRetriever();
		PitchProcessor detector = new PitchProcessor(algorithm, sampleRate, size, handler);
		dispatcher.addAudioProcessor(detector);
		dispatcher.run();

		int[] pitches = handler.getPitchArray();

		return pitches;
	}
}