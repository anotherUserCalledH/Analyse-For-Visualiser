package musicanalysis.structure;

import be.tarsos.dsp.pitch.PitchProcessor;
import java.lang.reflect.InvocationTargetException;

import musicanalysis.algorithms.PitchAlgorithm;
import musicanalysis.algorithms.TarsosPitchAlgorithm;

public class PitchPluginLoader extends PluginLoader
{
	public PitchPluginLoader()
	{
		super("pitch");
		initialise();
	}
	private void initialise()
	{
		for(PitchProcessor.PitchEstimationAlgorithm pitchAlgorithm : PitchProcessor.PitchEstimationAlgorithm.values())
		{
			PitchAlgorithm currentAlgorithm = (PitchAlgorithm) new TarsosPitchAlgorithm(pitchAlgorithm);
			algorithms.add(currentAlgorithm);
		}
	}

	@Override
	protected void addToAlgorithms(Class<?> newClass)
	{
		if(PitchAlgorithm.class.isAssignableFrom(newClass))
		{
			try
			{
				PitchAlgorithm newAlgorithm = (PitchAlgorithm) newClass.getConstructor().newInstance();
				algorithms.add(newAlgorithm);
			}
			catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
	}
}