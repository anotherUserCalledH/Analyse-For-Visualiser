package musicanalysis.evaluate;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.structure.ManageDirectories;

public abstract class EvaluationModel<T extends AnalysisAlgorithm>
{
	protected ArrayList<Path> evalAudio;
	protected ArrayList<Path> evalAnnotations;

	protected final String annotationDirectory;

	protected final String annotationExtension;

	public EvaluationModel(String annotationDirectory, String annotationExtension)
	{
		this.annotationDirectory = annotationDirectory;
		this.annotationExtension = annotationExtension;
		loadEvalData();
	}
	public Path findAnnotationFile(Path evalDirectory, Path currentFile)
	{
		String correspondingAnnotation = currentFile.getFileName().toString().replaceFirst("\\..*", annotationExtension);
		Path annotationFile = currentFile.resolve(evalDirectory + "\\" + annotationDirectory + "\\" + correspondingAnnotation);

		return annotationFile;
	}

	public void loadEvalData()
	{
		try
		{
			Path evalDirectory = Paths.get(getClass().getResource("").toURI());
			List<Path> audioFiles = ManageDirectories.listFilesWithCondition(evalDirectory, new ManageDirectories.AudioFilePredicate());
			audioFiles.sort(Comparator.comparing(currentFile -> currentFile.getFileName()));

			this.evalAudio = new ArrayList<Path>();
			this.evalAnnotations = new ArrayList<Path>();

			for(Path currentFile : audioFiles)
			{
				Path annotationFile = findAnnotationFile(evalDirectory, currentFile);
				if(Files.exists(annotationFile))
				{
					evalAudio.add(currentFile);
					evalAnnotations.add(annotationFile);
				}
			}
		}
		catch(URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public abstract ObservableList<T> getAlgorithms();

	public abstract ResultTable[] evaluate(SimpleBooleanProperty[] useAlgorithm);

}
