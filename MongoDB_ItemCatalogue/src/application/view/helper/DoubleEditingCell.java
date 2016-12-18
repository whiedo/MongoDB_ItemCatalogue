package application.view.helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import application.model.ItemSales;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class DoubleEditingCell extends TableCell<ItemSales, Double> {

    private final TextField textField = new TextField();

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
        NumberFormat nf = DecimalFormat.getInstance(Locale.GERMAN);
        Number number = 0;
		try {
			number = nf.parse(text);
			commitEdit(Double.parseDouble(number + ""));
		} catch (ParseException e) {
			cancelEdit();
			e.printStackTrace();
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

    @Override
    public void commitEdit(Double value) {
        super.commitEdit(value);
        ((ItemSales) this.getTableRow().getItem()).setQuantity(value.doubleValue());
    }
}