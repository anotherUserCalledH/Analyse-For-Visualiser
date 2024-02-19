package musicanalysis.gui.panels;

import musicanalysis.algorithms.BeatAlgorithm;
import musicanalysis.gui.windows.LaunchNewWindow;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.windows.AnalysisData;
import musicanalysis.gui.panels.model.BeatPanelModel;

import java.io.IOException;
import java.nio.file.Path;

public class BeatAnalysisPanel extends AnalysisPanel<BeatAlgorithm>
{
	public BeatAnalysisPanel() throws Exception
	{
		super(new BeatPanelModel(), "BEAT");
	}

	@Override
	public void updateAnalysisStatus(SavedSong selectedSong)
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
	protected void buildPreview(AnalysisData dataToPreview, Path songPath)
	{
		float[] beatData = (float[]) dataToPreview.getData();
		try
		{
			LaunchNewWindow.launchBeatPreview(songPath, beatData);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}