package musicanalysis.gui.panels;

import javafx.collections.ObservableList;
import musicanalysis.algorithms.PitchAlgorithm;
import musicanalysis.evaluate.PitchEvaluationModel;
import musicanalysis.gui.windows.LaunchNewWindow;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.gui.panels.model.PitchPanelModel;

import java.io.IOException;
import java.nio.file.Path;

import javafx.event.ActionEvent;


public class PitchAnalysisPanel extends AnalysisPanel<PitchAlgorithm>
{
	public PitchAnalysisPanel() throws Exception
	{
		super(new PitchPanelModel(), "PITCH");
	}

	@Override
	public void updateAnalysisStatus(SavedSong selectedSong)
	{
		if(selectedSong.checkHasSeparatedAudio() == true)
		{
			if(selectedSong.checkHasPitchData() == true)
			{
				setComplete();
			}
			else
			{
				setReady();
			}
		}
		else
		{

			setNotReady();
		}

		previewButton.setVisible(false);
	}

	@Override
	protected void buildPreview(AnalysisData dataToPreview, Path songPath)
	{
		int[] pitchData = (int[]) dataToPreview.getData();
		try
		{
			LaunchNewWindow.launchPitchPreview(songPath, pitchData);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void evaluate(ActionEvent event)
	{
		ObservableList<PitchAlgorithm> pitchAlgorithms = placeholderModel.getAlgorithms();
		PitchEvaluationModel evalModel = new PitchEvaluationModel(pitchAlgorithms);

		try
		{
			LaunchNewWindow.launchEvaluation(evalModel, "Evaluate Pitch");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}