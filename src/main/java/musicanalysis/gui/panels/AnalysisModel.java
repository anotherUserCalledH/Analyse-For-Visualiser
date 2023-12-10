package musicanalysis.gui.panels;

import javafx.collections.FXCollections;
import java.nio.file.Path;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.jar.JarFile;


public abstract class AnalysisModel<T>
{
	protected String subdirectoryName;
	protected ObservableList<T> algorithms = FXCollections.observableArrayList();

	public ObservableList<T> getAlgorithms()
	{
		return algorithms;
	}

	protected abstract void addToAlgorithms(Class<?> newClass);

	protected static URL[] listJarFiles(Path pluginDirectory)
	{
		List<Path> jarFiles = null;
		URL[] jarURLs = null;

		try
		{
			Stream<Path> pathStream = Files.list(pluginDirectory);
			jarFiles = pathStream.collect(Collectors.toList());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		if(jarFiles != null)
		{
			jarURLs = new URL[jarFiles.size()];

			for(Path jarPath : jarFiles)
			{
				int index = jarFiles.indexOf(jarPath);
				URI jarURI = jarPath.toUri();
				try
				{
					URL jarURL = jarURI.toURL();
					jarURLs[index] = jarURL;
				}
				catch(MalformedURLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return jarURLs;
	}

	public void loadPlugins(Path pluginPath)
	{
		Path subdirectoryPath = pluginPath.resolve(subdirectoryName);
		if(Files.exists(subdirectoryPath))
		{
			URL[] jarURLs = listJarFiles(subdirectoryPath);

			if(jarURLs.length > 0)
			{
				ClassLoader mainClassLoader = AnalysisModel.class.getClassLoader();
				URLClassLoader classLoader = new URLClassLoader(jarURLs, mainClassLoader);

				for(URL jarURL : jarURLs)
				{
					try
					{
						String mainClass = new JarFile(jarURL.getPath()).getManifest().getMainAttributes().getValue("Main-Class");
						Class<?> newClass = classLoader.loadClass(mainClass);
						addToAlgorithms(newClass);
					}
					catch(IOException | ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			}
		}	
	}
}

