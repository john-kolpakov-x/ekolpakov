package kz.greetgo.ekolpakov.register.z_etc;

import org.bson.types.ObjectId;

public class MongoUtil {
  public static String toStr(Object objectValue) {
    if (objectValue == null) {
      return null;
    }

    if (objectValue instanceof String) {
      return (String) objectValue;
    }

    if (objectValue instanceof ObjectId) {
      ObjectId objectId = (ObjectId) objectValue;
      return objectId.toHexString();
    }

    throw new IllegalArgumentException("To String cannot convert " + objectValue.getClass() + " = " + objectValue);
  }

  public static ObjectId toId(Object objectValue) {
    if (objectValue == null) {
      return null;
    }

    if (objectValue instanceof ObjectId) {
      return (ObjectId) objectValue;
    }

    if (objectValue instanceof String) {
      return new ObjectId((String) objectValue);
    }

    throw new IllegalArgumentException("To ObjectId cannot convert " + objectValue.getClass() + " = " + objectValue);
  }
}
