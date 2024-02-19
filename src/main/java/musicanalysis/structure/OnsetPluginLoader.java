package musicanalysis.structure;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import musicanalysis.algorithms.OnsetAlgorithm;
import musicanalysis.algorithms.TarsosOnsetAlgorithm;

public class OnsetPluginLoader extends PluginLoader
{
	private ArrayList<OnsetAlgorithm> algorithms;
    public OnsetPluginLoader()
    {
		algorithms = new ArrayList<OnsetAlgorithm>();
        initialise();
		importAlgorithms("onset");
    }

	public ArrayList<OnsetAlgorithm> getAlgorithms()
	{
		return algorithms;
	}
    private void initialise()
    {
        OnsetAlgorithm defaultAlgorithm = (OnsetAlgorithm) new TarsosOnsetAlgorithm("Complex");
        algorithms.add(defaultAlgorithm);
    }

    @Override
    protected void addToAlgorithms(Class<?> newClass)
    {
        if(OnsetAlgorithm.class.isAssignableFrom(newClass))
        {
            try
            {
                OnsetAlgorithm newAlgorithm = (OnsetAlgorithm) newClass.getConstructor().newInstance();
                algorithms.add(newAlgorithm);
            }
            catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }
}