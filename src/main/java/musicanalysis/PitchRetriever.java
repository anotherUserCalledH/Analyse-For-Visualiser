package musicanalysis;

import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionResult;

import java.util.ArrayList;

public class PitchRetriever implements PitchDetectionHandler
{
	private ArrayList<float[]> pitchList = new ArrayList<>();

	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent)
	{
		float timeStamp = (float) audioEvent.getTimeStamp();
		float pitch = pitchDetectionResult.getPitch();
		float probability = pitchDetectionResult.getProbability();
		boolean pitched = pitchDetectionResult.isPitched();

		float[] pitchInfo = {timeStamp, pitch};
		pitchList.add(pitchInfo);
	}

	public int[] getPitchArray()
	{
		int[] pitchArray = new int[pitchList.size()];

		for(int index = 0; index < pitchList.size(); index++)
		{
			float[] pitchInfo = pitchList.get(index);
			pitchArray[index] = FrequencyToMidi.getMidiNoteNumber(pitchInfo[1]);
		}

		return pitchArray;
	}
}