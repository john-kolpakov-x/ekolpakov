package kz.greetgo.ekolpakov.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.ekolpakov.controller.model.ForumRecord;
import kz.greetgo.ekolpakov.controller.model.ForumToSave;
import kz.greetgo.ekolpakov.controller.register.ForumRegister;
import kz.greetgo.ekolpakov.register.z_etc.MongodbSource;
import kz.greetgo.ekolpakov.register.z_etc.ParentTestNg;
import kz.greetgo.util.RND;
import org.bson.Document;
import org.testng.annotations.Test;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static kz.greetgo.ekolpakov.register.z_etc.MongoUtil.toId;
import static org.assertj.core.api.Assertions.assertThat;

public class ForumRegisterImplTest extends ParentTestNg {

  public BeanGetter<ForumRegister> forumRegister;
  public BeanGetter<MongodbSource> mongodbSource;

  @Test
  public void save_new() {

    String personId = RND.str(10);
    setCurrentPersonId(personId);

    ForumToSave forumToSave = new ForumToSave();
    forumToSave.code = RND.str(10);
    forumToSave.title = RND.str(10);

    //
    //
    ForumRecord record = forumRegister.get().save(forumToSave).orElseThrow(RuntimeException::new);
    //
    //

    assertThat(record).isNotNull();
    assertThat(record.id).isNotNull();
    assertThat(record.code).isEqualTo(forumToSave.code);
    assertThat(record.title).isEqualTo(forumToSave.title);

    Document saved = mongodbSource.get().forum().find(eq("_id", toId(record.id))).limit(1).first();

    assertThat(saved).isNotNull();
    assertThat(saved.get("code")).isEqualTo(forumToSave.code);
    assertThat(saved.get("title")).isEqualTo(forumToSave.title);
    assertThat(saved.get("author")).isEqualTo(personId);
    assertThat(saved.get("createdAt")).isInstanceOf(Date.class);
  }
}
