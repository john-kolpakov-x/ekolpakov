package kz.greetgo.ekolpakov.probes;

import kz.greetgo.ekolpakov.html.HtmlCorrector;
import kz.greetgo.ekolpakov.templates.Templates;
import kz.greetgo.ekolpakov.util.ClassResourceLoader;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.util.introspection.UberspectImpl;
import org.apache.velocity.util.introspection.UberspectPublicFields;

import java.io.StringWriter;
import java.util.Properties;

public class Probe {
  public static void main(String[] args) {
    new Probe().exec();
  }

  private void exec() {
    Properties pp = new Properties();
    pp.setProperty("file.resource.loader.description", "Load resources from classpath");
    pp.setProperty("file.resource.loader.class", ClassResourceLoader.class.getName());
    pp.setProperty("file.resource.loader.from-class", Templates.class.getName());
    pp.setProperty("file.resource.loader.cache", "true");
    pp.setProperty("file.resource.loader.modificationCheckInterval", "2");
    pp.setProperty("runtime.introspector.uberspect",
      UberspectImpl.class.getName() + ", " + UberspectPublicFields.class.getName());
    pp.setProperty("input.encoding", "UTF-8");
    pp.setProperty("directive.foreach.maxloops", "-1");

    VelocityEngine ve = new VelocityEngine();
    ve.setProperty(VelocityEngine.RUNTIME_LOG_NAME, "local-engine");

    ve.init(pp);

    VelocityContext velocityContext = new VelocityContext();

    Page page = new Page();
    page.title = "Page title";

    velocityContext.put("name", "Velocity");
    velocityContext.put("page", page);

    Template template = ve.getTemplate("probe.vm");

    StringWriter sw = new StringWriter();

    template.merge(velocityContext, sw);

    HtmlCorrector html = HtmlCorrector.of(sw.toString());

    System.out.println("html = [[" + html.result() + "]]");
  }
}
