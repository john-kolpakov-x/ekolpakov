package kz.pompei.ekolpakov.server.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
      .addResourceLocations("classpath:/static/");

    registry.addResourceHandler("/static2/**")
      .resourceChain(false)
      .addResolver(new ResourceResolver() {
        @Override
        public Resource resolveResource(HttpServletRequest request,
                                        String requestPath,
                                        List<? extends Resource> locations,
                                        ResourceResolverChain chain) {

          System.out.println("g5423v56 :: requestPath = " + requestPath + ", locations = " + locations + ", chain = " + chain);


          if ("wow/wer.css".equals(requestPath)) {

            Path werCss = Paths.get(System.getProperty("java.io.tmpdir"))
              .resolve("ekolpakov")
              .resolve("wow")
              .resolve("wer.css");

            if (Files.exists(werCss)) {
              return new FileSystemResource(werCss);
            }

            werCss.getParent().toFile().mkdirs();

            try (PrintStream out = new PrintStream(werCss.toFile(), StandardCharsets.UTF_8)) {
              out.println("body {");
              out.println("  color: green;");
              out.println("}");
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

            return new FileSystemResource(werCss);

          }

          return null;
        }

        @Override
        public String resolveUrlPath(String resourcePath,
                                     List<? extends Resource> locations,
                                     ResourceResolverChain chain) {
          return "booms";
        }
      })
    ;
  }
}
