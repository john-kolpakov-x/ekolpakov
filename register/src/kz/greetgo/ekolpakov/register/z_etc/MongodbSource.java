package kz.greetgo.ekolpakov.register.z_etc;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface MongodbSource {
  MongoCollection<Document> forum();

  MongoCollection<Document> forumMessage();
}
