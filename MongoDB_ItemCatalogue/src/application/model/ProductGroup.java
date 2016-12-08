package application.model;

import org.bson.types.ObjectId;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductGroup {

	private ObjectId objectId;
	private StringProperty code;
	private StringProperty description;
	private StringProperty material;

	public ProductGroup() {
        this(null, null, null, null);
    }

    public ProductGroup(String id, String code, String description, String material) {
    	if (id != null)
    		this.objectId = new ObjectId(id);
    	else
    		objectId = new ObjectId();
        this.code = new SimpleStringProperty(code);
        this.description = new SimpleStringProperty(description);
        this.material = new SimpleStringProperty(material);
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

	public StringProperty getDescriptionProperty() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
    public String getDescription() {
        return description.get();
    }

	public StringProperty getMaterialProperty() {
		return material;
	}

	public void setMaterial(String material) {
		this.material.set(material);
	}
    
    public String getMaterial() {
    	return material.get();
    }
}
