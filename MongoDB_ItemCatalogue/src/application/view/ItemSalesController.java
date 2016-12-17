package application.view;

import application.MainApp;
import application.model.ItemSales;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ItemSalesHelper;
import application.view.helper.DoubleEditingCell;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;

public class ItemSalesController {
	@FXML private TableView<ItemSales> itemSalesTable;
    @FXML private TableColumn<ItemSales, String> itemNumberColumn;
    @FXML private TableColumn<ItemSales, String> descrColumn;
    @FXML private TableColumn<ItemSales, Double> quantityColumn;
    @FXML private TableColumn<ItemSales, Double> amountColumn;
    
    private MainApp mainApp;
    private ObservableList<ItemSales> itemSalesData;
    
    public ItemSalesController() {
    }
    
    @FXML
    private void initialize() {
    	itemSalesData = ItemSalesHelper.getItemSales();
    	itemSalesTable.setItems(itemSalesData);
    	
    	itemNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNumberProperty());
    	itemNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	itemNumberColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ItemSales, String>>() {
                    @Override public void handle(TableColumn.CellEditEvent<ItemSales, String> t) {
                        ((ItemSales) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setItemNumber(t.getNewValue());
                    }
                });
        
    	descrColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
    	descrColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    	descrColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ItemSales, String>>() {
                    @Override public void handle(TableColumn.CellEditEvent<ItemSales, String> t) {
                        ((ItemSales) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setDescription(t.getNewValue());
                    }
                });
    	
    	quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getQuantityProperty().asObject());
    	quantityColumn.setCellFactory(col -> new DoubleEditingCell());
    	
    	amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty().asObject());
    	amountColumn.setCellFactory(col -> new DoubleEditingCell());
    }
    
    @FXML
    private void handleNew() {
    	itemSalesData.add(new ItemSales());
    	itemSalesTable.setItems(itemSalesData);
    }
    
    @FXML
    private void handleDelete() {
    	
	    int selectedIndex = itemSalesTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	itemSalesTable.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Keine Auswahl");
	        alert.setHeaderText("Keinen Verkauf ausgewählt!");
	        alert.setContentText("Bitte Verkauf in der Tabelle auswählen.");
	
	        alert.showAndWait();
	    }
    }
    
    public ObservableList<ItemSales> getItemSalesList() {
    	return itemSalesData;
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
}
