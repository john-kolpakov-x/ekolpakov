package kz.greetgo.ekolpakov.register.beans.all;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.ekolpakov.register.z_etc.MongodbSource;
import org.bson.Document;

@Bean
public class MongodbSourceFactory implements HasAfterInject {

  private MongoCollection<Document> forum;
  private MongoCollection<Document> forumMessage;

  @Override
  public void afterInject() {

    MongoClient mongoClient = new MongoClient();
    MongoDatabase database = mongoClient.getDatabase(System.getProperty("user.name") + "_ekolpakov");

    forum = database.getCollection("forum");
    forumMessage = database.getCollection("forumMessage");

    ensureIndexes();
  }


  @Bean
  public MongodbSource createMongodbSource() {
    return new MongodbSource() {
      @Override
      public MongoCollection<Document> forum() {
        return forum;
      }

      @Override
      public MongoCollection<Document> forumMessage() {
        return forumMessage;
      }
    };
  }

  private void ensureIndexes() {
    Document index = new Document();
    index.append("code", 1);

    IndexOptions options = new IndexOptions();
    options.unique(true);

    forum.createIndex(index, options);
  }
}
