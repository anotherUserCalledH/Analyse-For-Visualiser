package musicanalysis.structure;

import musicanalysis.algorithms.AnalysisAlgorithm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class PluginLoader
{
	protected String pluginDirectoryName;
	protected ArrayList<AnalysisAlgorithm> algorithms;

	public PluginLoader(String pluginDirectoryName)
	{
		this.algorithms = new ArrayList<AnalysisAlgorithm>();
		this.pluginDirectoryName = pluginDirectoryName;
	}

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

	public ArrayList<AnalysisAlgorithm> importAlgorithms()
	{
		Path pluginPath = ManageDirectories.PLUGIN_DIRECTORY;
		Path pluginDirectory = pluginPath.resolve(pluginDirectoryName);
		if(Files.exists(pluginDirectory))
		{
			URL[] jarURLs = listJarFiles(pluginDirectory);

			if(jarURLs.length > 0)
			{
				ClassLoader mainClassLoader = PluginLoader.class.getClassLoader();
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

		return algorithms;
	}

	protected abstract void addToAlgorithms(Class<?> newClass);

}
