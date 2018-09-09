package kz.greetgo.ekolpakov.debug.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.ekolpakov.controller.util.Controller;
import kz.greetgo.ekolpakov.controller.views.AppViews;
import kz.greetgo.ekolpakov.debug.util.JettyWarServletAbstract;
import kz.greetgo.mvc.builder.ExecDefinition;
import kz.greetgo.mvc.interfaces.Views;

import java.util.List;
import java.util.stream.Collectors;

@Bean
public class JettyControllerServlet extends JettyWarServletAbstract {

  public BeanGetter<List<Controller>> controllerList;

  @Override
  protected List<Object> getControllerList() {
    return controllerList.get().stream().map(c -> (Object) c).collect(Collectors.toList());
  }

  public BeanGetter<AppViews> appViews;

  @Override
  protected Views getViews() {
    return appViews.get();
  }

  @Override
  protected String getTargetSubContext() {
    return "/api";
  }

  @Override
  protected void afterRegistered() {
    System.err.println("[WebAppContext] --------------------------------------");
    System.err.println("[WebAppContext] -- USING CONTROLLERS:");
    for (ExecDefinition execDefinition : execDefinitionList()) {

      System.err.println("[WebAppContext] --   " + execDefinition.infoStr());
    }
    System.err.println("[WebAppContext] --------------------------------------");
  }
}
