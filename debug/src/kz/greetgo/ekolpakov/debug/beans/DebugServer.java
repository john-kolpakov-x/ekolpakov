package kz.greetgo.ekolpakov.debug.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.ekolpakov.controller.util.Modules;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

import static kz.greetgo.ekolpakov.controller.util.OperationUtil.mustTrue;

@Bean
public class DebugServer implements HasAfterInject {

  public static final int PORT = 13_13;

  public final Server server = new Server(PORT);

  public BeanGetter<Utf8Filter> utf8Filter;
  public BeanGetter<CrossOriginFilter> crossOriginFilter;
  public BeanGetter<JettyControllerServlet> jettyControllerServlet;
  public BeanGetter<ForwardFilter> forwardFilter;

  @Override
  public void afterInject() {
    File dist = Modules.clientHtml().resolve("build").resolve("dest").toFile();

    if (!dist.exists()) {
      mustTrue(dist.mkdirs());
    }

    WebAppContext webAppServlet = new WebAppContext(dist.getAbsolutePath(), "/");

    utf8Filter.get().registerTo(webAppServlet);
    crossOriginFilter.get().registerTo(webAppServlet);
    forwardFilter.get().registerTo(webAppServlet);

    jettyControllerServlet.get().registerTo(webAppServlet);

    server.setHandler(webAppServlet);
  }

  public Server start() throws Exception {
    server.start();

    String url = "Go to http://localhost:" + PORT + "/api/auth/probe";
    System.err.println("[[[                                ]]]");
    System.err.println("[[[ Stand server has been launched ]]] [[[ " + url + " ]]]");
    System.err.println("[[[                                ]]]");

    return server;
  }
}
