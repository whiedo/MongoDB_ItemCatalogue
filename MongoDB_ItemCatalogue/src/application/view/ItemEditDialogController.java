package application.view;

import java.io.IOException;

import application.MainApp;
import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemEditDialogController {
	@FXML private TextField numberField;
    @FXML private TextField descrField;
    @FXML private TextField descr2Field;
    @FXML private TextField salespriceField;
    
	@FXML private TableView<Vendor> vendorSubTable;
    @FXML private TableColumn<Vendor, String> codeColumn;
    @FXML private TableColumn<Vendor, String> nameColumn;
    @FXML private TableColumn<Vendor, String> addressColumn;
    @FXML private TableColumn<Vendor, String> contactColumn;

    private Stage dialogStage;
    private Item item;
    private boolean okClicked = false;
    private ObservableList<Vendor> vendorSubData;
    private Item tmpVendorItem;

    @FXML
    private void initialize() {
        vendorSubData = null;
        vendorSubTable.setItems(vendorSubData);
        
        tmpVendorItem = new Item();
    	
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().getContactProperty());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setItem(Item item) {
        this.item = item;

        numberField.setText(item.getNumber());
        descrField.setText(item.getDescription());
        salespriceField.setText(Double.toString(item.getSalesprice()));
        
        vendorSubData = ItemHelper.getVendorsOfItem(item);
        vendorSubTable.setItems(vendorSubData);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            item.setNumber(numberField.getText());
            item.setDescription(descrField.getText());
            item.setSalesprice(Double.parseDouble(salespriceField.getText()));
            
            for(Vendor v : tmpVendorItem.getVendors()) {
            	item.addVendor(v);
            }
            
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleAddVendor() {
    	handleAddVendor(item);
    }
    
    private void handleAddVendor(Item item) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/SelectVendorList.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Lieferant auswählen");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.getIcons().add(new Image("file:resources/images/item.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        SelectVendorController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        tmpVendorItem.addVendor(controller.getVendor());
	        
        	vendorSubData.add(controller.getVendor());
	        vendorSubTable.setItems(vendorSubData);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (numberField.getText() == null || numberField.getText().length() == 0) {
            errorMessage += "Keine gültige Nummer!\n"; 
        }
        if (descrField.getText() == null || descrField.getText().length() == 0) {
            errorMessage += "Keine gültige Beschreibung!\n"; 
        }

        if (salespriceField.getText() == null || salespriceField.getText().length() == 0) {
            errorMessage += "Kein gültiger Verkaufspreis!\n"; 
        } else {
            // try to parse the salesprice into double
            try {
                Double.parseDouble(salespriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Kein gültiger Verkaufspreis (Muss Zahl sein)!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte ungültige Felder korrigieren!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
