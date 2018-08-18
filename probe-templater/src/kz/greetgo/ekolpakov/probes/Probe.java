package kz.greetgo.ekolpakov.probes;

import kz.greetgo.ekolpakov.templates.Templates;
import kz.greetgo.ekolpakov.util.ClassResourceLoader;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

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

    VelocityEngine ve = new VelocityEngine();
    ve.setProperty(VelocityEngine.RUNTIME_LOG_NAME, "local-engine");

    ve.init(pp);

    VelocityContext velocityContext = new VelocityContext();

    velocityContext.put("name", "Velocity");

    Template template = ve.getTemplate("asd.vm");

    StringWriter sw = new StringWriter();

    template.merge(velocityContext, sw);

    System.out.println("result = [[" + sw.toString() + "]]");
  }
}
