package kz.greetgo.ekolpakov.controller.html;

import java.util.ArrayList;
import java.util.List;

public class HtmlCorrector {
  private final List<HtmlPart> partList = new ArrayList<>();

  private HtmlCorrector() {}

  public static HtmlCorrector of(String content) {
    HtmlCorrector ret = new HtmlCorrector();
    HtmlCorrectorUtil.parse(ret.partList, content);
    HtmlCorrectorUtil.correct(ret.partList);
    return ret;
  }

  public String result() {
    StringBuilder sb = new StringBuilder();
    partList.forEach(t -> t.printTo(sb));
    return sb.toString();
  }
}
