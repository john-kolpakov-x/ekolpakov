package kz.greetgo.ekolpakov.controller.register;

import kz.greetgo.ekolpakov.controller.model.ForumMessagePage;

public interface ForumRegister {
  ForumMessagePage getForumMessagePage(String forumCode, int offset);
}
