import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene (root,691,491);
			Parent content = FXMLLoader.load(getClass().getClassLoader().getResource("GUIdesign.fxml"));
			root.setCenter(content);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Image Score Annotator");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
