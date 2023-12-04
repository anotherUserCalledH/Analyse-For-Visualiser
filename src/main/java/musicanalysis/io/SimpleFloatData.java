package musicanalysis.io;

import java.util.ArrayList;

public class SimpleFloatData extends IOData
{
	private float[] floatDataArray;
	private ArrayList<Float> tempInputData;

	public SimpleFloatData(float[] floatDataArray)
	{
		this.hasData = true;
		this.floatDataArray = floatDataArray;
		this.tempInputData = new ArrayList<Float>();
	}

	public SimpleFloatData()
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
			noLines = floatDataArray.length;
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
		String builtOutput = floatDataArray[lineNumber] + ",";

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
		floatDataArray = convertToFloatArray(tempInputData);
		hasData = true;
		return floatDataArray;
	}
}