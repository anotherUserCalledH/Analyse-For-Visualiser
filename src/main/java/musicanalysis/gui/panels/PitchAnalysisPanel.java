package musicanalysis.gui.panels;

import musicanalysis.gui.SavedSong;
import musicanalysis.AnalyseMusic;
import musicanalysis.io.LoadData;

import be.tarsos.dsp.pitch.PitchProcessor;

//Should be moved to AnalysisPanel at some point
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import java.nio.file.Path;


public class PitchAnalysisPanel extends AnalysisPanel
{
	private PitchProcessor.PitchEstimationAlgorithm chosenPitchAlgorithm;

	public PitchAnalysisPanel() throws Exception
	{
		setHeaderLabel("PITCH");
	}

	@Override
	protected void initialiseChoiceBox()
	{
		ObservableList<PitchProcessor.PitchEstimationAlgorithm> algorithms = FXCollections.observableArrayList(AnalyseMusic.getPitchAlgorithms());
		
		algorithmsChoiceBox.setItems(algorithms);
		algorithmsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PitchProcessor.PitchEstimationAlgorithm>()
		{
			@Override
			public void changed(ObservableValue<? extends PitchProcessor.PitchEstimationAlgorithm> observable, PitchProcessor.PitchEstimationAlgorithm oldValue, PitchProcessor.PitchEstimationAlgorithm newValue)
			{
			if (newValue != null)
				{
					chosenPitchAlgorithm = (PitchProcessor.PitchEstimationAlgorithm) algorithmsChoiceBox.getSelectionModel().getSelectedItem();
					if(selectedSong.checkHasSeparatedAudio())
					{
						setReady();
					}
				}
			}
		});
	}

	@Override
	public void updateAnalysisStatus()
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

	protected void analysePitch(PitchProcessor.PitchEstimationAlgorithm pitchAlgorithm)
	{
		Path selectedSongFile = selectedSong.getVocalsFile();
		int[] pitchData = AnalyseMusic.detectPitch(selectedSongFile, pitchAlgorithm);

		Path pitchDataFile = selectedSong.getPitchDataFile();
		LoadData.writePitchData(pitchData, pitchDataFile);
		selectedSong.setHasPitchData(true);
	}

	@Override
	protected void analyse(ActionEvent event)
	{
		analysePitch(chosenPitchAlgorithm);
		setComplete();
	}

	@Override
	protected void preview(ActionEvent event)
	{

	}
}