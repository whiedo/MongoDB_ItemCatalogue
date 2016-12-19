package application.mongoDBInterface.ReferenceClass;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import application.model.Item;
import application.model.ProductGroup;
import application.model.Vendor;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemHelper {
	
    public static final String COLLECTION_NAME = "item";
    private static MongoCollection<Document> collection;
    
    public static void insertItem(Item item) {        
    	getCollection();
    	
    	ArrayList<BasicDBObject> vendorDocs = getVendorDocFromItem(item);
    	
    	Document doc = new Document("_id", item.getObjectId())
        		.append("number", item.getNumber())
                .append("description", item.getDescription())
                .append("salesprice", item.getSalesprice())
                .append("productGroup", item.getProductGroup())
                .append("vendors", vendorDocs);

        collection.insertOne(doc);
    }
    
    public static void updateItem(Item item) {     
    	getCollection();
    	
    	ArrayList<BasicDBObject> vendorDocs = getVendorDocFromItem(item);
    	
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("_id", item.getObjectId());
    	
    	BasicDBObject updateQuery = new BasicDBObject();
    	updateQuery.append("$set",
    		new BasicDBObject()
    			.append("number", item.getNumber())
    			.append("description", item.getDescription())
    			.append("salesprice", item.getSalesprice())
    			.append("productGroup", item.getProductGroup())
    			.append("vendors", vendorDocs));

    	collection.updateMany(searchQuery, updateQuery);
    }
    
    public static ArrayList<BasicDBObject> getVendorDocFromItem(Item item) {
    	ArrayList<BasicDBObject> vendorDocs = new ArrayList<>();
    	for(Vendor v : item.getVendors()) {
    		BasicDBObject vendorsObj = new BasicDBObject("_id",v.getObjectId());
    		vendorsObj.put("code", v.getCode());
    		vendorsObj.put("name", v.getName());
    		vendorsObj.put("address", v.getAddress());
    		vendorsObj.put("contact", v.getContact());
    		vendorDocs.add(vendorsObj);
    	}
    	
    	return vendorDocs;
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
		        		doc.get("productGroup").toString(),
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
	
	public static Integer getItemCountPerProductGroupCode(String productGroupCode) {
		getCollection();
		Integer i = 0;
		
		BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("productGroup", productGroupCode);
    	
    	MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
		try {
		    while (cursor.hasNext()) {
		        cursor.next();
		        i++;
		    }
		} finally {
		    cursor.close();
		}
		
	    return i;
	}
	
	public static void deleteVendorFromItems(Vendor v) {
		getCollection();
		
		//loop through all items
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		    	boolean itemNeedsVendorUpdate = false;
		        Document doc = cursor.next();
		        
		        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

		        @SuppressWarnings("unchecked")
				List<Document> vendorList = (List<Document>) doc.get("vendors");
			    
		        //check vendors of item if update is neccessary
		        for (Document vendor : vendorList) {
		        	if (vendor.get("_id").toString().equals(v.getObjectId().toString())) {
		        		itemNeedsVendorUpdate = true;
		        	}
				}
		        
		        //if update neccessary build new vendor list and update item
		        if (itemNeedsVendorUpdate) {
			        for (Document vendor : vendorList) {
			        	if (!vendor.get("_id").toString().equals(v.getObjectId().toString())) {
				        	vendors.add(new Vendor(
				        			vendor.get("_id").toString(),
				        			vendor.get("code").toString(),
				        			vendor.get("name").toString(),
				        			vendor.get("address").toString(),
				        			vendor.get("contact").toString()));
			        	}
					}
			        
		        	updateItem(new Item(
		        			doc.get("_id").toString(),
			        		doc.get("number").toString(),
			        		doc.get("description").toString(),
			        		Double.parseDouble(doc.get("salesprice").toString()),
			        		doc.get("productGroup").toString(),
			        		vendors));
		        }
		    }
		} finally {
		    cursor.close();
		}
	}
	
	public static void deleteProductGroupFromItems(ProductGroup pg) {
		getCollection();
		
		BasicDBObject searchQuery = new BasicDBObject().append("productGroup", pg.getCode());
		
		BasicDBObject newValueDoc = new BasicDBObject();
		newValueDoc.append("$set", new BasicDBObject().append("productGroup", ""));

		collection.updateMany(searchQuery, newValueDoc);
	}
	
	public static void deleteItem(Item item) {
		getCollection();
		
		collection.deleteOne(Filters.eq("_id", item.getObjectId()));
	}
	
    public static boolean itemAlreadyExists(Item item) {
    	//query items which have not the same objectId and the same item number
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.put("_id", new BasicDBObject("$ne", item.getObjectId()));
    	searchQuery.put("number", item.getNumber());
    	
    	MongoCursor<Document> cursor = collection.find(searchQuery).iterator();
    	if (cursor.hasNext()) {
    		return true;
    	}
    	
    	return false;
    }
    
    public static Double getPrice(String itemNumber) {
    	getCollection();
    	
    	Document itemDoc = collection.find(Filters.eq("number", itemNumber)).first();
    	Double price = Double.parseDouble(itemDoc.get("salesprice").toString());
    	
    	return price;
    }
    
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
