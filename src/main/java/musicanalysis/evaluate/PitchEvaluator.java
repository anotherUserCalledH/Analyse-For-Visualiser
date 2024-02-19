package musicanalysis.evaluate;

import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.algorithms.PitchAlgorithm;
import musicanalysis.io.LoadMIDI;

import java.nio.file.Path;
import java.util.List;

public class PitchEvaluator implements Evaluator
{
	private String label;
	private PitchAlgorithm algorithm;

	public PitchEvaluator(PitchAlgorithm inputAlgorithm)
	{
		this.algorithm = inputAlgorithm;
	}

	public PitchEvalScore evaluate(Path evalAudio, Path evalAnnotation)
	{
		int[] detectedPitch = algorithm.analyse(evalAudio);
		int[] validationPitch = LoadMIDI.importMIDI(evalAnnotation.toString(), LoadMIDI.TrackType.MELODY, algorithm.getHopSize(), algorithm.getSampleRate());

		return evaluate(detectedPitch, validationPitch);
	}

 	private PitchEvalScore evaluate(int[] detectedPitch, int[] midiPitch)
 	{
 		int noPitches = (detectedPitch.length < midiPitch.length) ? detectedPitch.length : midiPitch.length;

 		double totalError = 0;
 		double grossError = 0;
 		double falsePositives = 0;		//excludes instances when the pitch detector returns no data
 		int guessesMade = 0;

 		for(int pitchIndex = 0; pitchIndex < noPitches; pitchIndex++)
 		{
 			if(midiPitch[pitchIndex] == 0)
 			{
 				midiPitch[pitchIndex] = -1;
 			}

 			if(detectedPitch[pitchIndex] != -1)
 			{
 				guessesMade++;
 			}

 			if(midiPitch[pitchIndex] != detectedPitch[pitchIndex])
 			{
 				totalError++;

 				if(detectedPitch[pitchIndex] != -1)
 				{
 					falsePositives++;
 				}

 				int absoluteDifference = Math.abs(detectedPitch[pitchIndex] - midiPitch[pitchIndex]);
 				if(absoluteDifference > 3)
 				{
 					grossError++;
 				}
 			}
 		}

		totalError = (float) totalError/noPitches;
		grossError = (float) grossError/noPitches;

		return new PitchEvalScore(grossError, totalError);
	}
}