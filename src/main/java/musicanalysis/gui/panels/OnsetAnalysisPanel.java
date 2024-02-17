package musicanalysis.gui.panels;

import musicanalysis.AnalyseMusic;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.panels.model.AnalysisData;
import musicanalysis.gui.panels.model.OnsetPanelModel;
import musicanalysis.io.LoadData;
import musicanalysis.gui.LaunchNewWindow;

import javafx.event.ActionEvent;
import java.nio.file.Path;


public class OnsetAnalysisPanel extends AnalysisPanel
{
	public OnsetAnalysisPanel() throws Exception
	{
		super(new OnsetPanelModel(), "ONSET");
	}


	@Override
	public void updateAnalysisStatus(SavedSong selectedSong)
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
	@Override
	protected void buildPreview(AnalysisData dataToPreview, Path songPath)
	{
		float[] onsetData = (float[]) dataToPreview.getData();
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