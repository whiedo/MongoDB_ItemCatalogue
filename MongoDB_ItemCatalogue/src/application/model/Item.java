package application.model;

import java.util.ArrayList;

import application.mongoDBInterface.ReferenceClass.Vendor;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

	private StringProperty number;
	private StringProperty description;
	private DoubleProperty salesprice;

	public Item() {
        this(null, null, 0);
    }

    public Item(String number, String description, double salesprice) {
        this.number = new SimpleStringProperty(number);
        this.description = new SimpleStringProperty(description);  
        this.salesprice = new SimpleDoubleProperty(salesprice);
    }

	public StringProperty getNumber() {
		return number;
	}

	public StringProperty getDescription() {
		return description;
	}

	public DoubleProperty getSalesprice() {
		return salesprice;
	}

	public void setNumber(StringProperty number) {
		this.number = number;
	}

	public void setDescription(StringProperty description) {
		this.description = description;
	}

	public void setSalesprice(DoubleProperty salesprice) {
		this.salesprice = salesprice;
	}
}
