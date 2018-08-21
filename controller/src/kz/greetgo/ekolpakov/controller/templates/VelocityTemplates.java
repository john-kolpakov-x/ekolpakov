package kz.greetgo.ekolpakov.controller.templates;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.ekolpakov.controller.html.HtmlCorrector;
import kz.greetgo.ekolpakov.controller.util.ClassResourceLoader;
import kz.greetgo.mvc.model.MvcModelData;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.util.introspection.UberspectImpl;
import org.apache.velocity.util.introspection.UberspectPublicFields;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

@Bean
public class VelocityTemplates implements HasAfterInject {

  private VelocityEngine engine;

  @Override
  public void afterInject() {
    Properties pp = new Properties();
    pp.setProperty("file.resource.loader.description", "Load resources from classpath");
    pp.setProperty("file.resource.loader.class", ClassResourceLoader.class.getName());
    pp.setProperty("file.resource.loader.from-class", VelocityTemplates.class.getName());
    pp.setProperty("file.resource.loader.cache", "true");
    pp.setProperty("file.resource.loader.modificationCheckInterval", "2");
    pp.setProperty("runtime.introspector.uberspect",
      UberspectImpl.class.getName() + ", " + UberspectPublicFields.class.getName());
    pp.setProperty("input.encoding", "UTF-8");
    pp.setProperty("directive.foreach.maxloops", "-1");

    engine = new VelocityEngine();
    engine.setProperty(VelocityEngine.RUNTIME_LOG_NAME, "local-engine");
    engine.init(pp);
  }

  public String evaluate(String nameVm, MvcModelData model) {
    Template template = engine.getTemplate(nameVm);

    VelocityContext velocityContext = new VelocityContext();
    if (model != null) {
      for (Map.Entry<String, Object> e : model.data.entrySet()) {
        velocityContext.put(e.getKey(), e.getValue());
      }
    }

    StringWriter sw = new StringWriter();

    template.merge(velocityContext, sw);

    HtmlCorrector html = HtmlCorrector.of(sw.toString());

    return html.result();
  }
}
