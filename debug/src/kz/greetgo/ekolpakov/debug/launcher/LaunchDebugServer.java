package kz.greetgo.ekolpakov.debug.launcher;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
import kz.greetgo.ekolpakov.controller.util.Modules;
import kz.greetgo.ekolpakov.debug.bean_container.DebugBeanContainer;

public class LaunchDebugServer {
  public static void main(String[] args) throws Exception {
    new LaunchDebugServer().exec();
  }

  private void exec() throws Exception {
    DepinjectUtil.implementAndUseBeanContainers(
      "kz.greetgo.ekolpakov.debug",
      Modules.debug().resolve("build").resolve("src_bean_containers").toFile().getAbsolutePath()
    );

    DebugBeanContainer beanContainer = Depinject.newInstance(DebugBeanContainer.class);

    beanContainer.server().start().join();
  }
}
