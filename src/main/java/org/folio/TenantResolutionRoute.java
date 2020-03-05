package org.folio;

import static io.vertx.core.http.HttpMethod.CONNECT;
import static io.vertx.core.http.HttpMethod.DELETE;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.HEAD;
import static io.vertx.core.http.HttpMethod.OPTIONS;
import static io.vertx.core.http.HttpMethod.OTHER;
import static io.vertx.core.http.HttpMethod.PATCH;
import static io.vertx.core.http.HttpMethod.POST;
import static io.vertx.core.http.HttpMethod.PUT;
import static io.vertx.core.http.HttpMethod.TRACE;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import io.quarkus.vertx.web.Route;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.RandomUtils;

@ApplicationScoped
public class TenantResolutionRoute {

    @Route(regex = "/eholdings/.*", methods = {OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT, PATCH, OTHER})
    public void handle(RoutingContext rc) {
        HttpServerRequest req = rc.request();
        HttpServerResponse res = rc.response();

        String tenant = req.getHeader("x-okapi-tenant");
        //res.putHeader("x-okapi-tenant-ds", getTenantDs(tenant));
        res.putHeader("x-okapi-tenant", getTenantDs(tenant));

        res.end();
    }

    private String getTenantDs(String tenant) {
        String idx = "00" + RandomUtils.nextInt(1, 3);

        return defaultIfBlank(tenant + idx, "EMPTY" + idx);
    }

}