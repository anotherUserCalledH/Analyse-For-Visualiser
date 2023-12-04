package musicanalysis;

import musicanalysis.io.IOData;

import be.tarsos.dsp.onsets.OnsetHandler;

import java.util.ArrayList;

public class OnsetRetriever implements OnsetHandler
{
	private ArrayList<Float> onsetList = new ArrayList<>();

	@Override
	public void handleOnset(double time, double salience)
	{
		onsetList.add((float) time);
	}

	public float[] getOnsetArray()
	{
		float[] onsetArray = IOData.convertToFloatArray(onsetList);
		
		return onsetArray;
	}
}