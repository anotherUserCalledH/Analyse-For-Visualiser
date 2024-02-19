package musicanalysis.io;

import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import java.nio.file.StandardOpenOption;

public class LoadData
{
	private static void writeData(IOData outputObject, Path path)
	{
		try
		{
			OutputStream output = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			
			for(int lineNumber = 0; lineNumber < outputObject.getNoLines(); lineNumber++)
			{
				String builtLine = outputObject.buildLine(lineNumber);
				writer.write(builtLine, 0, builtLine.length());
				writer.newLine();
			}
			writer.close();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writePitchData(int[] outputArray, Path path)
	{
		IOData outputObject = new PitchData(outputArray);
		writeData(outputObject, path);
	}

	// public static void writeOnsetData(float[] outputArray, Path path)
	// {
	// 	IOData outputObject = new
	// 	writeData(outputObject, path);
	// }

	public static void writeSimpleFloatData(float[] outputArray, Path path)
	{
		IOData outputObject = new SimpleFloatData(outputArray);
		writeData(outputObject, path);
	}

	public static void writeBeatData(float[] outputArray, Path path)
	{
		writeSimpleFloatData(outputArray, path);
	}

	public static void writeOnsetData(float[] outputArray, Path path)
	{
		writeSimpleFloatData(outputArray, path);
	}

	private static IOData readData(IOData inputObject, Path path)
	{
		try
		{
			InputStream input = Files.newInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String inputLine;
			while((inputLine = reader.readLine()) != null)
			{
				inputObject.saveLine(inputLine);
			}

			input.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return inputObject;
	}

	public static int[] readPitchData(Path path)
	{
		IOData inputObject = new PitchData();
		PitchData pitchDataObject = (PitchData) readData(inputObject, path);
		int[] inputArray = pitchDataObject.getData();
		return inputArray;
	}

	public static float[] readSimpleFloatData(Path path)
	{
		IOData inputObject = new SimpleFloatData();
		SimpleFloatData dataObject = (SimpleFloatData) readData(inputObject, path);
		float[] inputArray = dataObject.getData();
		return inputArray;
	}

	public static float[] readBeatData(Path path)
	{
		return readSimpleFloatData(path);
	}

	public static float[] readOnsetData(Path path)
	{
		return readSimpleFloatData(path);
	}


}