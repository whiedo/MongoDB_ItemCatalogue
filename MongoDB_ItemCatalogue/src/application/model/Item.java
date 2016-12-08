package application.model;

import org.bson.types.ObjectId;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

	private ObjectId objectId;
	private StringProperty number;
	private StringProperty description;
	private DoubleProperty salesprice;

	public Item() {
        this(null, null, null, 0);
    }

    public Item(String id, String number, String description, double salesprice) {
    	if (id != null)
    		this.objectId = new ObjectId(id);
    	else
    		objectId = new ObjectId();
        this.number = new SimpleStringProperty(number);
        this.description = new SimpleStringProperty(description);
        this.salesprice = new SimpleDoubleProperty(salesprice);
    }
    
    public ObjectId getObjectId() {
        return objectId;
    }
    
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
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
