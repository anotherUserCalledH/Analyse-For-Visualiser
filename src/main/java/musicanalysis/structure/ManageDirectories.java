package musicanalysis.structure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageDirectories
{
    public static final Path CURRENT_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    public static final Path DATA_DIRECTORY = getDirectory(CURRENT_DIRECTORY, "data");
    public static final Path PLUGIN_DIRECTORY = getDirectory(CURRENT_DIRECTORY, "plugins");
    public static final Path DEMUCS_DIRECTORY = PLUGIN_DIRECTORY.resolve("demucs/demucs_api.exe");

	public static class DirectoryPredicate implements Predicate<Path>
    {
        @Override
        public boolean test(Path testPath)
        {
            return Files.isDirectory(testPath);
        }
    }

    public static class AudioFilePredicate implements Predicate<Path>
    {
        @Override
        public boolean test(Path testPath)
        {
            String extension = testPath.toString().toLowerCase();
            boolean isAudioFile = false;
            if(extension.endsWith(".wav") || extension.endsWith(".mp3"))
            {
                isAudioFile = true;
            }

            return isAudioFile;
        }
    }

    public static Path getDirectory(Path parentDirectory, String destinationName)
    {
        Path destinationDirectory = parentDirectory.resolve(destinationName);
        try
        {
            if(!Files.exists(destinationDirectory)){Files.createDirectory(destinationDirectory);}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return destinationDirectory;
    }

    public static List<Path> listFilesWithCondition(Path searchDirectory, Predicate<Path> searchCondition)
    {
        List<Path> outputFileList = null;
        try
        {
            Stream<Path> pathStream = Files.list(searchDirectory);
            outputFileList = pathStream.filter(searchCondition).collect(Collectors.toList());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return outputFileList;
    }

    public static Path findFileWithCondition(Path searchDirectory, Predicate<Path> searchCondition)
    {
        Path outputFile = null;

        List<Path> outputFileList = listFilesWithCondition(searchDirectory, searchCondition);
        if(!outputFileList.isEmpty())
        {
            outputFile = outputFileList.get(0);
        }

        return outputFile;
    }

	public static Path copyFileToNewLocation(Path originalPath, Path destinationDirectory)
	{
		Path newPath = destinationDirectory.resolve(originalPath.getFileName().toString());
		try
		{
			Files.copy(originalPath, newPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return newPath;
	}
}
