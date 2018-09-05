package kz.greetgo.ekolpakov.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.ekolpakov.controller.model.NavRef;
import kz.greetgo.ekolpakov.controller.util.Controller;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.model.MvcModel;

import java.util.ArrayList;
import java.util.List;

@Bean
@ControllerPrefix("/page")
public class PageController implements Controller {

  @OnGet("/asd")
  public String asd(MvcModel model) {
    model.setParam("asd", "Hello from asd");
    return "asd.vm";
  }

  @OnGet("/forum")
  public String forum(MvcModel model) {
    model.setParam("nav", getNav());
    return "forum.vm";
  }

  private List<NavRef> getNav() {
    List<NavRef> ret = new ArrayList<>();
    ret.add(NavRef.parse("  Главная страница ~ /api/page/forum"));
    ret.add(NavRef.parse("  Блоги            ~ /api/page/forum"));
    ret.add(NavRef.parse("* Форум            ~ /api/page/forum"));
    ret.add(NavRef.parse("  Заметки          ~ /api/page/forum"));
    return ret;
  }

}
