package application.view;

import application.MainApp;
import application.model.ProductGroup;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.collections.ObservableList;
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
    private ObservableList<ProductGroup> productGroupData;
    
    public ProductGroupOverviewController () {
    }
    
    @FXML
    private void initialize() {
    	productGroupData = ProductGroupHelper.getProductGroups();
        productGroupTable.setItems(productGroupData);
    	
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
            alert.setHeaderText("Keine Produktgruppe ausgewählt!");
            alert.setContentText("Bitte Produktgruppe in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewProductGroup() {
    	ProductGroup tmpProductGroup = new ProductGroup();
        boolean okClicked = mainApp.showProductGroupEditDialog(tmpProductGroup);
        if (okClicked) {
        	productGroupData.add(tmpProductGroup);
            ProductGroupHelper.insertProductGroup(tmpProductGroup);
        }
    }

    @FXML
    private void handleEditProductGroup() {
        ProductGroup selectedProductGroup = productGroupTable.getSelectionModel().getSelectedItem();
        if (selectedProductGroup != null) {
            boolean okClicked = mainApp.showProductGroupEditDialog(selectedProductGroup);
            if (okClicked) {
                showProductGroupDetails(selectedProductGroup);
                ProductGroupHelper.updateProductGroup(selectedProductGroup);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Keine Produktgruppe ausgewählt!");
            alert.setContentText("Bitte Produktgruppe in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
}
