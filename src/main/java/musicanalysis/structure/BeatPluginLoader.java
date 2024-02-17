package musicanalysis.structure;

import java.lang.reflect.InvocationTargetException;
import musicanalysis.algorithms.BeatAlgorithm;
import musicanalysis.algorithms.TarsosBeatAlgorithm;

public class BeatPluginLoader extends PluginLoader
{
    public BeatPluginLoader()
    {
        super("beat");
        initialise();
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