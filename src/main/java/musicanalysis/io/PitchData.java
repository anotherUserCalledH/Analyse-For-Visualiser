package musicanalysis.io;

import java.util.ArrayList;

public class PitchData extends IOData
{
	private int[] pitchDataArray; //Pitch data in midi pitches
	private ArrayList<Integer> tempInputData;

	public PitchData(int[] pitchDataArray)
	{
		this.hasData = true;
		this.pitchDataArray = pitchDataArray;
		this.tempInputData = new ArrayList<Integer>();
	}

	public PitchData()
	{
		this.hasData = false;
		this.tempInputData = new ArrayList<Integer>();
	}

	@Override
	public int getNoLines()
	{
		int noLines = 0;

		if(hasData = true)
		{
			noLines = pitchDataArray.length;
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
		String builtOutput = pitchDataArray[lineNumber] + ",";

		return builtOutput;
	}

	@Override
	public void saveLine(String inputLine)
	{
		String[] inputStrings = inputLine.split(",");
		int inputInt = Integer.parseInt(inputStrings[0]);
		tempInputData.add(inputInt);
	}

	public int[] getData()
	{
		pitchDataArray = convertToIntArray(tempInputData);
		hasData = true;
		return pitchDataArray;
	}
}