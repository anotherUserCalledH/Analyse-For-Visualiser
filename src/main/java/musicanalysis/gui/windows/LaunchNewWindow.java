package musicanalysis.gui.windows;

import javafx.scene.Parent;
import musicanalysis.algorithms.AnalysisAlgorithm;
import musicanalysis.evaluate.EvaluationModel;
import musicanalysis.gui.GUI;
import musicanalysis.preview.PreviewVisualiser;
import musicanalysis.preview.VisualiserEffect;
import musicanalysis.preview.BeatMarker;
import musicanalysis.preview.TimestampMarker;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.nio.file.Path;

public class LaunchNewWindow
{
	public enum AlgorithmType
	{
		BEAT, PITCH, ONSET
	}

	//Needs refactoring
	public static VisualiserEffect launchPreview(Path songPath, AlgorithmType previewType) throws IOException
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(GUI.class.getResource("windows/preview_window.fxml"));
		Pane root = fxmlLoader1.load();
		PreviewController previewController1 = fxmlLoader1.getController();
		ObservableList<Node> list = root.getChildren();

		Canvas canvas1 = (Canvas) list.get(0);
		GraphicsContext context = canvas1.getGraphicsContext2D();
		int canvasWidth = (int) canvas1.getWidth();
		int canvasHeight = (int) canvas1.getHeight();

		VisualiserEffect effect = null;

		switch(previewType)
		{
			case BEAT:
				effect = new BeatMarker(context, canvasWidth, canvasHeight);
				break;

			case ONSET:
				effect = new TimestampMarker(context, canvasWidth, canvasHeight);
				break;
		}

		PreviewVisualiser previewVisualiser1 = new PreviewVisualiser(context, canvasWidth, canvasHeight, songPath, effect);	
		previewController1.setPreviewVisualiser(previewVisualiser1);

		Stage stage1 = new Stage();
		stage1.setTitle("Preview");
		stage1.setScene(new Scene(root));
		stage1.initModality(Modality.APPLICATION_MODAL);
		stage1.setResizable(false);
		stage1.setOnHidden(event -> previewController1.shutdown());
		stage1.show();

		return effect;
	}

	public static void launchBeatPreview(Path songPath, float[] beatArray) throws IOException
	{
		BeatMarker effect = (BeatMarker) launchPreview(songPath, AlgorithmType.BEAT);
		effect.setData(beatArray);
	}

	public static void launchOnsetPreview(Path songPath, float[] onsetArray) throws IOException
	{
		TimestampMarker effect = (TimestampMarker) launchPreview(songPath, AlgorithmType.ONSET);
		effect.setData(onsetArray);
	}

	public static void launchPitchPreview(Path songPath, int[] pitchArray) throws IOException
	{
	}

	public static <T extends AnalysisAlgorithm> void launchEvaluation(EvaluationModel<T> evalModel, String windowTitle) throws IOException
	{
		int width = 1000;
		int height = 500;

		FXMLLoader fxmlLoader2 = new FXMLLoader(GUI.class.getResource("windows/evaluation_window.fxml"));
		Parent root = fxmlLoader2.load();
		EvaluationController<T> evalController1 = fxmlLoader2.getController();
		evalController1.setModel(evalModel);
		Stage stage1 = new Stage();
		stage1.setTitle(windowTitle);
		stage1.setScene(new Scene(root, width, height));
		stage1.initModality(Modality.APPLICATION_MODAL);
		stage1.show();
	}
}
