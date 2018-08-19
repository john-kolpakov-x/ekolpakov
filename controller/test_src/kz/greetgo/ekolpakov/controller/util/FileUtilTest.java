package kz.greetgo.ekolpakov.controller.util;

import kz.greetgo.util.RND;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kz.greetgo.ekolpakov.controller.util.OperationUtil.mustTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest {

  Path testRoot;

  @BeforeMethod
  public void prepareTestRoot() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    testRoot = Paths.get("build")
      .resolve("tests_data")
      .resolve("FileUtilTest")
      .resolve(sdf.format(new Date()) + "__" + RND.intStr(4))
    ;
  }

  @Test
  public void findRoot() throws Exception {
    File file = testRoot.resolve(".greetgo").resolve("project-name.txt").toFile();
    mustTrue(file.getParentFile().mkdirs());
    Files.write(file.toPath(), "Привет".getBytes(UTF_8));

    File subDir = testRoot.resolve("asd").resolve("dsa").resolve("wow").toFile();
    mustTrue(subDir.mkdirs());

    //
    //
    Path root = FileUtil.findRoot(subDir.toPath());
    //
    //

    assertThat(root.toAbsolutePath()).isEqualTo(testRoot.toAbsolutePath());
  }

}
