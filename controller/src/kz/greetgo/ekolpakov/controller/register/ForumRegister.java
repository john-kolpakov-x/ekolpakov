package kz.greetgo.ekolpakov.controller.register;

import kz.greetgo.ekolpakov.controller.model.ForumMessagePage;
import kz.greetgo.ekolpakov.controller.model.ForumRecord;
import kz.greetgo.ekolpakov.controller.model.ForumToSave;

import java.util.Optional;

public interface ForumRegister {
  ForumMessagePage getForumMessagePage(String forumCode, int offset);

  Optional<ForumRecord> save(ForumToSave forumToSave);
}
