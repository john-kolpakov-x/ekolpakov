package kz.greetgo.ekolpakov.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.ekolpakov.controller.model.NavRef;
import kz.greetgo.ekolpakov.controller.register.ForumRegister;
import kz.greetgo.ekolpakov.controller.util.Controller;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.mvc.model.MvcModel;

import java.util.ArrayList;
import java.util.List;

@Bean
@ControllerPrefix("/page")
public class PageController implements Controller {

  public BeanGetter<ForumRegister> forumRegister;

  @OnGet("/forum")
  public String forum(MvcModel model, @Par("offset") int offset, RequestTunnel tunnel) {
    String forumId = tunnel.requestAttributes().get("forum-id");

    model.setParam("nav", getNav());

    model.setParam("page", forumRegister.get().getForumMessagePage(forumId, offset));

    return "forum.vm";
  }

  private List<NavRef> getNav() {
    List<NavRef> ret = new ArrayList<>();
    ret.add(NavRef.parse("  Главная страница ~ /api/page/forum"));
    ret.add(NavRef.parse("  Блоги            ~ /api/page/forum"));
    ret.add(NavRef.parse("* Форум            ~ /forum/default_forum_page.html?offset=100"));
    ret.add(NavRef.parse("  Заметки          ~ /api/page/forum"));
    return ret;
  }
}
