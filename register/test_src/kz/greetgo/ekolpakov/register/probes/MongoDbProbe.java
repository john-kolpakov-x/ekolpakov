package kz.greetgo.ekolpakov.register.probes;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.model.DBCollectionUpdateOptions;
import kz.greetgo.ekolpakov.controller.util.App;

import static java.util.Collections.singletonList;

public class MongoDbProbe {
  public static void main(String[] args) {
    new MongoDbProbe().execute();
  }

  private void execute() {
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    DB database = mongoClient.getDB(System.getProperty("user.name") + "_" + App.name());

    DBCollection sinus = database.getCollection("CoSinus");

    {
      BasicDBObject arrayFilter = new BasicDBObject();
      arrayFilter.put("element", 2);


      DBCollectionUpdateOptions options = new DBCollectionUpdateOptions()
        .upsert(true)
        .multi(false)
        .arrayFilters(singletonList(arrayFilter));

      BasicDBObject query = new BasicDBObject();
      query.put("name", "id.0000-0007");

      BasicDBObject update = new BasicDBObject();
      update.put("root.asd.$[element].description", "Станислав помахал рукой");

      BasicDBObject set = new BasicDBObject();
      set.put("$set", update);

      sinus.update(query, set, options);
    }

    System.out.println("database = " + database);
  }
}
