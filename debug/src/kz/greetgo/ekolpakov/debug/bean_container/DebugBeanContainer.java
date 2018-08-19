package kz.greetgo.ekolpakov.debug.bean_container;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.ekolpakov.debug.beans.DebugServer;

@Include(BeanConfigRootDebug.class)
public interface DebugBeanContainer extends BeanContainer {
  DebugServer server();
}
