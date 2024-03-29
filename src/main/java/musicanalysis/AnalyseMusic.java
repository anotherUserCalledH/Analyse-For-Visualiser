package musicanalysis;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.ComplexOnsetDetector;
import be.tarsos.dsp.pitch.PitchProcessor;


import java.nio.file.Path;

//Needs to refactored once more algorithms are added
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

	public static int[] detectPitch(Path audioFile, PitchProcessor.PitchEstimationAlgorithm algorithm)
	{
		int size = 1024;
		int overlap = 0;
		int sampleRate = 16000;

		String stringPath = audioFile.toAbsolutePath().toString();

		AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(stringPath, sampleRate, size, overlap);

		PitchRetriever handler = new PitchRetriever();
		PitchProcessor detector = new PitchProcessor(algorithm, sampleRate, size, handler);
		dispatcher.addAudioProcessor(detector);
		dispatcher.run();

		int[] pitches = handler.getPitchArray();

		return pitches;
	}

	public static float[] detectOnsets(Path audioFile)
	{
		int size = 512;
		int overlap = 256;
		int sampleRate = 44100;

		String stringPath = audioFile.toAbsolutePath().toString();

		AudioDispatcher dispatcher = AudioDispatcherFactory.fromPipe(stringPath, sampleRate, size, overlap);

		OnsetRetriever handler = new OnsetRetriever();
		ComplexOnsetDetector detector = new ComplexOnsetDetector(size, 0.7, 0.1);
		detector.setHandler(handler);
		dispatcher.addAudioProcessor(detector);
		dispatcher.run();

		float[] onsets = handler.getOnsetArray();

		return onsets;
	}
}

 //FXCollections.observableArrayList("YIN", "McLeod Pitch Method", "Yin FFT", "Dynamic Wavelet", "Spectrum Energy", "AMDF Function", "Chunky Yin");