package kz.greetgo.ekolpakov.controller.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.ekolpakov.controller.templates.VelocityTemplates;
import kz.greetgo.mvc.interfaces.MethodInvokedResult;
import kz.greetgo.mvc.interfaces.MethodInvoker;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.mvc.interfaces.Views;
import kz.greetgo.mvc.model.MvcModelData;

import java.lang.reflect.Method;

@Bean
public class AppViews implements Views {
  private final ObjectMapper objectMapper = new ObjectMapper();

  private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

  @Override
  public String toJson(Object object, RequestTunnel tunnel, Method method) throws Exception {
    return convertToJson(object);
  }

  private String convertToJson(Object object) throws Exception {
    if (object == null) return null;
    return objectMapper.writer().writeValueAsString(object);
  }

  public BeanGetter<VelocityTemplates> templates;

  @Override
  public void performRequest(MethodInvoker methodInvoker) {
    MethodInvokedResult invokedResult = methodInvoker.invoke();

    if (invokedResult.tryDefaultRender()) {
      return;
    }

    if (invokedResult.error() != null) {
      performError(invokedResult.error(), methodInvoker.tunnel());
    } else {
      performOk(invokedResult.returnedValue(), methodInvoker.tunnel(), methodInvoker.model());
    }

    methodInvoker.tunnel().flushBuffer();
  }

  private void performOk(Object returnedValue, RequestTunnel tunnel, MvcModelData model) {
    if (returnedValue instanceof String && ((String) returnedValue).endsWith(".vm")) {
      String result = templates.get().evaluate((String) returnedValue, model);
      tunnel.setResponseHeader("Content-Type", "text/html; charset=utf-8");
      tunnel.getResponseWriter().write(result);
      return;
    }

    throw new RuntimeException("Cannot perform returnedValue = " + returnedValue);

  }

  private void performError(Throwable error, RequestTunnel tunnel) {
    log.error("Request error", error);
    MvcModelData model = new MvcModelData();
    model.setParam("error", error);
    String result = templates.get().evaluate("error.vm", model);
    tunnel.setResponseHeader("Content-Type", "text/html; charset=utf-8");
    tunnel.getResponseWriter().write(result);
  }
}
