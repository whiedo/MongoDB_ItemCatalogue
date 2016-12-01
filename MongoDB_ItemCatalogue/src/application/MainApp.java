package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import application.model.*;
import application.view.ItemEditDialogController;
import application.view.ItemOverviewController;
public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane borderPane;
	private ObservableList<Item> itemData = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		try {
	        this.primaryStage = primaryStage;
	        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            borderPane = (BorderPane) loader.load();
			
            Scene scene = new Scene(borderPane);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.getIcons().add(new Image("file:resources/images/item.png"));
            
			primaryStage.setTitle("Artikel Katalog");
			primaryStage.setScene(scene);
			primaryStage.show();
			
	        itemData.add(new Item("Hans", "Muster","Muster", 0));
	        itemData.add(new Item("Ruth", "Mueller","Muster", 2));
	        itemData.add(new Item("Heinz", "Kurz","Muster", 2));
	        itemData.add(new Item("Cornelia", "Meier","Muster", 2));
	        itemData.add(new Item("Werner", "Meyer","Muster", 2));
	        itemData.add(new Item("Lydia", "Kunz","Muster", 2));
	        itemData.add(new Item("Anna", "Best","Muster", 2));
	        itemData.add(new Item("Stefan", "Meier","Muster", 2));
	        itemData.add(new Item("Martin", "Mueller","Muster", 2));
			
			showItemOverview();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public void showItemOverview() {
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

	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean showItemEditDialog(Item item) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ItemEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Artikel bearbeiten");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:resources/images/item.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ItemEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setItem(item);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
