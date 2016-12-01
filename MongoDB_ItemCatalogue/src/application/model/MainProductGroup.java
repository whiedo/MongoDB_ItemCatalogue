package application.model;

import com.mongodb.BasicDBObject;

public class MainProductGroup extends BasicDBObject {
	public MainProductGroup(String name, String description) {
		put("name",name);
		put("description",description);
	}
}
