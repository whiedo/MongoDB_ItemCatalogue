package application.view;

import application.MainApp;
import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemOverviewController {
	@FXML private TableView<Item> itemTable;
	@FXML private TableView<Vendor> vendorSubTable;
    @FXML private TableColumn<Item, String> numberColumn;
    @FXML private TableColumn<Item, String> descrColumn;
    @FXML private TableColumn<Vendor, String> codeColumn;
    @FXML private TableColumn<Vendor, String> nameColumn;
    
    @FXML private Label numberLbl;
    @FXML private Label DescrLbl;
    @FXML private Label salespriceLbl;
    @FXML private Label productGroupLbl;

    private MainApp mainApp;
    private ObservableList<Item> itemData;
    private ObservableList<Vendor> vendorSubData;
    
    public ItemOverviewController() {
    }

    @FXML
    private void initialize() {
    	itemData = ItemHelper.getItems();
        itemTable.setItems(itemData);
        
        vendorSubData = null;
        vendorSubTable.setItems(vendorSubData);
        
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());
        descrColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().getCodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
       
        // Clear item details.
        showItemDetails(null);

        // Listen for selection changes and show the item details when changed.
        itemTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItemDetails(newValue));
    }
    
    private void showItemDetails(Item item) {
        if (item != null) {
            // Fill the labels with info from the item object.
            numberLbl.setText(item.getNumber());
            DescrLbl.setText(item.getDescription());
            salespriceLbl.setText(item.getSalesprice().toString().replace(".", ","));
            productGroupLbl.setText(item.getProductGroup());
            
            vendorSubData = ItemHelper.getVendorsOfItem(item);
            vendorSubTable.setItems(vendorSubData);
        } else {
            // Person is null, remove all the text.
        	numberLbl.setText("");
        	DescrLbl.setText("");
        	salespriceLbl.setText("");
        	productGroupLbl.setText("");
        	
        	vendorSubData = null;
            vendorSubTable.setItems(vendorSubData);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void handleDeleteItem() {
        int selectedIndex = itemTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	ItemHelper.deleteItem(itemTable.getSelectionModel().getSelectedItem());
            itemTable.getItems().remove(selectedIndex);
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
    private void handleNewItem() {
        Item tmpItem = new Item();
        boolean okClicked = mainApp.showItemEditDialog(tmpItem);
        if (okClicked) {
            itemData.add(tmpItem);
            ItemHelper.insertItem(tmpItem);
        }
    }

    @FXML
    private void handleEditItem() {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
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
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Kein Artikel ausgewählt!");
            alert.setContentText("Bitte Artikel in der Tabelle auswählen.");

            alert.showAndWait();
        }
    }
}