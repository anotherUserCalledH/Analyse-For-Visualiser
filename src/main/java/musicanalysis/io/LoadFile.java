package musicanalysis.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.List;
import java.util.stream.Collectors;

public class LoadFile
{
	public class DirectoryPredicate implements Predicate<Path>
	{
		@Override
		public boolean test(Path testPath)
		{
			return Files.isDirectory(testPath);
		}
	}

	public class AudioFilePredicate implements Predicate<Path>
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

	public static List<Path> listStorageDirectories(Path dataDirectory)
	{
		List<Path> storageDirectories = null;

		try
		{
			Stream<Path> pathStream = Files.list(dataDirectory);
			DirectoryPredicate predicate1 = new LoadFile().new DirectoryPredicate();
			storageDirectories = pathStream.filter(predicate1).collect(Collectors.toList());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return storageDirectories;
	}

	public static Path findSongFile(Path storageDirectory)
	{
		Path songFile = null;
		try
		{
			Stream<Path> pathStream = Files.list(storageDirectory);
			AudioFilePredicate predicate2 = new LoadFile().new AudioFilePredicate();
			List<Path> audioFiles = pathStream.filter(predicate2).collect(Collectors.toList());
			if(!audioFiles.isEmpty())
			{
				songFile = audioFiles.get(0);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return songFile;
	}

	public static Path storeFile(Path sourceFile, Path storageDirectory, String fullFileName)
	{
		Path savedFile = null;

		//Apply predicate to check file is audio file
		boolean isAudioFile = new LoadFile().new AudioFilePredicate().test(sourceFile);

		if(isAudioFile == true)
		{
			//Create path to destination location
			savedFile = storageDirectory.resolve(fullFileName);

			//Copy file to new destination
			try
			{
				Files.copy(sourceFile, savedFile);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return savedFile;
	}
}