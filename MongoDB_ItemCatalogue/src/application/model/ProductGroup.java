package application.model;

import com.mongodb.BasicDBObject;

public class ProductGroup extends BasicDBObject {
	public ProductGroup(String name, String description) {
		put("name",name);
		put("description",description);
	}
}
