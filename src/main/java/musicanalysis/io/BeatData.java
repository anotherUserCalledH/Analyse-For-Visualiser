package musicanalysis.io;

import musicanalysis.Util;

import java.util.ArrayList;

public class BeatData extends IOData
{
	private float[] beatDataArray; //Beat data in time stamps
	private ArrayList<Float> tempInputData;

	public BeatData(float[] beatDataArray)
	{
		this.hasData = true;
		this.beatDataArray = beatDataArray;
		this.tempInputData = new ArrayList<Float>();
	}

	public BeatData()
	{
		this.hasData = false;
		this.tempInputData = new ArrayList<Float>();
	}

	@Override
	public int getNoLines()
	{
		int noLines = 0;

		if(hasData = true)
		{
			noLines = beatDataArray.length;
		}
		else
		{
			System.err.println("No data to output");
		}

		return noLines;
	}

	@Override
	public String buildLine(int lineNumber)
	{
		String builtOutput = beatDataArray[lineNumber] + ",";

		return builtOutput;
	}

	@Override
	public void saveLine(String inputLine)
	{
		String[] inputStrings = inputLine.split(",");
		float inputFloat = Float.parseFloat(inputStrings[0]);
		tempInputData.add(inputFloat);
	}

	public float[] getData()
	{
		beatDataArray = convertToFloatArray(tempInputData);
		hasData = true;
		return beatDataArray;
	}
}