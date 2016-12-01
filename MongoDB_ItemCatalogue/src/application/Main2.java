package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main2 extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();
        showItemOverview();
	}
	
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
		try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
			

			
            // Show the scene containing the root layout.
			
            Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Shows the item overview inside the root layout.
     */
    public void showItemOverview() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ItemOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();
            // Set item overview into the center of root layout.
            rootLayout.setCenter(itemOverview);
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public static void main(String[] args) {
		launch(args);
	}
}
