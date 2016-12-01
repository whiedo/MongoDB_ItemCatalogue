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

	public StringProperty getNumberProperty() {
		return number;
	}
	
    public String getNumber() {
        return number.get();
    }
    
	public void setNumberProperty(StringProperty number) {
		this.number = number;
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}
	
	public void setDescriptionProperty(StringProperty description) {
		this.description = description;
	}
	
    public String getDescription() {
        return description.get();
    }

	public DoubleProperty getSalespriceProperty() {
		return salesprice;
	}

	public void setSalespriceProperty(DoubleProperty salesprice) {
		this.salesprice = salesprice;
	}
    
    public Double getSalesprice() {
    	return salesprice.get();
    }
}
