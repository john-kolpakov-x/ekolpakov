package kz.greetgo.ekolpakov.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class ClassResourceLoader extends ResourceLoader {

  private Class fromClass;

  public void init(ExtProperties configuration) {

    try {
      fromClass = Class.forName(configuration.getString("from-class"));
      log.debug("ClassResourceLoader: load resourcs from class " + fromClass.getName());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    log.trace("ClassResourceLoader: initialization complete.");
  }

  /**
   * Get a Reader so that the Runtime can build a
   * template with it.
   *
   * @param name     name of template to get
   * @param encoding asked encoding
   * @return InputStream containing the template
   * @throws ResourceNotFoundException if template not found
   *                                   in  classpath.
   * @since 2.0
   */
  public Reader getResourceReader(String name, String encoding)
    throws ResourceNotFoundException {
    Reader result = null;

    if (StringUtils.isEmpty(name)) {
      throw new ResourceNotFoundException("No template name provided");
    }

    /*
     * look for resource in thread classloader first (e.g. WEB-INF\lib in
     * a servlet container) then fall back to the system classloader.
     */
    InputStream rawStream = null;
    try {
      rawStream = fromClass.getResourceAsStream(name);
      if (rawStream != null) {
        result = buildReader(rawStream, encoding);
      }
    } catch (Exception fnfe) {
      if (rawStream != null) {
        try {
          rawStream.close();
        } catch (IOException ioe) {
          log.warn("Error while close input stream", ioe);
        }
      }
      throw new ResourceNotFoundException("ClasspathResourceLoader problem with template: " + name, fnfe);
    }

    if (result == null) {
      String msg = "ClasspathResourceLoader Error: cannot find resource " + name;

      throw new ResourceNotFoundException(msg);
    }

    return result;
  }

  /**
   * @see ResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
   */
  public boolean isSourceModified(Resource resource) {
    return false;
  }

  /**
   * @see ResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
   */
  public long getLastModified(Resource resource) {
    return 0;
  }
}

