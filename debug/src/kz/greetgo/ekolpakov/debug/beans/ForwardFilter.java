package kz.greetgo.ekolpakov.debug.beans;

import kz.greetgo.depinject.core.Bean;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Bean
public class ForwardFilter implements Filter {
  public void registerTo(WebAppContext webAppContext) {
    webAppContext.addFilter(new FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST));
  }

  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    filter(chain, (HttpServletRequest) request, (HttpServletResponse) response);
  }

  private static final Pattern FORUM = Pattern.compile("/forum/(\\w+)\\.html");

  private void filter(FilterChain chain,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

    String uri = request.getRequestURI();

    {
      Matcher matcher = FORUM.matcher(uri);
      if (matcher.matches()) {
        request.setAttribute("forum-id", matcher.group(1));
        request.getRequestDispatcher("/api/page/forum").forward(request, response);
        return;
      }
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {}
}