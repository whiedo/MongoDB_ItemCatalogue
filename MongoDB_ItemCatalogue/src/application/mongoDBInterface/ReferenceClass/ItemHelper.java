package application.mongoDBInterface.ReferenceClass;



import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import application.model.Item;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemHelper {
	
    public static final String COLLECTION_NAME = "item";
    private static MongoCollection<Document> collection;
    
    public static void insertItem(Item item) {        
    	getCollection();
    	
    	Document doc = new Document("_id", item.getObjectId())
        		.append("number", item.getNumber())
                .append("description", item.getDescription())
                .append("salesprice", item.getSalesprice());

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
		        items.add(new Item(
	        			doc.get("_id").toString(),
		        		doc.get("number").toString(),
		        		doc.get("description").toString(),
		        		Double.parseDouble(doc.get("salesprice").toString())));
		    }
		} finally {
		    cursor.close();
		}
	    return items;
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
