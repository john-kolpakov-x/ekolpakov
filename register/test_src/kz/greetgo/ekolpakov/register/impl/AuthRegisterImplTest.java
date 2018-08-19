package kz.greetgo.ekolpakov.register.impl;

import org.testng.annotations.Test;

public class AuthRegisterImplTest {

  @Test
  public void testName() {
    AuthRegisterImpl register = new AuthRegisterImpl();

    String login = register.login("asd", "dsa");

    System.out.println(login);
  }
}
