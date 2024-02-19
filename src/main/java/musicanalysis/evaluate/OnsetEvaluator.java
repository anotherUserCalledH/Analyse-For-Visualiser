//package musicanalysis.evaluate;
//
//import musicanalysis.algorithms.AnalysisAlgorithm;
//import musicanalysis.algorithms.OnsetAlgorithm;
//import musicanalysis.io.IOData;
//import musicanalysis.io.LoadData;
//
//import java.nio.file.Path;
//import java.util.ArrayList;
//
//public class OnsetEvaluator implements Evaluator
//{
//	private final String AnnotationDirectory = "onset";
//	private final String AnnotationType = ".json";
//
//	@Override
//	public String getAnnotationDirectory()
//	{
//		return AnnotationDirectory;
//	}
//
//	@Override
//	public String getAnnotationType()
//	{
//		return AnnotationType;
//	}
//
//	@Override
//	public EvalScore evaluate(AnalysisAlgorithm algorithm, Path evalAudio, Path evalAnnotation)
//	{
//		OnsetAlgorithm currentAlgorithm = (OnsetAlgorithm) algorithm;
//		float[] detectedOnsets = algorithm.analyse(evalAudio);
//		float[] validationOnsets = LoadData.readOnsetData(evalAnnotation);
//
//		return evaluate(detectedOnsets, validationOnsets);
//	}
//
//	public OnsetEvalScore evaluate(float[] detectedOnsets, float[] validationOnsets)
//	{
//		float leeway = 0.2f;
//
//		ArrayList<Float> correctOnsets = new ArrayList<Float>();
//		ArrayList<Float> spuriousOnsets = new ArrayList<Float>();
//		ArrayList<Float> missedOnsets = new ArrayList<Float>();
//
//		int currentIndex = 0;
//		float onsetLowerBound = validationOnsets[currentIndex] - leeway;
//		float onsetUpperBound = validationOnsets[currentIndex] + leeway;
//
//		for(float onset : detectedOnsets)
//		{
//			boolean breakLoop = false;
//			while(breakLoop == false)
//			{
//				if(currentIndex >= validationOnsets.length)
//				{
//					spuriousOnsets.add(onset);
//					breakLoop = true;
//				}
//
//				else
//				{
//					onsetLowerBound = validationOnsets[currentIndex] - leeway;
//					onsetUpperBound = validationOnsets[currentIndex] + leeway;
//
//					if(onset < onsetLowerBound)
//					{
//						spuriousOnsets.add(onset);;
//						breakLoop = true;
//					}
//
//					else if(onset > onsetUpperBound)
//					{
//						missedOnsets.add(validationOnsets[currentIndex]);
//						currentIndex++;
//					}
//
//					else if(onset >= onsetLowerBound && onset <= onsetUpperBound)
//					{
//						correctOnsets.add(onset);
//						currentIndex++;
//						breakLoop = true;
//					}
//				}
//			}
//		}
//
//		for(int index = currentIndex; index < validationOnsets.length; index++)
//		{
//			missedOnsets.add(validationOnsets[index]);
//		}
//
//		return new OnsetEvalScore(validationOnsets.length, detectedOnsets.length, correctOnsets.size(), spuriousOnsets.size());
//	}
//}
