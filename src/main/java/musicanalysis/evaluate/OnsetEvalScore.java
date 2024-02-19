package musicanalysis.evaluate;

public class OnsetEvalScore implements EvalScore
{
	private int noOnsets;

	private int noDetections;

	private int noTruePositives;

	private int noFalsePositives;

	public OnsetEvalScore(int noOnsets, int noDetections, int noTruePositives, int noFalsePositives)
	{
		this.noOnsets = noOnsets;
		this.noDetections = noDetections;
		this.noTruePositives = noTruePositives;
		this.noFalsePositives = noFalsePositives;
	}
}
