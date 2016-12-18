package application.view;

import application.model.Vendor;
import application.mongoDBInterface.ReferenceClass.VendorHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VendorEditDialogController {
	@FXML private TextField codeField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField contactField;
    

    private Stage dialogStage;
    private Vendor vendor;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;

        codeField.setText(vendor.getCode());
        nameField.setText(vendor.getName());
        addressField.setText(vendor.getAddress());
        contactField.setText(vendor.getContact());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	vendor.setCode(codeField.getText());
        	vendor.setName(nameField.getText());
        	vendor.setAddress(addressField.getText());
        	vendor.setContact(contactField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        //check syntax
        if (codeField.getText() == null || codeField.getText().length() == 0) {
            errorMessage += "Kein gültiger Code!\n"; 
        }
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Keine gültige Beschreibung!\n"; 
        }

        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "Keine gültige Adresse!\n"; 
        }
        
        if (contactField.getText() == null || contactField.getText().length() == 0) {
            errorMessage += "Kein gültiger Kontakt!\n"; 
        }
        
        //check database (first build tmpProductGroup which will be inserted)
        Vendor tmpVendor = new Vendor(
        		vendor.getObjectId().toString(), codeField.getText(), nameField.getText(),
        		addressField.getText(), contactField.getText());
        
        if (VendorHelper.vendorAlreadyExists(tmpVendor)) {
        	errorMessage += "Lieferantencode bereits vergeben!\n";
        }
        
        //give return value
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ungültige Felder");
            alert.setHeaderText("Bitte ungültige Felder korrigieren!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
