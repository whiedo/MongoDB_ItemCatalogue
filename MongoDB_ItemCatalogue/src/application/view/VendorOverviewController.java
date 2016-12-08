package application.view;

import application.MainApp;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.VendorHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class VendorOverviewController {
	@FXML private TableView<Vendor> vendorTable;
    @FXML private TableColumn<Vendor, String> codeColumn;
    @FXML private TableColumn<Vendor, String> nameColumn;
    
    @FXML private Label codeLbl;
    @FXML private Label nameLbl;
    @FXML private Label addressLbl;
    @FXML private Label contactLbl;
    
    private MainApp mainApp;
    private ObservableList<Vendor> vendorData;
    
    public VendorOverviewController () {
    }
    
    @FXML
    private void initialize() {
    	vendorData = VendorHelper.getVendors();
        vendorTable.setItems(vendorData);
    	
    	codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       
        // Clear item details.
        showVendorDetails(null);

        // Listen for selection changes and show the item details when changed.
        vendorTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showVendorDetails(newValue));
    }
    
    private void showVendorDetails(Vendor vendor) {
        if (vendor != null) {
            // Fill the labels with info from the item object.
        	codeLbl.setText(vendor.getCode());
        	nameLbl.setText(vendor.getName());
        	addressLbl.setText(vendor.getAddress().toString());
        	contactLbl.setText(vendor.getContact().toString());
        } else {
            // Person is null, remove all the text.
        	codeLbl.setText("");
        	nameLbl.setText("");
        	addressLbl.setText("");
        	contactLbl.setText("");
        }
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void handleDeleteVendor() {
        int selectedIndex = vendorTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	VendorHelper.deleteVendor(vendorTable.getSelectionModel().getSelectedItem());
            vendorTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Keinen Lieferanten ausgewählt!");
            alert.setContentText("Bitte Lieferanten in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewVendor() {
    	Vendor tmpVendor = new Vendor();
        boolean okClicked = mainApp.showVendorEditDialog(tmpVendor);
        if (okClicked) {
        	vendorData.add(tmpVendor);
            VendorHelper.insertVendor(tmpVendor);
        }
    }

    @FXML
    private void handleEditVendor() {
        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();
        if (selectedVendor != null) {
            boolean okClicked = mainApp.showVendorEditDialog(selectedVendor);
            if (okClicked) {
                showVendorDetails(selectedVendor);
                VendorHelper.updateVendor(selectedVendor);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Kein Lieferant ausgewählt!");
            alert.setContentText("Bitte Lieferanten in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
}
