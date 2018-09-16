package kz.greetgo.ekolpakov.register.probes;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.model.DBCollectionUpdateOptions;
import kz.greetgo.ekolpakov.controller.util.App;
import org.bson.types.ObjectId;

public class MongoDbProbeUpdateById {
  public static void main(String[] args) {
    new MongoDbProbeUpdateById().execute();
  }

  private void execute() {
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    DB database = mongoClient.getDB(System.getProperty("user.name") + "_" + App.name());

    DBCollection sinus = database.getCollection("CoSinus");

    {
      DBCollectionUpdateOptions options = new DBCollectionUpdateOptions()
        .upsert(true)
        .multi(false);

      BasicDBObject query = new BasicDBObject();
      query.put("_id", new ObjectId("5b9dfff1fb5785b6d3a49782"));
//      query.put("_id", new BasicDBObject().put("$oid", "5b9dfff1fb5785b6d3a49782"));

      BasicDBObject update = new BasicDBObject();
      update.put("wow.asd2.dsa", "SIMUS");

      BasicDBObject set = new BasicDBObject();
      set.put("$set", update);

      sinus.update(query, set, options);
    }

    System.out.println("database = " + database);
  }
}
