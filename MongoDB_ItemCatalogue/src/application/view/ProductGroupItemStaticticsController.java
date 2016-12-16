package application.view;

import application.MainApp;
import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.ItemHelper;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class ProductGroupItemStaticticsController {
	@FXML private BarChart<String, Integer> barChart;
	@FXML private CategoryAxis xAxis;
	
    private MainApp mainApp;
	private ObservableList<String> productGroupNames;
		
	@FXML
	private void initialize() {
		productGroupNames = ProductGroupHelper.getProductGroupsAsStringList();
	    xAxis.setCategories(productGroupNames);
	    
	    setXData(productGroupNames);
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
	
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
