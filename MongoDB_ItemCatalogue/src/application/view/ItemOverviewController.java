package application.view;

import application.MainApp;
import application.model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemOverviewController {
	@FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> NoColumn;
    @FXML private TableColumn<Item, String> DescrColumn;
    
    @FXML private Label noLbl;
    @FXML private Label DescrLbl;
    @FXML private Label Descr2Lbl;

    private MainApp mainApp;

    public ItemOverviewController() {
    }

    @FXML
    private void initialize() {
        NoColumn.setCellValueFactory(cellData -> cellData.getValue().getNumberProperty());
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
            noLbl.setText(item.getNumber());
            DescrLbl.setText(item.getDescription());
            Descr2Lbl.setText(item.getDescription());
        } else {
            // Person is null, remove all the text.
        	noLbl.setText("");
        	DescrLbl.setText("");
        	Descr2Lbl.setText("");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        itemTable.setItems(mainApp.getItemData());
    }
}