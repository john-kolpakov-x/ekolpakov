package kz.greetgo.ekolpakov.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.ekolpakov.controller.util.Controller;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.model.MvcModel;

@Bean
@ControllerPrefix("/page")
public class PageController implements Controller {

  @OnGet("/asd")
  public String asd(MvcModel model) {
    model.setParam("asd", "Hello from asd");
    return "asd.vm";
  }

}
