package application.mongoDBInterface.ReferenceClass;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

import application.model.ItemSales;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemSalesHelper {
    public static final String COLLECTION_NAME = "itemSales";
    private static MongoCollection<Document> collection;
    
    public static void insertItemSales(ItemSales itemSales) {        
    	getCollection();
    	
    	Document doc = new Document("_id", itemSales.getObjectId())
        		.append("itemNumber", itemSales.getItemNumber())
                .append("description", itemSales.getDescription())
                .append("quantity", itemSales.getQuantity())
                .append("amount", itemSales.getAmount());

        collection.insertOne(doc);
    }
    
    public static void updateItemSales(ItemSales itemSales) {     
    	getCollection();
    	
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("_id", itemSales.getObjectId());
    	
    	BasicDBObject updateQuery = new BasicDBObject();
    	updateQuery.append("$set",
    		new BasicDBObject()
    			.append("itemNumber", itemSales.getItemNumber())
    			.append("description", itemSales.getDescription())
    			.append("quantity", itemSales.getQuantity())
    			.append("amount",  itemSales.getAmount()));

    	collection.updateMany(searchQuery, updateQuery);
    }
    
    public static void updateMultipleItemSales(ObservableList<ItemSales> itemSalesList) {
    	getCollection();
    	
    	// Delete All documents from collection Using blank BasicDBObject
    	BasicDBObject document = new BasicDBObject();
    	collection.deleteMany(document);
    	
    	for (ItemSales itemSales : itemSalesList) {
    		insertItemSales(itemSales);
    	}
    }
	
	public static ObservableList<ItemSales> getItemSales() {
		getCollection();
		
		ObservableList<ItemSales> itemSales = FXCollections.observableArrayList();
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        itemSales.add(new ItemSales(
	        			doc.get("_id").toString(),
		        		doc.get("itemNumber").toString(),
		        		doc.get("description").toString(),
		        		Double.parseDouble(doc.get("quantity").toString()),
		        		Double.parseDouble(doc.get("amount").toString())));
		    }
		} finally {
		    cursor.close();
		}
	    return itemSales;
	}
	
	public static void deleteItemSales(ItemSales itemSales) {
		getCollection();
		
		collection.deleteOne(Filters.eq("_id", itemSales.getObjectId()));
	}
	
	public static ObservableList<ItemSales> getTopTenItemSales() {
		getCollection();
		
		ObservableList<ItemSales> itemSales = FXCollections.observableArrayList();
    	
		Block<Document> addItemSaleBlock = new Block<Document>() {
		    @Override
		    public void apply(final Document doc) {
		    	itemSales.add(new ItemSales(
		    			null,
		    			doc.get("_id").toString(),
		        		"",
		        		0,
		        		Double.parseDouble(doc.get("amount").toString())));
		    }
		};
	    
		collection.aggregate(
			      Arrays.asList(
				          Aggregates.project(
					              Projections.fields(
					                    Projections.include("_id"),
					                    Projections.include("itemNumber"),
					                    Projections.include("quantity"),
					                    Projections.include("amount")
					              )
					          ),
			              Aggregates.group("$itemNumber", Accumulators.sum("amount", "$amount")),
			              Aggregates.sort(new BasicDBObject("amount", -1)),
			              Aggregates.limit(10)
			      )
			).forEach(addItemSaleBlock);
		
		return itemSales;
	}
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
