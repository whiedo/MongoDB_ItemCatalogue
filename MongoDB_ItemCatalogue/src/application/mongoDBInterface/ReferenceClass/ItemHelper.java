package application.mongoDBInterface.ReferenceClass;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import application.model.Item;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemHelper {
	
    private static final String COLLECTION_NAME = "item";
    private static MongoCollection<Document> collection;
    
    public static void insertItem(Item item) throws UnknownHostException {        
        Document doc = new Document("number", item.getNumber())
                .append("description", item.getDescription())
                .append("description2", item.getDescription2())
                .append("salesprice", item.getSalesprice());

        getCollection();
        collection.insertOne(doc);
    }
	
	public static ObservableList<Item> getItems() {
		getCollection();
		
		ObservableList<Item> items = FXCollections.observableArrayList();
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        items.add(new Item(
		        		doc.get("number").toString(),
		        		doc.get("description").toString(),
		        		doc.get("description2").toString(),
		        		Double.parseDouble(doc.get("salesprice").toString())));
		    }
		} finally {
		    cursor.close();
		}
	    return items;
	}
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
