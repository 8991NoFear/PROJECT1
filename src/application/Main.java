package application;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private static Stage primaryStage;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		loadScene(primaryStage);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}
	
	private void loadScene(Stage primaryStage) throws IOException {
		loadFXMLFile(primaryStage, "Scene.fxml", "Application", StageStyle.DECORATED);
		setPrimaryStage(primaryStage);
	}
	
	private void loadFXMLFile(Stage stage, String FXMLFile, String title, StageStyle style) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		
		String workingDir = System.getProperty("user.dir");
		String path = new String(workingDir + "\\src\\application\\" + FXMLFile);
		FileInputStream is = new FileInputStream(path);
		loader.load(is);
		
		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.initStyle(style);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	private void setPrimaryStage(Stage primaryStage) {
		Main.primaryStage = primaryStage;
	}
	
	@Override
	public void stop() {
		
	}
}
