package musicanalysis;

public class FrequencyToMidi
{
	public static int getMidiNoteNumber(double frequency)
	{
		int midiNoteNumber = -1;

		if(frequency >= 27.50 && frequency <= 13289.75)
		{
			midiNoteNumber = calculateNote(frequency);
		}

		return midiNoteNumber;
	}

	private static boolean getClosestNumber(double lowerBound, double upperBound, double number)
	{
		boolean closestToUpper;

		if(upperBound - number <= number - lowerBound)
		{
			closestToUpper = true;
		}
		else
		{
			closestToUpper = false;
		}

		return closestToUpper;
	}

	private static int calculateNote(double frequency)
	{
		int numberOfOctaves = 0;

		while(frequency >= 55)
		{
			frequency /= 2d;
			numberOfOctaves++;
		}
		
		int midiNoteNumber = -1;
		boolean closestToUpper;

		if(frequency > 51.91d)
		{
			closestToUpper = getClosestNumber(51.91, 55, frequency);

			if(closestToUpper == true)
			{
				midiNoteNumber = 33;
			}
			else
			{
				midiNoteNumber = 32;
			}
		}

		else if(frequency > 49d)
		{
			closestToUpper = getClosestNumber(49, 51.91, frequency);

			if(closestToUpper == true)
			{
				midiNoteNumber = 32;
			}
			else
			{
				midiNoteNumber = 31;
			}
		}

		else if(frequency > 46.25d)
		{
			closestToUpper = getClosestNumber(46.25, 49, frequency);

			if(closestToUpper == true)
			{
				midiNoteNumber = 31;
			}
			else
			{
				midiNoteNumber = 30;
			}
		}

		else if(frequency > 43.65d)
		{
			closestToUpper = getClosestNumber(43.65, 46.25, frequency);

			if(closestToUpper == true)
			{
				midiNoteNumber = 30;
			}
			else
			{
				midiNoteNumber = 29;
			}
		}

		else if(frequency > 41.20d)
		{
			closestToUpper = getClosestNumber(41.20, 43.65, frequency);

			if(closestToUpper == true)
			{
				midiNoteNumber = 29;
			}
			else
			{
				midiNoteNumber = 28;
			}
		}

		else if(frequency > 38.89d)
		{
			closestToUpper = getClosestNumber(38.89, 41.20, frequency);
			
			if(closestToUpper == true)
			{
				midiNoteNumber = 28;
			}
			else
			{
				midiNoteNumber = 27;
			}
		}

		else if(frequency > 36.71d)
		{
			closestToUpper = getClosestNumber(36.71, 38.89, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 27;
			}
			else
			{
				midiNoteNumber = 26;
			}
		}

		else if(frequency > 34.65d)
		{
			closestToUpper = getClosestNumber(34.65, 36.71, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 26;
			}
			else
			{
				midiNoteNumber = 25;
			}
		}

		else if(frequency > 32.70d)
		{
			closestToUpper = getClosestNumber(32.70, 34.65, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 25;
			}
			else
			{
				midiNoteNumber = 24;
			}
		}	

		else if(frequency > 30.87d)
		{
			closestToUpper = getClosestNumber(30.87, 32.70, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 24;
			}
			else
			{
				midiNoteNumber = 23;
			}
		}

		else if(frequency > 29.14d)
		{
			closestToUpper = getClosestNumber(29.14, 30.87, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 23;
			}
			else
			{
				midiNoteNumber = 22;
			}
		}	

		else if(frequency >= 27.50d)
		{
			closestToUpper = getClosestNumber(27.50, 29.14, frequency);
			if(closestToUpper == true)
			{
				midiNoteNumber = 22;
			}
			else
			{
				midiNoteNumber = 21;
			}
		}

		midiNoteNumber += (numberOfOctaves * 12);

		return midiNoteNumber;
	}
}