package application.view;

import application.model.ProductGroup;
import application.mongoDBInterface.ReferenceClass.ProductGroupHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductGroupEditDialogController {
	@FXML private TextField codeField;
    @FXML private TextField descrField;
    @FXML private TextField materialField;

    private Stage dialogStage;
    private ProductGroup productGroup;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;

        codeField.setText(productGroup.getCode());
        descrField.setText(productGroup.getDescription());
        materialField.setText(productGroup.getMaterial());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	productGroup.setCode(codeField.getText());
        	productGroup.setDescription(descrField.getText());
        	productGroup.setMaterial(materialField.getText());

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
            errorMessage += "Kein g�ltige Code!\n"; 
        }
        if (descrField.getText() == null || descrField.getText().length() == 0) {
            errorMessage += "Keine g�ltige Beschreibung!\n"; 
        }

        if (materialField.getText() == null || materialField.getText().length() == 0) {
            errorMessage += "Kein g�ltiges Material!\n"; 
        }

        //check database (first build tmpProductGroup which will be inserted)
        ProductGroup tmpProductGroup = new ProductGroup(
        		productGroup.getObjectId().toString(), codeField.getText(),
        		descrField.getText(), materialField.getText());
        
        if (ProductGroupHelper.productGroupAlreadyExists(tmpProductGroup)) {
        	errorMessage += "Produktgruppencode bereits vergeben!\n";
        }
        
        //give return value
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ung�ltige Felder");
            alert.setHeaderText("Bitte ung�ltige Felder korrigieren!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
