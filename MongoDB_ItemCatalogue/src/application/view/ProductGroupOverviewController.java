package application.view;

import application.MainApp;
import application.model.Item;
import application.model.ProductGroup;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ProductGroupOverviewController {
	@FXML private TableView<ProductGroup> productGroupTable;
    @FXML private TableColumn<ProductGroup, String> codeColumn;
    @FXML private TableColumn<ProductGroup, String> descrColumn;
    
    @FXML private Label codeLbl;
    @FXML private Label descrLbl;
    @FXML private Label materialLbl;
    
    private MainApp mainApp;
    
    public ProductGroupOverviewController () {
    }
    
    @FXML
    private void initialize() {
    	codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
    	descrColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
       
        // Clear item details.
        showProductGroupDetails(null);

        // Listen for selection changes and show the item details when changed.
        productGroupTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductGroupDetails(newValue));
    }
    
    private void showProductGroupDetails(ProductGroup productGroup) {
        if (productGroup != null) {
            // Fill the labels with info from the item object.
        	codeLbl.setText(productGroup.getCode());
        	descrLbl.setText(productGroup.getDescription());
        	materialLbl.setText(productGroup.getMaterial().toString());
        } else {
            // Person is null, remove all the text.
        	codeLbl.setText("");
        	descrLbl.setText("");
        	materialLbl.setText("");
        }
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        //productGroupTable.setItems(mainApp.getProductGroupData());
    }
    
    @FXML
    private void handleDeleteProductGroup() {
        int selectedIndex = productGroupTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	ProductGroupHelper.deleteProductGroup(productGroupTable.getSelectionModel().getSelectedItem());
            productGroupTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Kein Artikel ausgewählt!");
            alert.setContentText("Bitte Artikel in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewProductGroup() {
    	ProductGroup tmpProductGroup = new ProductGroup();
        boolean okClicked = mainApp.showProductGroupEditDialog(tmpProductGroup);
        if (okClicked) {
            mainApp.getItemData().add(tmpProductGroup);
            ProductGroupHelper.insertProductGroup(tmpProductGroup);
        }
    }

    @FXML
    private void handleEditItem() {
        Item selectedItem = productGroupTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean okClicked = mainApp.showItemEditDialog(selectedItem);
            if (okClicked) {
                showItemDetails(selectedItem);
                ItemHelper.updateItem(selectedItem);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}
