package kz.greetgo.ekolpakov.controller.html;

import static kz.greetgo.ekolpakov.controller.html.HtmlCorrectorUtil.ANCHOR_FINISH;
import static kz.greetgo.ekolpakov.controller.html.HtmlCorrectorUtil.ANCHOR_START;
import static kz.greetgo.ekolpakov.controller.html.HtmlCorrectorUtil.HIDDEN_ANCHOR_FINISH;
import static kz.greetgo.ekolpakov.controller.html.HtmlCorrectorUtil.HIDDEN_ANCHOR_START;

public class HtmlAnchor extends HtmlPart {
  private final String content;
  private final int aStart;
  private final int aClose;
  private final int aFinish;
  private final char hrefQuote;
  @SuppressWarnings({"FieldCanBeLocal", "unused"})
  private final int hrefStartIndex;
  @SuppressWarnings({"FieldCanBeLocal", "unused"})
  private final int hrefEndIndex;
  private final int hrefValueStartIndex;
  private final int hrefValueEndIndex;

  public boolean seoHidden = false;

  public HtmlAnchor(String content,
                    int aStart, int aClose, int aFinish,
                    char hrefQuote,
                    int hrefStartIndex, int hrefEndIndex,
                    int hrefValueStartIndex, int hrefValueEndIndex) {
    this.content = content;
    this.aStart = aStart;
    this.aClose = aClose;
    this.aFinish = aFinish;
    this.hrefQuote = hrefQuote;
    this.hrefStartIndex = hrefStartIndex;
    this.hrefEndIndex = hrefEndIndex;
    this.hrefValueStartIndex = hrefValueStartIndex;
    this.hrefValueEndIndex = hrefValueEndIndex;
  }

  public String href() {
    return content.substring(hrefValueStartIndex, hrefValueEndIndex);
  }

  public String innerText() {
    return content.substring(aClose + 1, aFinish);
  }

  public char hrefQuote() {
    return hrefQuote;
  }

  @Override
  public void printTo(StringBuilder sb) {
    sb.append(seoHidden ? HIDDEN_ANCHOR_START : ANCHOR_START);

    sb.append(content, aStart + ANCHOR_START.length(), aFinish);

    sb.append(seoHidden ? HIDDEN_ANCHOR_FINISH : ANCHOR_FINISH);
  }

  public String fullText() {
    StringBuilder sb = new StringBuilder();
    printTo(sb);
    return sb.toString();
  }
}
