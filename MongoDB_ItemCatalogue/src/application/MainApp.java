package application;
	
import java.io.IOException;

import com.aquafx_project.AquaFx;

import application.model.Item;
import application.model.ProductGroup;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.ItemSalesHelper;
import application.view.ItemEditDialogController;
import application.view.ItemOverviewController;
import application.view.ItemSalesController;
import application.view.ProductGroupEditDialogController;
import application.view.ProductGroupItemStaticticsController;
import application.view.ProductGroupOverviewController;
import application.view.RootLayoutController;
import application.view.VendorEditDialogController;
import application.view.VendorOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        
        //automatically style whole application with aquafx
        AquaFx.style();
        
        //add icon
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
	
	public void showItemSales() {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ItemSales.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Verkäufe erfassen");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:resources/images/item.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ItemSalesController controller = loader.getController();
	        controller.setMainApp(this);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	        ItemSalesHelper.updateMultipleItemSales(controller.getItemSalesList());
	            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showProductGroupItemStatistics() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProductGroupItemStatistics.fxml"));
            AnchorPane productGroupItemStatistics = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Artikel nach Produktgruppen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(productGroupItemStatistics);
            dialogStage.setScene(scene);
            
            ProductGroupItemStaticticsController controller = loader.getController();
            
            dialogStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showTopTenStatistics() {
        try {
            // Load item overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TopTenStatistics.fxml"));
            AnchorPane salesStatistics = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Verkaufsstatistik");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(salesStatistics);
            dialogStage.setScene(scene);
            
            dialogStage.show();
        } catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return null;
	}
}
