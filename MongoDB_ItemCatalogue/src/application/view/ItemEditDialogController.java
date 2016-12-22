package application.view;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Locale;

import application.MainApp;
import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ItemEditDialogController {
	@FXML private TextField numberField;
    @FXML private TextField descrField;
    @FXML private TextField salespriceField;
    @FXML private ComboBox<String> productGroupComboBox;
    
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

        //empty item for saving all vendors for edited item
        tmpVendorItem = new Item();
        
        //add product group list to combo button
        ObservableList<String> productGroupList = ProductGroupHelper.getProductGroupsAsStringList();
        productGroupList.add("");
        productGroupComboBox.setItems(productGroupList);
    	
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
        salespriceField.setText(item.getSalesprice().toString().replace(".", ","));
        if (item.getProductGroup() != "")
        	productGroupComboBox.getSelectionModel().select(item.getProductGroup());
        
        vendorSubData = ItemHelper.getVendorsOfItem(item);
        vendorSubTable.setItems(vendorSubData);
        
        ArrayList<Vendor> vendorList = new ArrayList<Vendor>();
        vendorList.addAll(item.getVendors()); 
        tmpVendorItem.setVendors(vendorList);
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
            if (productGroupComboBox.getSelectionModel().getSelectedItem() == null)
            	item.setProductGroup("");
            else
            	item.setProductGroup(productGroupComboBox.getSelectionModel().getSelectedItem());
            item.setVendors(tmpVendorItem.getVendors());
            
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

	        if (itemContainsVendor(controller.getVendor(),tmpVendorItem)) {
	        	//vendor already selected
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Lieferant bereits vorhanden");
	            alert.setHeaderText("Lieferant bereits vorhanden!");
	            alert.showAndWait();
	            
	        } else {
	        	//Add vendor to all vendors buffer
	        	tmpVendorItem.addVendor(controller.getVendor());
	        	
	        	//Add vendor to table
	        	vendorSubData.add(controller.getVendor());
		        vendorSubTable.setItems(vendorSubData);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    @FXML
    private void handleDeleteVendor() {
    	handleDeleteVendor(item);
    }
    
    private void handleDeleteVendor(Item item) {   
    	Vendor vendor = vendorSubTable.getSelectionModel().getSelectedItem();
    	
        int selectedIndex = vendorSubTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	ListIterator<Vendor> listIterator = tmpVendorItem.getVendors().listIterator();
        	while(listIterator.hasNext()) {
        		Vendor v = listIterator.next();
        		if (v.getObjectId().toString().contentEquals(vendor.getObjectId().toString())) {
        			listIterator.remove();
        			break;
        		}
        	}
        	vendorSubTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Keinen Lieferanten ausgewählt!");
            alert.setContentText("Bitte Lieferanten in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
    
    private boolean itemContainsVendor(Vendor v, Item tmpVendorItem) {
    	for (Vendor vendor : tmpVendorItem.getVendors()) {
    		if (vendor.getCode().equals(v.getCode())) {
    			return true;
    		}
    	}
    	return false;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        //check syntax
        if (numberField.getText() == null || numberField.getText().length() == 0) {
            errorMessage += "Keine gültige Nummer!\n"; 
        }
        
        if (descrField.getText() == null || descrField.getText().length() == 0) {
            errorMessage += "Keine gültige Beschreibung!\n"; 
        }

        if (salespriceField.getText() == null || salespriceField.getText().length() == 0) {
            errorMessage += "Kein gültiger Verkaufspreis!\n";
        } else {
            NumberFormat nf = DecimalFormat.getInstance(Locale.GERMAN);
            Number number = 0;
    		try {
    			number = nf.parse(salespriceField.getText());
    			salespriceField.setText(Double.parseDouble(number + "") + "");
    		} catch (ParseException e) {
    			errorMessage += "Kein gültiger Verkaufspreis!\n";
    		}
        }
        
        //check database (first build tmpItem which will be inserted)
        String tmpProductGroup;
        if (productGroupComboBox.getSelectionModel().getSelectedItem() == null)
        	tmpProductGroup = "";
        else
        	tmpProductGroup = productGroupComboBox.getSelectionModel().getSelectedItem();
        
        Item tmpItem = new Item(
        		item.getObjectId().toString(), numberField.getText(), descrField.getText(),
        		Double.parseDouble(salespriceField.getText()), tmpProductGroup, tmpVendorItem.getVendors());
        
        if (ItemHelper.itemAlreadyExists(tmpItem)) {
        	errorMessage += "Artikelnummer bereits vergeben!\n";
        }
        
        //give return value
        if (errorMessage.length() == 0) {
            return true;
        } else {
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
