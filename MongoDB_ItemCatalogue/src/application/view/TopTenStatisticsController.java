package application.view;

import application.model.ItemSales;
import application.mongoDBInterface.ReferenceClass.ItemSalesHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class TopTenStatisticsController {
	@FXML private BarChart<String, Double> barChart;
	@FXML private CategoryAxis xAxis;
	
	private ObservableList<String> itemNames;
	private ObservableList<ItemSales> itemSales;
	
	@FXML
	private void initialize() {
		itemNames = getTopTenItemNames();
	    xAxis.setCategories(itemNames);
	    
	    setXData();
	}
	
	public void setXData() {
		double[] itemNamesCounter = new double[itemNames.size()];
		
		int i = 0;
		for (ItemSales is : itemSales) {
			itemNamesCounter[i] = is.getAmount();
			i++;
		}

        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (i = 0; i < itemNamesCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(itemNames.get(i), itemNamesCounter[i]));
        }

        barChart.getData().add(series);
	}
	
	private ObservableList<String> getTopTenItemNames() {
		ObservableList<String> itemNames = FXCollections.observableArrayList();
		
		itemSales = ItemSalesHelper.getTopTenItemSales();
		
		for(ItemSales itemSale : itemSales) {
			itemNames.add(itemSale.getItemNumber());
		}
		
		return itemNames;	
	}

}
