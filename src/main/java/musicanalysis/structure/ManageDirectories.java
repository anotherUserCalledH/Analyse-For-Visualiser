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
    public static final Path DEMUCS_DIRECTORY = ManageDirectories.PLUGIN_DIRECTORY.resolve("demucs/RunDemucs.exe");

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



    //Used in LoadSongModel
    public static List<Path> listStorageDirectories()
    {
        List<Path> storageDirectories = null;

        try
        {
            Stream<Path> pathStream = Files.list(DATA_DIRECTORY);
            storageDirectories = pathStream.filter(new DirectoryPredicate()).collect(Collectors.toList());
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
            List<Path> audioFiles = pathStream.filter(new AudioFilePredicate()).collect(Collectors.toList());
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

    //Used in model
    public static Path storeFile(Path sourceFile, Path storageDirectory, String fullFileName)
    {
        Path savedFile = null;

        //Apply predicate to check file is audio file
        boolean isAudioFile = new AudioFilePredicate().test(sourceFile);

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
