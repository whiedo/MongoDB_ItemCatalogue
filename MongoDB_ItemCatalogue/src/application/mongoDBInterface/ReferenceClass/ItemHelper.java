package application.mongoDBInterface.ReferenceClass;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import application.model.Item;
import application.model.Vendor;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemHelper {
	
    public static final String COLLECTION_NAME = "item";
    private static MongoCollection<Document> collection;
    
    public static void insertItem(Item item) {        
    	getCollection();
    	
    	ArrayList<BasicDBObject> vendorDocs = new ArrayList<>();
    	for(Vendor v : item.getVendors()) {
    		BasicDBObject vendorsObj = new BasicDBObject("_id",v.getObjectId());
    		vendorsObj.put("code", v.getCode());
    		vendorsObj.put("name", v.getName());
    		vendorDocs.add(vendorsObj);
    	}
    	
    	Document doc = new Document("_id", item.getObjectId())
        		.append("number", item.getNumber())
                .append("description", item.getDescription())
                .append("salesprice", item.getSalesprice())
                .append("vendors", vendorDocs);

        collection.insertOne(doc);
    }
    
    public static void updateItem(Item item) {     
    	getCollection();
    	
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("_id", item.getObjectId());
    	
    	BasicDBObject updateQuery = new BasicDBObject();
    	updateQuery.append("$set",
    		new BasicDBObject()
    			.append("number", item.getNumber())
    			.append("description", item.getDescription())
    			.append("salesprice", item.getSalesprice()));

    	collection.updateMany(searchQuery, updateQuery);
    }
	
	public static ObservableList<Item> getItems() {
		getCollection();
		
		ObservableList<Item> items = FXCollections.observableArrayList();
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        
		        ArrayList<Vendor> vendors = new ArrayList<Vendor>();
		        
		        @SuppressWarnings("unchecked")
				List<Document> vendorList = (List<Document>) doc.get("vendors");
			        
		        for (Document vendor : vendorList) {
		        	vendors.add(new Vendor(
		        			vendor.get("_id").toString(),
		        			vendor.get("code").toString(),
		        			vendor.get("name").toString(),
		        			vendor.get("address").toString(),
		        			vendor.get("contact").toString()));
					
				}
		        
		        items.add(new Item(
	        			doc.get("_id").toString(),
		        		doc.get("number").toString(),
		        		doc.get("description").toString(),
		        		Double.parseDouble(doc.get("salesprice").toString()),
		        		vendors));
		    }
		} finally {
		    cursor.close();
		}
	    return items;
	}
	
	public static ObservableList<Vendor> getVendorsOfItem(Item item) {
		MongoCollection<Document> vendorCollection =
				DatabaseManager.database.getCollection(VendorHelper.COLLECTION_NAME);
		
		ObservableList<Vendor> vendors = FXCollections.observableArrayList();
				
		for(Vendor v : item.getVendors()) {
			BasicDBObject searchQuery = new BasicDBObject();
	    	searchQuery.append("_id", v.getObjectId());
	    	
	    	MongoCursor<Document> cursor = vendorCollection.find(searchQuery).iterator();
			try {
			    while (cursor.hasNext()) {
			        Document doc = cursor.next();
			        vendors.add(new Vendor(
		        			doc.get("_id").toString(),
			        		doc.get("code").toString(),
			        		doc.get("name").toString(),
			        		doc.get("address").toString(),
			        		doc.get("contact").toString()));
			    }
			} finally {
			    cursor.close();
			}
		}
		
	    return vendors;
	}
	
	public static void deleteItem(Item item) {
		getCollection();
		
		collection.deleteOne(Filters.eq("_id", item.getObjectId()));
	}
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
