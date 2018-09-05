package kz.greetgo.ekolpakov.controller.model;

public class NavRef {
  public String title;
  public String ref;
  public boolean active;

  public static NavRef parse(String refStr) {
    refStr = refStr.trim();

    NavRef ret = new NavRef();
    ret.active = false;
    if (refStr.startsWith("*")) {
      ret.active = true;
      refStr = refStr.substring(1);
    }
    String[] split = refStr.split("~");
    ret.title = split[1].trim();
    ret.ref = split[2].trim();
    return ret;
  }

  @Override
  public String toString() {
    return "NavRef{" +
      "title='" + title + '\'' +
      ", ref='" + ref + '\'' +
      ", active=" + active +
      '}';
  }
}
