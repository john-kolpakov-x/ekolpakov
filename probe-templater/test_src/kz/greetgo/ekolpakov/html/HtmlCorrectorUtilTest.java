package kz.greetgo.ekolpakov.html;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlCorrectorUtilTest {

  @Test
  public void parse_NormalParser() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, " hello <a x='asd' href=\"http://localhost/1.html\" y='ewq'>ref 1</a> world ");
    //
    //

    assertThat(partList).hasSize(3);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);
    assertThat(partList.get(1)).isInstanceOf(HtmlAnchor.class);
    assertThat(partList.get(2)).isInstanceOf(HtmlText.class);

    {
      StringBuilder sb = new StringBuilder();
      partList.get(0).printTo(sb);
      assertThat(sb.toString()).isEqualTo(" hello ");
    }
    {
      HtmlAnchor a = (HtmlAnchor) partList.get(1);
      assertThat(a.fullText()).isEqualTo("<a x='asd' href=\"http://localhost/1.html\" y='ewq'>ref 1</a>");
      assertThat(a.href()).isEqualTo("http://localhost/1.html");
      assertThat(a.innerText()).isEqualTo("ref 1");
      assertThat(a.hrefQuote()).isEqualTo('"');
      a.seoHidden = true;
      assertThat(a.fullText()).isEqualTo("<aa x='asd' href=\"http://localhost/1.html\" y='ewq'>ref 1</aa>");
    }
    {
      StringBuilder sb = new StringBuilder();
      partList.get(2).printTo(sb);
      assertThat(sb.toString()).isEqualTo(" world ");
    }
  }

  @Test
  public void parse_oneAnchor() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a href='http://localhost/13.html'>REF 13</a>");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlAnchor.class);

    HtmlAnchor a = (HtmlAnchor) partList.get(0);
    assertThat(a.fullText()).isEqualTo("<a href='http://localhost/13.html'>REF 13</a>");
    assertThat(a.href()).isEqualTo("http://localhost/13.html");
    assertThat(a.innerText()).isEqualTo("REF 13");
    assertThat(a.hrefQuote()).isEqualTo('\'');
    a.seoHidden = true;
    assertThat(a.fullText()).isEqualTo("<aa href='http://localhost/13.html'>REF 13</aa>");
  }

  @Test
  public void parse_leftHrefClose() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a href='http://localhost/13.html>REF 13</a>");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a href='http://localhost/13.html>REF 13</a>");
  }

  @Test
  public void parse_hrefNoEqualSign() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a href d>REF 13</a>");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a href d>REF 13</a>");
  }


  @Test
  public void parse_hrefNoQuoteAfterEqualSign() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a href = d>REF 13</a>");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a href = d>REF 13</a>");
  }

  @Test
  public void parse_noHref() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a asd='asd'>REF 13</a>");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a asd='asd'>REF 13</a>");
  }

  @Test
  public void parse_noCloseBracket() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a asd='asd' ");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a asd='asd' ");
  }

  @Test
  public void parse_noNoFinishTag() {
    List<HtmlPart> partList = new ArrayList<>();

    //
    //
    HtmlCorrectorUtil.parse(partList, "<a asd='asd'> wow ");
    //
    //

    assertThat(partList).hasSize(1);

    assertThat(partList.get(0)).isInstanceOf(HtmlText.class);

    StringBuilder sb = new StringBuilder();
    partList.get(0).printTo(sb);
    assertThat(sb.toString()).isEqualTo("<a asd='asd'> wow ");
  }

  private HtmlAnchor parse(String text) {
    List<HtmlPart> partList = new ArrayList<>();
    HtmlCorrectorUtil.parse(partList, text);
    assertThat(partList).hasSize(1);
    return (HtmlAnchor) partList.get(0);
  }

  @Test
  public void correct() {
    List<HtmlAnchor> partList = new ArrayList<>();
    partList.add(parse("<a href='ref1'>content 1</a>"));
    partList.add(parse("<a href='ref3'>content 2</a>"));
    partList.add(parse("<a href='ref2'>content 3</a>"));
    partList.add(parse("<a href='ref3'>content 4</a>"));
    partList.add(parse("<a href='ref2'>content 5</a>"));
    partList.add(parse("<a href='ref3'>content 6</a>"));
    partList.add(parse("<a href='ref4'>content 7</a>"));

    //
    //
    HtmlCorrectorUtil.correct(partList);
    //
    //

    assertThat(partList.get(0).seoHidden).isFalse();
    assertThat(partList.get(1).seoHidden).isFalse();
    assertThat(partList.get(2).seoHidden).isFalse();
    assertThat(partList.get(3).seoHidden).isTrue();
    assertThat(partList.get(4).seoHidden).isTrue();
    assertThat(partList.get(5).seoHidden).isTrue();
    assertThat(partList.get(6).seoHidden).isFalse();
  }
}