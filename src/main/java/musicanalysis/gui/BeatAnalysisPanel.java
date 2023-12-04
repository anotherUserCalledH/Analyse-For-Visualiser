package musicanalysis.gui;

import musicanalysis.io.SavedSong;
import musicanalysis.AnalyseMusic;
import musicanalysis.io.LoadData;

import javafx.event.ActionEvent;
import java.nio.file.Path;

public class BeatAnalysisPanel extends AnalysisPanel
{
	public BeatAnalysisPanel() throws Exception
	{
		setHeaderLabel("BEAT");
	}

	@Override
	public void updateAnalysisStatus()
	{
		if(selectedSong.checkHasBeatData() == true)
		{
			setComplete();
		}
		else
		{
			setReady();
		}
	}

	@Override
	protected void initialiseChoiceBox()
	{
	}

	protected boolean analyseBeat()
	{
		Path selectedSongFile = selectedSong.getSongFile();
		float[] beatData = AnalyseMusic.detectBeat(selectedSongFile);
		boolean detectionSuccessful = false;

		if(beatData != null)
		{
			Path beatDataFile = selectedSong.getBeatDataFile();
			LoadData.writeBeatData(beatData, beatDataFile);
			//Should probably in boolean return value to LoadData to check for errors
			selectedSong.setHasBeatData(true);
			detectionSuccessful = true;
		}

		return detectionSuccessful;
	}

	protected float[] getBeatData()
	{
		Path beatDataFile = selectedSong.getBeatDataFile();
		float[] beatData = LoadData.readBeatData(beatDataFile);

		return beatData;
	}

	@Override
	protected void analyse(ActionEvent event)
	{
		boolean detectionSuccessful = analyseBeat();
		if(detectionSuccessful) { setComplete(); }
		else { setFailed(); }
	}

	@Override
	protected void preview(ActionEvent event)
	{
		Path songPath = selectedSong.getSongFile();
		float[] beatData = getBeatData();

		try
		{
			LaunchPreviewWindow.launchBeatPreview(songPath, beatData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}