package musicanalysis.evaluate;

public class PitchEvalScore implements EvalScore
{
	private double grossError;
	private double totalError;

	public PitchEvalScore(double grossError, double totalError)
	{
		this.grossError = grossError;
		this.totalError = totalError;
	}

	public double getGrossError()
	{
		return grossError;
	}

	public double getTotalError()
	{
		return totalError;
	}
}
