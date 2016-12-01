package application.mongoDBInterface;

import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

public class QueryDriver {

	public static String str1;
	
	public static String makeFirstQuery() {
		str1 = "";
		InsertDriver.initDatabase(); 
		FindIterable<Document> iterable = InsertDriver.database.getCollection("item").find();
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        str1 += document.toString();
		    }
		});
		return str1;
	}
	
}
