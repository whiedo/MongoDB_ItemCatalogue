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

    private MainApp mainApp;

    public ItemOverviewController() {
    }

    @FXML
    private void initialize() {
        NoColumn.setCellValueFactory(cellData -> cellData.getValue().getNumber());
        DescrColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        itemTable.setItems(mainApp.getItemData());
    }
}