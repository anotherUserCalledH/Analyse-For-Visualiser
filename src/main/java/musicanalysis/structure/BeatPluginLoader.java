package musicanalysis.structure;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import musicanalysis.algorithms.BeatAlgorithm;
import musicanalysis.algorithms.TarsosBeatAlgorithm;

public class BeatPluginLoader extends PluginLoader
{
	private ArrayList<BeatAlgorithm> algorithms;

	public BeatPluginLoader()
    {
		algorithms = new ArrayList<BeatAlgorithm>();
        initialise();
		importAlgorithms("beat");
    }

	public ArrayList<BeatAlgorithm> getAlgorithms()
	{
		return algorithms;
	}

    private void initialise()
    {
        BeatAlgorithm defaultAlgorithm = (BeatAlgorithm) new TarsosBeatAlgorithm("Beatroot");
        algorithms.add(defaultAlgorithm);
    }

    @Override
    protected void addToAlgorithms(Class<?> newClass)
    {
        if(BeatAlgorithm.class.isAssignableFrom(newClass))
        {
            try
            {
                BeatAlgorithm newAlgorithm = (BeatAlgorithm) newClass.getConstructor().newInstance();
                algorithms.add(newAlgorithm);
            }
            catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }
}