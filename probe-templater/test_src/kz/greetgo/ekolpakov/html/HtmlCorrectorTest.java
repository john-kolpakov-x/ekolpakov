package kz.greetgo.ekolpakov.html;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlCorrectorTest {

  @Test(timeOut = 1000)
  public void correctSomeAnchors() {

    HtmlCorrector corrector = HtmlCorrector.of("" +
      "<a href=\"http://localhost/1.html\">Ref 1</a>\n" +
      "<a href=\"http://localhost/2.html\" alt=\"wow\">Ref 2</a>\n" +
      "<a href=\"http://localhost/1.html\">Ref 1 asd</a>\n" +
      "<a href=\"http://localhost/2.html\">Ref 2</a>\n" +
      "<a alt=\"sys\" href=\"http://localhost/3.html\">Ref 3 asd</a>\n" +
      "<a alt=\"oops\" href=\"http://localhost/2.html\">Ref 2</a>\n" +
      "<a href=\"http://localhost/4.html\">Ref 4 asd</a>\n" +
      "");

    String result = corrector.result();

    assertThat(result).isEqualTo("" +
      "<a href=\"http://localhost/1.html\">Ref 1</a>\n" +
      "<a href=\"http://localhost/2.html\" alt=\"wow\">Ref 2</a>\n" +
      "<aa href=\"http://localhost/1.html\">Ref 1 asd</aa>\n" +
      "<aa href=\"http://localhost/2.html\">Ref 2</aa>\n" +
      "<a alt=\"sys\" href=\"http://localhost/3.html\">Ref 3 asd</a>\n" +
      "<aa alt=\"oops\" href=\"http://localhost/2.html\">Ref 2</aa>\n" +
      "<a href=\"http://localhost/4.html\">Ref 4 asd</a>\n" +
      "");
  }
}