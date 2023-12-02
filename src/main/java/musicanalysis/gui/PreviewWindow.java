package musicanalysis.gui;

import musicanalysis.preview.PreviewVisualiser;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.fxml.FXMLLoader;

public class PreviewWindow
{
	static final int CANVAS_WIDTH = 250;
	static final int CANVAS_HEIGHT = 250; 

	public PreviewWindow() throws Exception
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(GUI.class.getResource("previewWindow.fxml"));
		Pane root = fxmlLoader1.load();
		Scene scene1 = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT + 30);
		scene1.setFill(Color.BLACK);

		ObservableList<Node> list = root.getChildren();
		Canvas canvas1 = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		list.add(0, canvas1);

		GraphicsContext context = canvas1.getGraphicsContext2D();
		PreviewVisualiser previewVisualiser1 = new PreviewVisualiser(context, CANVAS_WIDTH, CANVAS_HEIGHT);
		PreviewController previewController1 = fxmlLoader1.getController();
		previewController1.setPreviewVisualiser(previewVisualiser1);

		Stage stage1 = new Stage();
		stage1.setTitle("Preview");
		stage1.setScene(scene1);
		stage1.initModality(Modality.APPLICATION_MODAL);
		stage1.show();

		// root.setMinWidth(CANVAS_WIDTH, canvas1);
		// root.setMinHeight(canvas1, CANVAS_HEIGHT);
		// root.setMaxWidth(canvas1, CANVAS_WIDTH);
		// root.setMaxHeight(canvas1, CANVAS_HEIGHT);
	}
}