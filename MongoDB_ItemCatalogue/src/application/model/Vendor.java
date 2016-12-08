package application.model;

import org.bson.types.ObjectId;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vendor {

	private ObjectId objectId;
	private StringProperty code;
	private StringProperty name;
	private StringProperty address;
	private StringProperty contact;

	public Vendor() {
        this(null, null, null, null, null);
    }

    public Vendor(String id, String code, String name, String address, String contact) {
    	if (id != null)
    		this.objectId = new ObjectId(id);
    	else
    		objectId = new ObjectId();
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.contact = new SimpleStringProperty(contact);
    }
    
    public ObjectId getObjectId() {
        return objectId;
    }
    
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

    
	public StringProperty getCodeProperty() {
		return code;
	}
	
    public String getCode() {
        return code.get();
    }
    
	public void setCode(String code) {
		this.code.set(code);
	}

	public StringProperty getNameProperty() {
		return name;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
    public String getName() {
        return name.get();
    }

	public StringProperty getMaterialProperty() {
		return address;
	}

	public void setAddress(String address) {
		this.address.set(address);
	}
    
    public String getAddress() {
    	return address.get();
    }
    
	public StringProperty getContactProperty() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact.set(contact);
	}
    
    public String getContact() {
    	return contact.get();
    }
}
