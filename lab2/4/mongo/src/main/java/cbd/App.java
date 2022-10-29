package cbd;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {


   public static long countLocalidades(MongoCollection<Document> c,String locallidade){
      Bson query = eq("localidade", locallidade);  
      return c.countDocuments(query);
   }

   public static void countRestByLocalidade(MongoCollection<Document> collection){
      collection.aggregate(
         Arrays.asList(
            Aggregates.group("$localidade", Accumulators.sum("count", 1))
         )
      ).forEach(doc -> System.out.println(doc.toJson()));
   }

   
         
   public static void main( String args[] ) {
      // Creating a Mongo client
      //String uri = "mongodb+srv://admin:admin@cbd.qkxdku5.mongodb.net/?retryWrites=true&w=majority";
      try(MongoClient client = MongoClients.create()){
         MongoDatabase database = client.getDatabase("cbd");
         MongoCollection<Document> collection = database.getCollection("restaurants");
         System.out.println();


 
         collection.createIndex(Indexes.text("nome"));
         collection.createIndex(Indexes.ascending("localidade"));
         collection.createIndex(Indexes.ascending("gastronomia"));
         //collection.dropIndexes();
         for (Document index : collection.listIndexes()) {
            System.out.println(index.toJson());
         }

         long a=countLocalidades(collection,"Bronx");
         System.out.println(a);

         countRestByLocalidade(collection);








         

         //MongoCursor<Document> cursor= collection.find()

         //Bson filter = Filters.text("los amigos");
         //collection.find(filter).forEach(doc -> System.out.println(doc.toJson()));






         //Bson filter = eq("localidade", "Brooklyn");
         //Bson sort = Sorts.ascending("localidade");
         //Bson projection = fields(include("nome","localidade"), excludeId());
         //MongoCursor<Document> cursor = collection.find(filter).limit(10).sort(sort).projection(projection).cursor();

         /*Bson projectedFields = Projections.fields( 
                   Projections.include("nome","localidade"), 
                   Projections.excludeId()); 
         MongoCursor<Document> cursor = collection.find().projection(projectedFields).limit(10).cursor();
         while (cursor.hasNext()){
            System.out.println(cursor.next().toJson());
         }*/



         
      }
   }
}