package application.model;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

	private StringProperty number;
	private StringProperty description;
	private StringProperty description2;
	private DoubleProperty salesprice;

	public Item() {
        this(null, null, null, 0);
    }

    public Item(String number, String description, String description2, double salesprice) {
        this.number = new SimpleStringProperty(number);
        this.description = new SimpleStringProperty(description);
        this.description2 = new SimpleStringProperty(description2);  
        this.salesprice = new SimpleDoubleProperty(salesprice);
    }
    
	public StringProperty getNumberProperty() {
		return number;
	}
	
    public String getNumber() {
        return number.get();
    }
    
	public void setNumber(String number) {
		this.number.set(number);
	}

	public StringProperty getDescriptionProperty() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
    public String getDescription() {
        return description.get();
    }
    
	public StringProperty getDescriptionProperty2() {
		return description2;
	}
	
	public void setDescription2(String description2) {
		this.description2.set(description2);
	}
	
    public String getDescription2() {
        return description2.get();
    }

	public DoubleProperty getSalespriceProperty() {
		return salesprice;
	}

	public void setSalesprice(Double salesprice) {
		this.salesprice.set(salesprice);
	}
    
    public Double getSalesprice() {
    	return salesprice.get();
    }
}
