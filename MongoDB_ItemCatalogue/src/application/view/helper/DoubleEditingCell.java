package application.view.helper;

import java.util.regex.Pattern;

import application.model.ItemSales;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class DoubleEditingCell extends TableCell<ItemSales, Double> {

    private final TextField textField = new TextField();
    private final Pattern doublePattern = Pattern.compile("\\d+\\.\\d+");

    public DoubleEditingCell() {
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                processEdit();
            }
        });
        textField.setOnAction(event -> processEdit());
    }

    private void processEdit() {
        String text = textField.getText();
        if (doublePattern.matcher(text).matches()) {
            commitEdit(Double.parseDouble(text));
        } else {
            cancelEdit();
        }
    }

    @Override
    public void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            textField.setText(value.toString());
            setGraphic(textField);
        } else {
            setText(value.toString());
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        Number value = getItem();
        if (value != null) {
            textField.setText(value.toString());
            setGraphic(textField);
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    // This seems necessary to persist the edit on loss of focus; not sure why:
    @Override
    public void commitEdit(Double value) {
        super.commitEdit(value);
        ((ItemSales) this.getTableRow().getItem()).setQuantity(value.doubleValue());
    }
}