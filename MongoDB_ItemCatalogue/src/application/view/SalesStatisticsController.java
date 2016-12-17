package application.view;

import application.model.ItemSales;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ItemSalesHelper;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class SalesStatisticsController {
	@FXML private BarChart<String, Integer> barChart;
	@FXML private CategoryAxis xAxis;
	
	private ObservableList<String> itemNames;
	
	@FXML
	private void initialize() {
		itemNames = getTopTenItemNames();
	    xAxis.setCategories(itemNames);
	    
	    setXData(itemNames);
	}
	
	public void setXData(ObservableList<String> productGroups) {
		int[] productGroupCounter = new int[productGroups.size()];
		
        for (String productGroupCode : productGroups) {
            int productGroupCount = ItemHelper.getItemCountPerProductGroupCode(productGroupCode);
            productGroupCounter[productGroups.indexOf(productGroupCode)] += productGroupCount;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < productGroupCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(productGroups.get(i), productGroupCounter[i]));
        }

        barChart.getData().add(series);
	}
	
	private ObservableList<String> getTopTenItemNames() {
		ObservableList<String> itemNames = FXCollections.observableArrayList();
		
		ObservableList<ItemSales> itemSales = ItemSalesHelper.getTopTenItemSales();
		
		for(ItemSales itemSale : itemSales) {
			itemNames.add(itemSale.getItemNumber());
		}
		
		return itemNames;	
	}

}
