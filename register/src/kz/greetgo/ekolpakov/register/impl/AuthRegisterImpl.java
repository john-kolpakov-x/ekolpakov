package kz.greetgo.ekolpakov.register.impl;

import kz.greetgo.ekolpakov.controller.register.AuthRegister;

public class AuthRegisterImpl implements AuthRegister {
  @Override
  public String login(String username, String password) {
    return "OK";
  }
}
