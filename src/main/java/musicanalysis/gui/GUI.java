package musicanalysis.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Init stuff
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends Application
{
	static final int WIDTH = 700;
	static final int HEIGHT = 700;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		FXMLLoader fxmlLoader1 = new FXMLLoader(GUI.class.getResource("view.fxml"));
		Parent root = fxmlLoader1.load();
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Controller controller = fxmlLoader1.getController();

		primaryStage.setTitle("GUI :)");
		primaryStage.setScene(scene);
		primaryStage.show();


		//Init stuff
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.WARNING);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
