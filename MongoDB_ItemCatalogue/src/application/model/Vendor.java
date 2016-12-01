package application.model;

import com.mongodb.BasicDBObject;

public class Vendor extends BasicDBObject {
	public Vendor(String name, String address) {
		put("name",name);
		put("address",address);
	}
}
