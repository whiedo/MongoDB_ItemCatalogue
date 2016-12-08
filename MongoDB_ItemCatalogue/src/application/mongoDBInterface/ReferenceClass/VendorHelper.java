package application.mongoDBInterface.ReferenceClass;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import application.model.Vendor;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendorHelper {
	
    public static final String COLLECTION_NAME = "vendor";
    private static MongoCollection<Document> collection;
    
    public static void insertVendor(Vendor vendor) {        
    	getCollection();
    	
    	Document doc = new Document("_id", vendor.getObjectId())
        		.append("code", vendor.getCode())
                .append("name", vendor.getName())
                .append("address", vendor.getAddress())
                .append("contact", vendor.getContact());

        collection.insertOne(doc);
    }
    
    public static void updateVendor(Vendor vendor) {     
    	getCollection();
    	
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("_id", vendor.getObjectId());
    	
    	BasicDBObject updateQuery = new BasicDBObject();
    	updateQuery.append("$set",
    		new BasicDBObject()
    			.append("code", vendor.getCode())
    			.append("name", vendor.getName())
    			.append("address", vendor.getAddress())
    			.append("contact", vendor.getContact()));

    	collection.updateMany(searchQuery, updateQuery);
    }
	
	public static ObservableList<Vendor> getVendors() {
		getCollection();
		
		ObservableList<Vendor> vendors = FXCollections.observableArrayList();
		
		MongoCursor<Document> cursor = collection.find().iterator();
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
	    return vendors;
	}
	
	public static void deleteVendor(Vendor vendor) {
		getCollection();
		
		collection.deleteOne(Filters.eq("_id", vendor.getObjectId()));
	}
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
