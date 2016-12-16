package application.model;

import java.util.ArrayList;

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
	private StringProperty productGroup;
	private ArrayList<Vendor> vendors;

	public Item() {
        this(null, "", "", 0, "", null);
    }

    public Item(String id, String number, String description, double salesprice, String productGroup, ArrayList<Vendor> vendors) {
    	if (id != null)
    		this.objectId = new ObjectId(id);
    	else
    		objectId = new ObjectId();
        this.number = new SimpleStringProperty(number);
        this.description = new SimpleStringProperty(description);
        this.salesprice = new SimpleDoubleProperty(salesprice);
        this.productGroup = new SimpleStringProperty(productGroup);
        if (vendors != null)
        	this.vendors = vendors;
        else
        	this.vendors = new ArrayList<Vendor>();
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
    
	public StringProperty getProductGroupProperty() {
		return productGroup;
	}
	
	public void setProductGroup(String productGroup) {
		this.productGroup.set(productGroup);
	}
	
    public String getProductGroup() {
        return productGroup.get();
    }
    
	public void setVendors(ArrayList<Vendor> vendors) {
		this.vendors = vendors;
	}
    
    public ArrayList<Vendor> getVendors() {
    	return vendors;
    }
    
    public void addVendor(Vendor v) {
    	vendors.add(v);
    }
    
}
