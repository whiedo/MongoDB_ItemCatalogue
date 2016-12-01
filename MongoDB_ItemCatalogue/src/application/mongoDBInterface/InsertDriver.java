package application.mongoDBInterface;

import com.mongodb.MongoClient;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import application.model.*;
import application.mongoDBInterface.ReferenceClass.Item;
import application.mongoDBInterface.ReferenceClass.Vendor;

import java.util.ArrayList;


public class InsertDriver {
	private static MongoClient mongoClient;
	public static MongoDatabase database;
	public static boolean databaseInitialized;
	
	public static void initDatabase() {
		if (databaseInitialized = false) return;
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("Projekt01");
		databaseInitialized = true;
	}
	
	public static void insertItemIntoDatabase() {
		initDatabase();
		
		MongoCollection<Item> mongoCollection = database.getCollection("item", Item.class);
		
		ArrayList<Vendor> vendors = new ArrayList<Vendor>();
		vendors.add(new Vendor("vendor1",""));
		vendors.add(new Vendor("vendor2",""));
		Item item1 = new Item("ABC123","Supertoller 3GB Computer",3.05,vendors);
		
		mongoCollection.insertOne(item1);
		
		//database.getCollection("item").insertOne(new Document("name","computer 2 GB").append("testattribut","Testwert"));
	}
}