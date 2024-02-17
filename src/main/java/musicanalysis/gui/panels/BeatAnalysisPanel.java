package musicanalysis.gui.panels;

import musicanalysis.gui.LaunchNewWindow;
import musicanalysis.AnalyseMusic;
import musicanalysis.gui.SavedSong;
import musicanalysis.gui.panels.model.AnalysisData;
import musicanalysis.gui.panels.model.BeatPanelModel;
import musicanalysis.io.LoadData;

import javafx.event.ActionEvent;
import java.nio.file.Path;

public class BeatAnalysisPanel extends AnalysisPanel
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
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}