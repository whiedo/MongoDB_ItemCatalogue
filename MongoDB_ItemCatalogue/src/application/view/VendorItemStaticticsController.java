package application.view;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.VendorHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class VendorItemStaticticsController {
	@FXML private BarChart<String, Integer> barChart;
	@FXML private CategoryAxis xAxis;
	
	private ObservableList<Vendor> vendorData;
	private ObservableList<String> vendorNames = FXCollections.observableArrayList();
		
	@FXML
	private void initialize() {
		vendorData = VendorHelper.getVendors();
		ArrayList<String> vendors = new ArrayList<String>();
		
		for(int i = 1; i>= vendorData.size(); i++) {
			vendors.add(vendorData.get(i).getName());
		}

		// Convert it to a list and add it to our ObservableList
	    for (String vendorName : vendorNames) {
	    	vendorNames.add(vendorName);
	    }
	    
	    xAxis.setCategories(vendorNames);
	}
	
	public void setData(ObservableList<Vendor> vendors, ObservableList<Item> items) {
	    // Count the number of people having their birthday in a specific month.
	    int[] monthCounter = new int[12];
	    for (Item i : items) {
	        //int month = i.getVendor().getVendorValue() -1;
	        //monthCounter[month]++;
	    }
	
	    XYChart.Series<String, Integer> series = new XYChart.Series<>();
	
	    // Create a XYChart.Data object for each month. Add it to the series.
	    //for (int i = 0; i < monthCounter.length; i++) {
	    //    series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
	    //}
	
	    //barChart.getData().add(series);
	}
}
