package application.view;

import application.MainApp;
import application.model.Item;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemOverviewController {
	@FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> NumberColumn;
    @FXML private TableColumn<Item, String> DescrColumn;
    
    @FXML private Label numberLbl;
    @FXML private Label DescrLbl;
    @FXML private Label salespriceLbl;

    private MainApp mainApp;

    public ItemOverviewController() {
    }

    @FXML
    private void initialize() {
        NumberColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());
        DescrColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
       
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
            salespriceLbl.setText(item.getSalesprice().toString());
        } else {
            // Person is null, remove all the text.
        	numberLbl.setText("");
        	DescrLbl.setText("");
        	salespriceLbl.setText("");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        itemTable.setItems(mainApp.getItemData());
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
            mainApp.getItemData().add(tmpItem);
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
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}