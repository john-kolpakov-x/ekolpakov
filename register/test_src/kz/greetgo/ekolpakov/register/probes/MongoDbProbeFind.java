package kz.greetgo.ekolpakov.register.probes;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.DBCollectionUpdateOptions;
import kz.greetgo.ekolpakov.controller.util.App;
import org.bson.types.ObjectId;

public class MongoDbProbeFind {
  public static void main(String[] args) {
    new MongoDbProbeFind().execute();
  }

  private void execute() {
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    DB database = mongoClient.getDB(System.getProperty("user.name") + "_" + App.name());

    DBCollection sinus = database.getCollection("CoSinus");

    {
      DBCollectionUpdateOptions options = new DBCollectionUpdateOptions()
        .upsert(true)
        .multi(false);

      BasicDBObject projection = new BasicDBObject();
      projection.put("wow", 1);

      DBObject result = sinus.findOne(new ObjectId("5b9dfff1fb5785b6d3a49782"), projection);

      System.out.println(result);
    }
  }
}
