package musicanalysis.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.Process;

import javafx.concurrent.Task;

public class ProgressUpdater extends Task
{
	Process monitoredProcess;

	public ProgressUpdater(Process monitoredProcess)
	{
		this.monitoredProcess = monitoredProcess;
	}

	public double checkProgress(String errorOutput)
	{
		StringBuilder outputString = new StringBuilder("");

		for(int index = 0; index < errorOutput.length(); index++)
		{
			char currentChar = errorOutput.charAt(index);


			if(Character.isDigit(currentChar))
			{
				outputString.append(currentChar);
			}
			if(currentChar == '%')
			{
				break;
			}
		}

		System.out.println(outputString.toString());

		double percentProgress = 10;
		if(outputString.length() > 0)
		{
			percentProgress = Integer.parseInt(outputString.toString());
			if(percentProgress < 10)
			{
				percentProgress = 10;
			}

		}

		return percentProgress;
	}

	@Override
	public Void call()
	{
		InputStream errorStream = monitoredProcess.getErrorStream();
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
		
		while(monitoredProcess.isAlive() == true)
		{
			String errorOutput = null;
			double percentProgress = 0;

			try
			{
				errorOutput = errorReader.readLine();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(errorOutput != null)
			{
				percentProgress = checkProgress(errorOutput);
			}
			updateProgress(percentProgress, 100);
		}

		try
		{
			errorReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}