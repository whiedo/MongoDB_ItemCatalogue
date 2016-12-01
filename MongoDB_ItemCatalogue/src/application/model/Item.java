package application.model;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;

public class Item extends BasicDBObject {
	public Item(String number, String description, double salesprice, ArrayList<Vendor> vendors) {
		put("number",number);
		put("description",description);
		put("salesprice",salesprice);
		put("vendors",vendors);
	}
}
