package musicanalysis;

import be.tarsos.dsp.beatroot.BeatRootOnsetEventHandler;
import be.tarsos.dsp.beatroot.AgentList;
import be.tarsos.dsp.beatroot.Induction;
import be.tarsos.dsp.beatroot.Agent;
import be.tarsos.dsp.beatroot.EventList;
import be.tarsos.dsp.beatroot.Event;

import java.util.LinkedList;

public class BeatRetriever extends BeatRootOnsetEventHandler
{
	private final EventList onsetList = new EventList();
	private Agent beatAgent;
	
	@Override
	public void handleOnset(double time, double salience)
	{
		double roundedTime = Math.round(time *100 )/100.0;
		Event e = newEvent(roundedTime,0);
		e.salience = salience;
		onsetList.add(e);		
	}

	private Event newEvent(double time, int beatNum)
	{
		return new Event(time,time, time, 56, 64, beatNum, 0, 1);
	}

	public boolean beatInduction()
	{
		AgentList agents = null;
		agents = Induction.beatInduction(onsetList);
		agents.beatTrack(onsetList, -1);
		beatAgent = agents.bestAgent();

		boolean inductionSuccessful = false;
		if(beatAgent != null)
		{
			beatAgent.fillBeats(-1.0);
			inductionSuccessful = true;
		}

		return inductionSuccessful;
	}	

	public float[] getBeatArray()
	{	
		float[] beatArray = null;

		if(beatAgent != null)
		{
			EventList beatEventList = beatAgent.events;
			LinkedList<Event> pitchLinkedList = beatEventList.l;
			beatArray = new float[pitchLinkedList.size()];

			for(int index = 0; index < pitchLinkedList.size(); index++)
			{
				beatArray[index] = (float) pitchLinkedList.get(index).keyDown;
			}
		}

		return beatArray;
	}
}