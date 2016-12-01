package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import application.model.*;
import application.view.ItemOverviewController;
public class MainApp extends Application {
	
	private ObservableList<Item> itemData = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            BorderPane borderPane = (BorderPane) loader.load();
			
            Scene scene = new Scene(borderPane);
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Artikel Katalog");
			primaryStage.setScene(scene);
			primaryStage.show();
			
	        itemData.add(new Item("Hans", "Muster", 0));
	        itemData.add(new Item("Ruth", "Mueller", 2));
	        itemData.add(new Item("Heinz", "Kurz", 2));
	        itemData.add(new Item("Cornelia", "Meier", 2));
	        itemData.add(new Item("Werner", "Meyer", 2));
	        itemData.add(new Item("Lydia", "Kunz", 2));
	        itemData.add(new Item("Anna", "Best", 2));
	        itemData.add(new Item("Stefan", "Meier", 2));
	        itemData.add(new Item("Martin", "Mueller", 2));
			
			showItemOverview(borderPane);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public void showItemOverview(BorderPane borderPane) {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ItemOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();
            // Set item overview into the center of root layout.
            borderPane.setCenter(itemOverview);
            
            ItemOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public ObservableList<Item> getItemData() {
        return itemData;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
