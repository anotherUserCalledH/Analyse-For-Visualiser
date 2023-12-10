package musicanalysis.gui.panels;

import be.tarsos.dsp.pitch.PitchProcessor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import musicanalysis.PitchDetectionAlgorithm;
import musicanalysis.TarsosPitchAlgorithm;

public class PitchAnalysisModel extends AnalysisModel<PitchDetectionAlgorithm>
{
	public PitchAnalysisModel()
	{
		subdirectoryName = "pitch";

		for(PitchProcessor.PitchEstimationAlgorithm pitchAlgorithm : PitchProcessor.PitchEstimationAlgorithm.values())
		{
			PitchDetectionAlgorithm currentAlgorithm = (PitchDetectionAlgorithm) new TarsosPitchAlgorithm(pitchAlgorithm);
			algorithms.add(currentAlgorithm);
		}
	}

	@Override
	protected void addToAlgorithms(Class<?> newClass)
	{
		if(PitchDetectionAlgorithm.class.isAssignableFrom(newClass))
		{
			try
			{
				PitchDetectionAlgorithm newAlgorithm = (PitchDetectionAlgorithm) newClass.getConstructor().newInstance();
				algorithms.add(newAlgorithm);
			}
			catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}
}