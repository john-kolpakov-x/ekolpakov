package kz.greetgo.ekolpakov.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.ekolpakov.controller.model.Session;
import kz.greetgo.ekolpakov.controller.register.AuthRegister;

import java.util.Optional;

@Bean
public class AuthRegisterImpl implements AuthRegister {
  @Override
  public String login(String username, String password) {
    return "OK";
  }

  @Override
  public void setCurrentSession(Session session) {
    if (session == null) {
      sessionThreadLocal.remove();
    } else {
      sessionThreadLocal.set(session);
    }
  }

  private final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();

  @Override
  public Optional<Session> currentSession() {
    return Optional.ofNullable(sessionThreadLocal.get());
  }
}
