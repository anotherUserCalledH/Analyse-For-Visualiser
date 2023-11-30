package musicanalysis.io;

import java.util.ArrayList;

public abstract class IOData
{
	protected boolean hasData;

	public abstract String buildLine(int loopVar);

	public abstract int getNoLines();

	public abstract void saveLine(String inputLine);

	public static int[] convertToIntArray(ArrayList<Integer> inputArrayList)
	{
		int noValues = inputArrayList.size();
		int[] outputArray = new int[noValues]; 
		for(int index = 0; index < noValues; index++)
		{
			outputArray[index] = inputArrayList.get(index);
		}

		return outputArray;
	}

	public static float[] convertToFloatArray(ArrayList<Float> inputArrayList)
	{
		int noValues = inputArrayList.size();
		float[] outputArray = new float[noValues]; 
		for(int index = 0; index < noValues; index++)
		{
			outputArray[index] = inputArrayList.get(index);
		}

		return outputArray;
	}
}