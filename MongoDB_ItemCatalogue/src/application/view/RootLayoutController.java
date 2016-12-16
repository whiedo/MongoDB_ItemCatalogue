package application.view;

import application.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {

    private MainApp mainApp;

    public RootLayoutController() {
    }
    
    @FXML
	private void handleCoreDataItem() {
    	mainApp.showItemOverview();
	}
	
    @FXML
	private void handleCoreDataProductGroup() {
		mainApp.showProductGroupOverview();
	}
    
    @FXML
    private void handleCoreDataVendor() {
    	mainApp.showVendorOverview();
    }
    
    @FXML
    private void handleProductGroupItemStatistics() {
    	mainApp.showProductGroupItemStatistics();
    }
	
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
