package musicanalysis.gui.panels;

import musicanalysis.gui.LaunchNewWindow;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.panels.model.AnalysisData;
import musicanalysis.gui.panels.model.PitchPanelModel;

import java.nio.file.Path;


public class PitchAnalysisPanel extends AnalysisPanel
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
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}