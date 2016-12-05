package application.view;

import application.model.ProductGroup;
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

        if (codeField.getText() == null || codeField.getText().length() == 0) {
            errorMessage += "Kein gültige Code!\n"; 
        }
        if (descrField.getText() == null || descrField.getText().length() == 0) {
            errorMessage += "Keine gültige Beschreibung!\n"; 
        }

        if (materialField.getText() == null || materialField.getText().length() == 0) {
            errorMessage += "Kein gültiges Material!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
