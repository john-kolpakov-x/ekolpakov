package kz.greetgo.ekolpakov.html;

public class HtmlText extends HtmlPart {
  private final String content;
  private final int indexFrom;
  private final int indexTo;

  public HtmlText(String content, int indexFrom, int indexTo) {
    this.content = content;
    this.indexFrom = indexFrom;
    this.indexTo = indexTo;
  }

  @Override
  public void printTo(StringBuilder sb) {
    sb.append(content, indexFrom, indexTo);
  }
}
