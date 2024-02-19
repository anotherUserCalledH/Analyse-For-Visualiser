package musicanalysis.gui.panels;

import musicanalysis.algorithms.OnsetAlgorithm;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.gui.panels.model.OnsetPanelModel;
import musicanalysis.gui.windows.LaunchNewWindow;

import java.io.IOException;
import java.nio.file.Path;


public class OnsetAnalysisPanel extends AnalysisPanel<OnsetAlgorithm>
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
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}