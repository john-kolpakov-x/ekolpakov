package kz.greetgo.ekolpakov.controller.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import static kz.greetgo.ekolpakov.controller.util.FileUtil.findRoot;

public class Modules {

  public static Path controller() {
    return findRoot(Paths.get(".")).resolve("controller");
  }

  public static Path debug() {
    return findRoot(Paths.get(".")).resolve("debug");
  }

  public static Path client() {
    return findRoot(Paths.get(".")).resolve("client");
  }

  public static Path clientHtml() {
    return findRoot(Paths.get(".")).resolve("client-html");
  }
}
