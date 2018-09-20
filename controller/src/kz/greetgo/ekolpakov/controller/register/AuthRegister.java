package kz.greetgo.ekolpakov.controller.register;

import kz.greetgo.ekolpakov.controller.model.Session;

import java.util.Optional;

public interface AuthRegister {
  String login(String username, String password);

  void setCurrentSession(Session session);

  Optional<Session> currentSession();
}
