package application.mongoDBInterface.ReferenceClass;

import com.mongodb.BasicDBObject;

public class Vendor extends BasicDBObject {
	public Vendor(String name, String address) {
		put("name",name);
		put("address",address);
	}
}
