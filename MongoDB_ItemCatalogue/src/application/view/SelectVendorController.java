package application.view;

import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.VendorHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class SelectVendorController {
	@FXML private TableView<Vendor> vendorTable;
    @FXML private TableColumn<Vendor, String> codeColumn;
    @FXML private TableColumn<Vendor, String> nameColumn;
    @FXML private TableColumn<Vendor, String> addressColumn;
    @FXML private TableColumn<Vendor, String> contactColumn;
    
    private ObservableList<Vendor> vendorData;
    private Stage dialogStage;
    private Vendor vendor;
    
    public SelectVendorController() {
    }
    
    @FXML
    private void initialize() {
    	vendorData = VendorHelper.getVendors();
        vendorTable.setItems(vendorData);
    	
    	codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    	addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
    	contactColumn.setCellValueFactory(cellData -> cellData.getValue().getContactProperty());

        // Listen for selection changes and show the item details when changed.
        vendorTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> vendorSelected(newValue));
    }
    
    private void vendorSelected(Vendor v) {
    	vendor = v;
    	dialogStage.close();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
    
    public Vendor getVendor() {
    	return vendor;
    }
}
