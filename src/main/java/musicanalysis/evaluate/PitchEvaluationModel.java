package musicanalysis.evaluate;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.util.ArrayList;

import musicanalysis.algorithms.PitchAlgorithm;

public class PitchEvaluationModel extends EvaluationModel<PitchAlgorithm>
{
	private ObservableList<PitchAlgorithm> algorithms;

	private ArrayList<String[]> overViewTable;
	private ArrayList<String[]> grossErrorTable;
	private ArrayList<String[]> totalErrorTable;

	private DecimalFormat formatter;


	public PitchEvaluationModel(ObservableList<PitchAlgorithm> inputAlgorithms)
	{
		super("pitch", ".mid");
		this.algorithms = inputAlgorithms;
		this.formatter = new DecimalFormat("#0.00%");
	}

	@Override
	public ObservableList<PitchAlgorithm> getAlgorithms()
	{
		return algorithms;
	}

	@Override
	public ResultTable[] evaluate(SimpleBooleanProperty[] useAlgorithm)
	{
		overViewTable = new ArrayList<String[]>();
		grossErrorTable = new ArrayList<String[]>();
		totalErrorTable = new ArrayList<String[]>();

		for(int currentAlgorithm = 0; currentAlgorithm < algorithms.size(); currentAlgorithm++)
		{
			if(useAlgorithm[currentAlgorithm].get() == true)
			{
				PitchEvalScore[] scores = calculateScores(algorithms.get(currentAlgorithm));
				String algorithmName = algorithms.get(currentAlgorithm).toString();
				addToOverviewTable(scores, algorithmName);
				addToSubTables(scores, algorithmName);
			}
		}

		String[] overViewTableHeaders = {"Algorithm", "Gross Error", "Total Error"};
		String[] subTableHeaders = getSubTableHeaders();

		ResultTable[] results = {new ResultTable("Overview", overViewTableHeaders, overViewTable), new ResultTable("Gross Error", subTableHeaders, grossErrorTable), new ResultTable("Total Error", subTableHeaders, totalErrorTable)};

		return results;
	}

	private PitchEvalScore[] calculateScores(PitchAlgorithm currentAlgorithm)
	{
		PitchEvaluator evaluator = new PitchEvaluator(currentAlgorithm);

		int noClips = evalAudio.size();
		PitchEvalScore[] scores = new PitchEvalScore[noClips];

		for(int currentClip = 0; currentClip < noClips; currentClip++)
		{
			scores[currentClip] = evaluator.evaluate(evalAudio.get(currentClip), evalAnnotations.get(currentClip));
		}

		return scores;
	}

	private void addToOverviewTable(PitchEvalScore[] scores, String algorithmName)
	{
		double averageGrossError = 0;
		double averageTotalError = 0;

		for(PitchEvalScore clipScore : scores)
		{
			averageGrossError += clipScore.getGrossError();
			averageTotalError += clipScore.getTotalError();
		}

		averageGrossError = averageGrossError / scores.length;
		averageTotalError = averageTotalError / scores.length;

		String[] newRow = {algorithmName, formatter.format(averageGrossError), formatter.format(averageTotalError)};

		overViewTable.add(newRow);
	}

	private void addToSubTables(PitchEvalScore[] scores, String algorithmName)
	{
		String[] grossErrorTableRow = new String[scores.length + 1];
		String[] totalErrorTableRow = new String[scores.length + 1];

		grossErrorTableRow[0] = algorithmName;
		totalErrorTableRow[0] = algorithmName;

		for(int clip = 0; clip < scores.length; clip++)
		{
			grossErrorTableRow[clip + 1] = formatter.format(scores[clip].getGrossError());
			totalErrorTableRow[clip + 1] = formatter.format(scores[clip].getTotalError());
		}

		grossErrorTable.add(grossErrorTableRow);
		totalErrorTable.add(totalErrorTableRow);
	}

	private String[] getSubTableHeaders()
	{
		String[] subTableHeaders = new String[evalAudio.size() + 1];

		subTableHeaders[0] = "Algorithm";
		for(int column = 1; column < subTableHeaders.length; column++)
		{
			subTableHeaders[column] = "Clip " + column;
		}

		return subTableHeaders;
	}
}
