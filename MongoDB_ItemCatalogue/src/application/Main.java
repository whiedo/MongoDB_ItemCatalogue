package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            BorderPane borderPane = (BorderPane) loader.load();
			
            Scene scene = new Scene(borderPane);
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Artikel Katalog");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			showItemOverview(borderPane);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public void showItemOverview(BorderPane borderPane) {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ItemOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();
            // Set item overview into the center of root layout.
            borderPane.setCenter(itemOverview);
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
