package musicanalysis;

import java.nio.file.Path;

public interface PitchDetectionAlgorithm
{
	public int getHopSize();
	public float getSampleRate();
	public int[] getPitchArray(Path songPath);
}