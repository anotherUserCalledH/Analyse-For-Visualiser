package musicanalysis.structure;

import java.lang.reflect.InvocationTargetException;
import musicanalysis.algorithms.OnsetAlgorithm;
import musicanalysis.algorithms.TarsosOnsetAlgorithm;

public class OnsetPluginLoader extends PluginLoader
{
    public OnsetPluginLoader()
    {
        super("onset");
        initialise();
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