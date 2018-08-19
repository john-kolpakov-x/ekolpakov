package kz.greetgo.ekolpakov.controller.util;

import java.io.File;
import java.nio.file.Path;

public class FileUtil {
  public static Path findRoot(Path currentDir) {

    File current = currentDir.toFile();

    while (current != null) {
      if (current.toPath().resolve(".greetgo").toFile().isDirectory()) {
        return current.toPath();
      }

      current = current.getParentFile();
    }

    throw new RuntimeException("Cannot find root");
  }
}
