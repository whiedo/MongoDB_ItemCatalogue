package application;
	
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import application.model.*;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.view.ItemEditDialogController;
import application.view.ItemOverviewController;
import application.view.ProductGroupEditDialogController;
import application.view.ProductGroupOverviewController;
import application.view.RootLayoutController;
import application.view.VendorEditDialogController;
import application.view.VendorOverviewController;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane borderPane;
	
	public static void main(String[] args) {
		application.mongoDBInterface.DatabaseManager.initDatabase();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
	        this.primaryStage = primaryStage;
	        
	        loadRootLayoutAndDoStyling();
			
			showItemOverview();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    private void loadRootLayoutAndDoStyling() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
        borderPane = (BorderPane) loader.load();
		
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(STYLESHEET_CASPIAN);
        primaryStage.getIcons().add(new Image("file:resources/images/item.png"));
        
        RootLayoutController rootController = loader.getController();
        rootController.setMainApp(this);
        
		primaryStage.setTitle("Artikelverwaltung");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showItemOverview() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ItemOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();
            itemOverview.getStylesheets().add(STYLESHEET_CASPIAN);
            
            // Set item overview into the center of root layout.
            borderPane.setCenter(itemOverview);
            
            ItemOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showProductGroupOverview() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductGroupOverview.fxml"));
            AnchorPane productGroupOverview = (AnchorPane) loader.load();
            productGroupOverview.getStylesheets().add(STYLESHEET_CASPIAN);
            
            // Set item overview into the center of root layout.
            borderPane.setCenter(productGroupOverview);
            
            ProductGroupOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showVendorOverview() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/VendorOverview.fxml"));
            AnchorPane vendorOverview = (AnchorPane) loader.load();
            vendorOverview.getStylesheets().add(STYLESHEET_CASPIAN);
            
            // Set item overview into the center of root layout.
            borderPane.setCenter(vendorOverview);
            
            VendorOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public boolean showProductGroupEditDialog(ProductGroup productGroup) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ProductGroupEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Produktgruppe bearbeiten");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:resources/images/item.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ProductGroupEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setProductGroup(productGroup);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean showVendorEditDialog(Vendor vendor) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/VendorEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Lieferant bearbeiten");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:resources/images/item.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        VendorEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setVendor(vendor);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return null;
	}
}
