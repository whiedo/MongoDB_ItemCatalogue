package application.model;

import org.bson.types.ObjectId;

import application.mongoDBInterface.ReferenceClass.ItemHelper;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemSales {
	private ObjectId objectId;
	private StringProperty itemNumber;
	private StringProperty description;
	private DoubleProperty quantity;
	private DoubleProperty amount;
	
	public ItemSales() {
		this(null, "", "", 0, 0);
	}
	
	public ItemSales(String id, String itemNumber, String description, double quantity, double amount) {
    	if (id != null)
    		this.objectId = new ObjectId(id);
    	else
    		objectId = new ObjectId();
    	this.itemNumber = new SimpleStringProperty(itemNumber);
    	this.description = new SimpleStringProperty(description);
    	this.quantity = new SimpleDoubleProperty(quantity);
    	this.amount = new SimpleDoubleProperty(amount);
	}
	
	public Double getPrice() {
		return ItemHelper.getPrice(getItemNumber());
	}
	
    public ObjectId getObjectId() {
        return objectId;
    }
    
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
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
    
	public StringProperty getItemNumberProperty() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber.set(itemNumber);
	}
	
    public String getItemNumber() {
        return itemNumber.get();
    }

	public DoubleProperty getQuantityProperty() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity.set(quantity);
	}
    
    public Double getQuantity() {
    	return quantity.get();
    }
    
	public DoubleProperty getAmountProperty() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount.set(amount);
	}
    
    public Double getAmount() {
    	return amount.get();
    }
}
