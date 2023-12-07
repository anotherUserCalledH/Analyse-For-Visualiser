//Henry Burston
//Music Visualiser v3 2023

//Used for testing pitch data

package project.io;

import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaMessage;
import java.nio.charset.StandardCharsets; 
import java.text.DecimalFormat;


public class LoadMIDI
{
	public enum TrackType
	{
		MELODY, BASSLINE
	}

	private static final int SAMPLE_RATE = 44100;

	private static int[] convertToPitchArray(long noteData[][], int noFrames, double millisecondsPerTick, float windowSize, boolean includeNoteEndings) //needs to be refactored once a unit test is created
	{
		int[] pitchArray = new int[noFrames];
		int currentEvent = 0;

		boolean firstTime = true;
		double timeOfFirstEvent = noteData[currentEvent][0] * millisecondsPerTick;


		for(int frame = 0; frame < noFrames; frame++)
		{
			if(firstTime == true)
			{
				if((frame * (windowSize)) > timeOfFirstEvent)
				{
					firstTime = false;
				}
				else
				{
					pitchArray[frame] = -1;
				}
			}
			if(firstTime != true)
			{
				double timeOfNextEvent = noteData[currentEvent + 1][0] * millisecondsPerTick;
				double frameTimeStamp = frame * (windowSize);

				if(frameTimeStamp > timeOfNextEvent)
				{
					currentEvent++;
					firstTime = false;
				}

				if(firstTime != true)
				{
					if((noteData[currentEvent][1] == -1) && (includeNoteEndings == false)) // removes data about note endings (except when there is a long silence), to make midi pitch data consistent with detected pitch data
					{
						double timeOfNoteEnding = noteData[currentEvent][0] * millisecondsPerTick;
						double timeOfNextNote = noteData[currentEvent+1][0] * millisecondsPerTick;
						double timeDifference = timeOfNextNote - timeOfNoteEnding;

						if(timeDifference < 62.5) //selected threshold is 1/16 of a second
						{
							currentEvent++;
						}
					}
				}
		
				pitchArray[frame] = (int) noteData[currentEvent][1]; //the pitch in noteData is a long for the timestamps, not for the pitch, so it can be converted to int
			}
		}

		return pitchArray;
	}

	private static double getResolution(Sequence inputSequence, float bpm)
	{
		float divisionType = inputSequence.getDivisionType();
		double millisecondsPerTick;

		if(divisionType == Sequence.PPQ)
		{
			double ticksPerBeat = inputSequence.getResolution();
			double ticksPerMinute = ticksPerBeat * bpm;
			double ticksPerMillisecond = ticksPerMinute/60000d;
			millisecondsPerTick = 60000d/ticksPerMinute;
		}
		else
		{
			millisecondsPerTick = -1;
			System.out.println("Error: Invalid MIDI format");
		}

		return millisecondsPerTick;
	}

	private static long[][] getNoteData(Track currentTrack)
	{
		int noEvents = currentTrack.size();
		long noteData[][] = new long[noEvents + 1][2]; // [][0] for timestamp, [][1] for pitch
		int highestNote = -1;
		int lowestNote = -1;

		int previousNoteType = -1; //removable, added solely to check for overlapping notes
		int noteEventIndex = 0;
		for(int event = 0; event < noEvents; event++)
		{
			MidiEvent currentEvent = currentTrack.get(event);
			MidiMessage currentMessage = currentEvent.getMessage();
			long timeStampInTicks = currentEvent.getTick();

			if(currentMessage instanceof ShortMessage)
			{
				ShortMessage noteEvent = (ShortMessage) currentMessage;

				if(noteEvent.getCommand() == ShortMessage.NOTE_ON)
				{
					int notePitch = noteEvent.getData1();
					noteData[noteEventIndex][0] = timeStampInTicks;
					noteData[noteEventIndex][1] = notePitch;

					previousNoteType = ShortMessage.NOTE_ON; //added  to check for overlapping notes
				}
				else if(noteEvent.getCommand() == ShortMessage.NOTE_OFF)
				{
					noteData[noteEventIndex][0] = timeStampInTicks;
					noteData[noteEventIndex][1] = -1;

					if(previousNoteType == ShortMessage.NOTE_OFF){System.out.println("Possible error detected: overlapping notes at " + timeStampInTicks);}
					previousNoteType = ShortMessage.NOTE_OFF; //again, added to check for overlapping notes
				}
				noteEventIndex++;
			}

			noteData[noEvents][0] = 2147483647; //fills the buffer which was added to avoid outOfBounds exception
		}

		return noteData;
	}

	private static Track getTrack(Track[] inputTracks, TrackType chosenTrack)
	{
		int requestedTrack = -1;
		String trackType = "Melody";  //track names in midi file MUST match these Strings

		if(chosenTrack == TrackType.BASSLINE)
		{
			trackType = "Bassline"; //See previous comment
		}

		for(int track = 0; track < inputTracks.length; track++)
		{
			Track currentTrack = inputTracks[track];
			int noEvents = currentTrack.size();
			for(int event = 0; event < noEvents; event++)
			{
				MidiEvent currentEvent = currentTrack.get(event);
				MidiMessage currentMessage = currentEvent.getMessage();
				if(currentMessage instanceof MetaMessage)
				{
					MetaMessage currentMetaMessage = (MetaMessage) currentMessage;

					if(currentMetaMessage.getType() == 3)
					{
						byte[] data = currentMetaMessage.getData();
						String trackName = new String(data, StandardCharsets.UTF_8);
						if(trackName.equals(trackType))
						{
							requestedTrack = track;
							break;
						}
					}
				}
			}
		}

		if(requestedTrack == -1)
		{
			System.out.println("Error: Melody not found. Please ensure the track is named 'Melody' or 'Bassline'");
		}

		return inputTracks[requestedTrack];
	}

	private static float getBPM(Sequence inputSequence)
	{
		Sequencer inputSequencer = null;

		try
		{
			inputSequencer = MidiSystem.getSequencer();
			inputSequencer.setSequence(inputSequence);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		float bpm = inputSequencer.getTempoInBPM();
		return bpm;
	}

	public static int[] importMIDI(String inputFilePath, TrackType chosenTrack, boolean includeNoteEndings)
	{
		Sequence inputSequence = null;

		try
		{
			File inputFile = new File(inputFilePath);
			inputSequence = MidiSystem.getSequence(inputFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		Track[] inputTracks = inputSequence.getTracks();
		float bpm = getBPM(inputSequence);
		Track requestedTrack = getTrack(inputTracks, chosenTrack);
		long[][] noteData = getNoteData(requestedTrack);

		double millisecondsPerTick = getResolution(inputSequence, bpm);
		long lengthInTicks = inputSequence.getTickLength();
		double lengthInMilliseconds = lengthInTicks*millisecondsPerTick;
		float windowSizeMillis = (1800f/SAMPLE_RATE) / 1000; // should be the same as pitch detection window, in future an argument may be needed
		int noFrames = (int) (lengthInMilliseconds * (1d/windowSizeMillis));

		int[] pitchArray = convertToPitchArray(noteData, noFrames, millisecondsPerTick, windowSizeMillis, includeNoteEndings);

		return pitchArray;
	}

	public static int[] importMIDI(String inputFilePath, TrackType chosenTrack)
	{
		return importMIDI(inputFilePath, chosenTrack, true); //1 for melody, 0 for bassline, maybe use ENUM instead
	}
}