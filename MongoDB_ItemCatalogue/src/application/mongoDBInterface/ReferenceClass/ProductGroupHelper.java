package application.mongoDBInterface.ReferenceClass;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import application.model.ProductGroup;
import application.mongoDBInterface.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductGroupHelper {
	
    public static final String COLLECTION_NAME = "productGroup";
    private static MongoCollection<Document> collection;
    
    public static void insertProductGroup(ProductGroup productGroup) {        
    	getCollection();
    	
    	Document doc = new Document("_id", productGroup.getObjectId())
        		.append("code", productGroup.getCode())
                .append("description", productGroup.getDescription())
                .append("material", productGroup.getMaterial());

        collection.insertOne(doc);
    }
    
    public static void updateProductGroup(ProductGroup productGroup) {     
    	getCollection();
    	
    	BasicDBObject searchQuery = new BasicDBObject();
    	searchQuery.append("_id", productGroup.getObjectId());
    	
    	BasicDBObject updateQuery = new BasicDBObject();
    	updateQuery.append("$set",
    		new BasicDBObject()
    			.append("code", productGroup.getCode())
    			.append("description", productGroup.getDescription())
    			.append("material", productGroup.getMaterial()));

    	collection.updateMany(searchQuery, updateQuery);
    }
	
	public static ObservableList<ProductGroup> getProductGroups() {
		getCollection();
		
		ObservableList<ProductGroup> productGroups = FXCollections.observableArrayList();
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        Document doc = cursor.next();
		        productGroups.add(new ProductGroup(
	        			doc.get("_id").toString(),
		        		doc.get("code").toString(),
		        		doc.get("description").toString(),
		        		doc.get("material").toString()));
		    }
		} finally {
		    cursor.close();
		}
	    return productGroups;
	}
	
	public static void deleteProductGroup(ProductGroup productGroup) {
		getCollection();
		
		collection.deleteOne(Filters.eq("_id", productGroup.getObjectId()));
	}
	
	private static void getCollection() {
		if (collection == null)
			collection = DatabaseManager.database.getCollection(COLLECTION_NAME);
	}
}
