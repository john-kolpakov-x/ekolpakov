package kz.greetgo.ekolpakov.html;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlCorrectorUtil {

  public static final String ANCHOR_START = "<a ";
  public static final String ANCHOR_FINISH = "</a>";
  public static final String HIDDEN_ANCHOR_START = "<aa ";
  public static final String HIDDEN_ANCHOR_FINISH = "</aa>";
  private static final String HREF = "href";

  public static void parse(List<HtmlPart> partList, String content) {
    int index = 0;

    MAIN:
    while (true) {
      int aStart = content.indexOf(ANCHOR_START, index);
      if (aStart < 0) {
        addText(partList, content, index, content.length());
        return;
      }
      int aClose = content.indexOf(">", aStart + ANCHOR_START.length());
      if (aClose < 0) {
        addText(partList, content, index, content.length());
        return;
      }

      int aFinish = content.indexOf(ANCHOR_FINISH, aClose + 1);
      if (aFinish < 0) {
        addText(partList, content, index, content.length());
        return;
      }

      String aContent = content.substring(aStart, aClose);

      int hrefStartIndex = aContent.indexOf(HREF);

      if (hrefStartIndex < 0) {
        int index2 = aFinish + ANCHOR_FINISH.length();
        addText(partList, content, index, index2);
        index = index2;
        continue MAIN;
      }

      int quoteIndex = hrefStartIndex + HREF.length();

      IN:
      while (true) {
        if (quoteIndex >= aContent.length()) {
          int index2 = aFinish + ANCHOR_FINISH.length();
          addText(partList, content, index, index2);
          index = index2;
          continue MAIN;
        }
        if (aContent.charAt(quoteIndex) == '=') {
          break IN;
        }
        quoteIndex++;
      }

      quoteIndex++;


      char hrefQuote = ' ';

      while (hrefQuote != '\'' && hrefQuote != '"') {
        if (quoteIndex >= aContent.length()) {
          int index2 = aFinish + ANCHOR_FINISH.length();
          addText(partList, content, index, index2);
          index = index2;
          continue MAIN;
        }
        hrefQuote = aContent.charAt(quoteIndex++);
      }

      int hrefValueStartIndex = quoteIndex;

      IN:
      while (true) {
        if (quoteIndex >= aContent.length()) {
          int index2 = aFinish + ANCHOR_FINISH.length();
          addText(partList, content, index, index2);
          index = index2;
          continue MAIN;
        }
        char c2 = aContent.charAt(quoteIndex);
        if (c2 == hrefQuote) {
          break IN;
        }
        quoteIndex++;
      }

      int hrefValueEndIndex = quoteIndex;

      int hrefEndIndex = quoteIndex + 1;

      addText(partList, content, index, aStart);

      partList.add(new HtmlAnchor(content,
        aStart, aClose, aFinish,
        hrefQuote,
        hrefStartIndex + aStart, hrefEndIndex + aStart,
        hrefValueStartIndex + aStart,
        hrefValueEndIndex + aStart
      ));

      index = aFinish + ANCHOR_FINISH.length();
    }

  }

  private static void addText(List<HtmlPart> partList, String content, int indexFrom, int indexTo) {
    if (indexFrom != indexTo) {
      partList.add(new HtmlText(content, indexFrom, indexTo));
    }
  }

  public static void correct(List<? extends HtmlPart> partList) {
    List<HtmlAnchor> anchorList = partList.stream()
      .filter(p -> p instanceof HtmlAnchor)
      .map(p -> (HtmlAnchor) p)
      .sorted(Comparator.comparing(HtmlAnchor::href))
      .collect(Collectors.toList());

    String prevHref = null;
    for (HtmlAnchor a : anchorList) {
      if (a.href().equals(prevHref)) {
        a.seoHidden = true;
      }
      prevHref = a.href();
    }
  }
}
