package web;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class PoweredByResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("X-Powered-By", "42/2's dtåbelyde");
        ResponseWrapper wrapped = new ResponseWrapper(responseContext.getStatus(), responseContext.getEntity());
        responseContext.setEntity(wrapped);
    }
}