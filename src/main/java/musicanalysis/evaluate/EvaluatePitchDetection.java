// public class TestPitchDetection
// {
// 	public static void TestPitchDetection(int[] detectedPitch, int[] midiPitch)
// 	{
// 		double windowSizeInSeconds = (1800f/44100f);
// 		int noPitches = (detectedPitch.length > midiPitch.length) ? detectedPitch.length : midiPitch.length;

// 		double totalError = 0;
// 		double grossError = 0; 		
// 		double falsePositives = 0;		//excludes instances when the pitch detector returns no data
// 		int guessesMade = 0;

// 		for(int pitchIndex = 0; pitchIndex < noPitches; pitchIndex++)
// 		{		
// 			if(midiPitch[pitchIndex] == 0)
// 			{
// 				midiPitch[pitchIndex] = -1;
// 			}

// 			if(detectedPitch[pitchIndex] != -1)
// 			{
// 				guessesMade++;
// 			}

// 			if(midiPitch[pitchIndex] != detectedPitch[pitchIndex])
// 			{
// 				totalError++;

// 				if(detectedPitch[pitchIndex] != -1)
// 				{
// 					falsePositives++;
// 				}

// 				int absoluteDifference = Math.abs(detectedPitch[pitchIndex] - midiPitch[pitchIndex]);
// 				if(absoluteDifference > 3)
// 				{
// 					grossError ++;
// 				}
// 			}
// 		}

// 		// int[] midiPitch = (ProcessMIDI.importMIDI(midiFilePath, LoadMIDI.TrackType.MELODY, false));
// 		// DecimalFormat dec = new DecimalFormat("#0.00");
// 		// dec.format(pitchIndex * windowSizeInSeconds) + detectedPitch[pitchIndex] + midiPitch[pitchIndex] + errorMark;


// 	}
// }