package org.folio;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.vertx.http.runtime.filters.Filters;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LoggingFilter {

  private static final Logger log = Logger.getLogger(LoggingFilter.class);

  public void registerFilter(@Observes Filters filters) {
    filters.register(rc -> {
      logRequest(rc.request());

      long start = System.currentTimeMillis();

      rc.next();

      long end = System.currentTimeMillis();

      logResponse(rc.response(), end - start);
    }, 100);
  }

  private void logRequest(HttpServerRequest req) {
    HttpMethod method = req.method();
    String uri = req.absoluteURI();
    MultiMap headers = req.headers();

    log.infof("TNR called for:\n %s %s\n%s", method, uri, headers);
  }

  private void logResponse(HttpServerResponse res, long duration) {
    MultiMap headers = res.headers();

    log.infof("TNR completed in %d ms with:\n%s", duration, headers.size() > 0 ? headers : "NO HEADERS");
  }

}
