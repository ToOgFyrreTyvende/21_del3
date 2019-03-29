package web;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.Collections;

@Provider
public class Interceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(final WriterInterceptorContext context)
            throws IOException, WebApplicationException {

        final Object entity = context.getEntity();

        System.out.println(context.getPropertyNames());

        context.abortWith(Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource.")
                .build());

        final Result result = new Result(entity, 200);

        // Tell JAX-RS about new entity.
        context.setEntity(result);

        // Tell JAX-RS the type of new entity.
        context.setType(Result.class);
        context.setGenericType(Result.class);

        // Pass the control to JAX-RS.
        context.proceed();
    }

}