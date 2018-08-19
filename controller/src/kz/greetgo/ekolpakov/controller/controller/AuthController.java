package kz.greetgo.ekolpakov.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.ekolpakov.controller.util.Controller;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;

@Bean
@ControllerPrefix("/auth")
public class AuthController implements Controller {

  @AsIs
  @OnGet("/probe")
  public String probe() {
    return "probe OK";
  }

}
