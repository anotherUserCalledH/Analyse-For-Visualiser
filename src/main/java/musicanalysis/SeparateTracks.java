//Launches a frozen version of Demucs, see https://github.com/facebookresearch/demucs

package musicanalysis;

import java.lang.ProcessBuilder;
import java.lang.Process;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.file.Path;

public class SeparateTracks
{
	public static Process separateAudioFile(Path demucsPath, Path inputPath, Path outputPath)
	{
		String demucsPathString = demucsPath.toAbsolutePath().toString();
		String inputPathString = inputPath.toAbsolutePath().toString();
		String outputPathString = outputPath.toAbsolutePath().toString();
		String outputCommand = "-o" + outputPathString;

		ProcessBuilder demucsPB = new ProcessBuilder();
		demucsPB.command(demucsPathString, inputPathString, outputCommand);

		Process demucs = null;		

		try
		{
			demucs = demucsPB.start();
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return demucs;
	}
}