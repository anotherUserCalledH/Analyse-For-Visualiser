package musicanalysis.gui.panels;

import musicanalysis.gui.SavedSong;
import musicanalysis.PitchDetectionAlgorithm;
import musicanalysis.io.LoadData;

//Should be moved to AnalysisPanel at some point
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import java.nio.file.Path;


public class PitchAnalysisPanel extends AnalysisPanel<PitchDetectionAlgorithm>
{
	private PitchDetectionAlgorithm chosenPitchAlgorithm;

	public PitchAnalysisPanel() throws Exception
	{
		setHeaderLabel("PITCH");
	}

	public void initialize()
	{
		model = new PitchAnalysisModel();
		super.initialize();
	}

	@Override
	public void setPluginDirectory(Path pluginDirectory)
	{
		model.loadPlugins(pluginDirectory);
	}

	@Override
	protected void initialiseChoiceBox()
	{
		ObservableList<PitchDetectionAlgorithm> algorithms = model.getAlgorithms();
		
		algorithmsChoiceBox.setItems(algorithms);
		algorithmsChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PitchDetectionAlgorithm>()
		{
			@Override
			public void changed(ObservableValue<? extends PitchDetectionAlgorithm> observable, PitchDetectionAlgorithm oldValue, PitchDetectionAlgorithm newValue)
			{
			if (newValue != null)
				{
					chosenPitchAlgorithm = (PitchDetectionAlgorithm) algorithmsChoiceBox.getSelectionModel().getSelectedItem();
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

	protected void analysePitch(PitchDetectionAlgorithm pitchAlgorithm, SavedSong selectedSong)
	{
		Path selectedSongFile = selectedSong.getVocalsFile();
		int[] pitchData = pitchAlgorithm.getPitchArray(selectedSongFile);

		Path pitchDataFile = selectedSong.getPitchDataFile();
		LoadData.writePitchData(pitchData, pitchDataFile);
		selectedSong.setHasPitchData(true);
	}

	@Override
	protected void analyse(ActionEvent event)
	{
		analysePitch(chosenPitchAlgorithm, selectedSong);
		setComplete();
	}

	@Override
	protected void preview(ActionEvent event)
	{

	}
}