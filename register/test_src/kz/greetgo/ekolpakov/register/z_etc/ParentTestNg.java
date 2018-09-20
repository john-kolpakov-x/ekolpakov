package kz.greetgo.ekolpakov.register.z_etc;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.ekolpakov.controller.model.Session;
import kz.greetgo.ekolpakov.controller.register.AuthRegister;
import org.testng.annotations.BeforeMethod;

@ContainerConfig(BeanConfigOfRegisterTests.class)
public class ParentTestNg extends AbstractDepinjectTestNg {

  public BeanGetter<AuthRegister> authRegister;

  protected void setCurrentPersonId(String personId) {
    Session session = new Session();
    session.personId = personId;
    authRegister.get().setCurrentSession(session);
  }

  @BeforeMethod
  public void cleanCurrentSession() {
    authRegister.get().setCurrentSession(null);
  }
}
