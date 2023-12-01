//Launches a frozen version of Demucs, see https://github.com/facebookresearch/demucs

package musicanalysis;

import java.lang.ProcessBuilder;
import java.lang.Process;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class SeparateTracks
{
	public static boolean separateAudioFile(Path demucsPath, Path inputPath, Path outputPath)
	{
		String demucsPathString = demucsPath.toAbsolutePath().toString();
		String inputPathString = inputPath.toAbsolutePath().toString();
		String outputPathString = outputPath.toAbsolutePath().toString();
		String outputCommand = "-o" + outputPathString;

		ProcessBuilder demucsPB = new ProcessBuilder();
		demucsPB.redirectErrorStream(true);
		// demucsPB.command(demucsPath + "\\RunDemucs.exe", inputFilePath, outputCommand);
		demucsPB.command(demucsPathString, inputPathString, outputCommand);
		String programOutput = "";

		try
		{
			Process demucs = demucsPB.start();
			InputStream input = demucs.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while(!(programOutput.equals("proceed")))
			{
				programOutput = reader.readLine();

				if(demucs.isAlive() != true)
				{
					break;
				}
			}
			input.close();
		}		
		catch(IOException e)
		{
			e.printStackTrace();
		}

		String inputFileName = (inputPath.getFileName()).toString();
		String[] splitFileName = inputFileName.split("\\.");
		String outputFolder = splitFileName[0];
		Path testPath = Paths.get(outputPathString + "\\htdemucs\\" + outputFolder + "\\vocals.wav");
		boolean outputExists = Files.exists(testPath); 

		return outputExists;
	}
}