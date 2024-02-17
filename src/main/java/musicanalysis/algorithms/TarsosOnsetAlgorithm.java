package musicanalysis.algorithms;

import musicanalysis.AnalyseMusic;
import musicanalysis.gui.panels.model.AnalysisData;

import java.nio.file.Path;

public class TarsosOnsetAlgorithm implements OnsetAlgorithm
{
    private int windowSize = 512;
    private int overlap = 256;
    private int sampleRate = 44100;

    private String algorithmName;

    public TarsosOnsetAlgorithm(String algorithmName)
    {
        this.algorithmName = algorithmName;
    }

    @Override
    public int getHopSize()
    {
        int hopSize = windowSize - overlap;
        return hopSize;
    }

    @Override
    public float getSampleRate()
    {
        return sampleRate;
    }

    @Override
    public AnalysisData analyse(Path audioFile)
    {
        return new AnalysisData(AnalyseMusic.detectOnsets(audioFile));
    }

    @Override
    public String toString()
    {
        return algorithmName;
    }
}
