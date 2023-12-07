package musicanalysis.gui.panels;

import musicanalysis.gui.SavedSong;
import musicanalysis.AnalyseMusic;
import musicanalysis.io.LoadData;
import musicanalysis.gui.LaunchNewWindow;

import javafx.event.ActionEvent;
import java.nio.file.Path;


public class OnsetAnalysisPanel extends AnalysisPanel
{
	public OnsetAnalysisPanel() throws Exception
	{
		setHeaderLabel("ONSET");
	}

	@Override
	protected void initialiseChoiceBox()
	{

	}

	@Override
	public void updateAnalysisStatus()
	{
		if(selectedSong.checkHasSeparatedAudio() == true)
		{
			if(selectedSong.checkHasOnsetData() == true)
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
	}

	protected void analyseOnsets()
	{
		Path selectedSongFile = selectedSong.getVocalsFile();
		float[] onsetData = AnalyseMusic.detectOnsets(selectedSongFile);

		Path onsetDataFile = selectedSong.getOnsetDataFile();
		LoadData.writeOnsetData(onsetData, onsetDataFile);
		selectedSong.setHasOnsetData(true);
	}

	protected float[] getOnsetData()
	{
		Path onsetDataFile = selectedSong.getOnsetDataFile();
		float[] onsetData = LoadData.readOnsetData(onsetDataFile);

		return onsetData;
	}

	@Override
	protected void analyse(ActionEvent event)
	{
		analyseOnsets();
		setComplete();
	}

	@Override
	protected void preview(ActionEvent event)
	{
		Path songPath = selectedSong.getSongFile();
		float[] onsetData = getOnsetData();

		try
		{
			LaunchNewWindow.launchOnsetPreview(songPath, onsetData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}