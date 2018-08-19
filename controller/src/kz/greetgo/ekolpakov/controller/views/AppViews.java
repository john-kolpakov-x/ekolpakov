package kz.greetgo.ekolpakov.controller.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.mvc.interfaces.MethodInvokedResult;
import kz.greetgo.mvc.interfaces.MethodInvoker;
import kz.greetgo.mvc.interfaces.RequestTunnel;
import kz.greetgo.mvc.interfaces.Views;

import java.lang.reflect.Method;

@Bean
public class AppViews implements Views {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String toJson(Object object, RequestTunnel tunnel, Method method) throws Exception {
    return convertToJson(object);
  }

  private String convertToJson(Object object) throws Exception {
    if (object == null) return null;
    return objectMapper.writer().writeValueAsString(object);
  }

  @Override
  public void performRequest(MethodInvoker methodInvoker) {
    MethodInvokedResult invokedResult = methodInvoker.invoke();

    if (invokedResult.tryDefaultRender()) {
      return;
    }

    throw new RuntimeException("Cannot make request");
  }
}
