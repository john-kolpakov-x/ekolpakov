package kz.pompei.ekolpakov.server.app;

import kz.pompei.ekolpakov.server.configs.ImporterConfigs;
import kz.pompei.ekolpakov.server.controller.ImporterControllers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ImporterControllers.class, ImporterConfigs.class})
public class LauncherServer {
  public static void main(String[] args) {
    SpringApplication.run(LauncherServer.class, args);
  }
}
