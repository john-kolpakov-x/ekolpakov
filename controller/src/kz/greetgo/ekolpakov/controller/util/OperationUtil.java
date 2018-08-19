package kz.greetgo.ekolpakov.controller.util;

public class OperationUtil {
  public static void mustTrue(boolean operationResult) {
    if (operationResult) return;
    throw new RuntimeException("Operation was absent");
  }
}
