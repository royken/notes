package com.douwe.notes.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Provider
@Produces("application/xml")
public class JAXBMarshaller implements MessageBodyWriter {

    @Context
    protected Providers providers;

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        System.out.println("Les problemes Les problemes");
        boolean bb = type.isAnnotationPresent(XmlRootElement.class);
        System.out.println("La valeur est "+bb);
        return bb;
    }

    @Override
    public long getSize(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try {
            JAXBContext ctx = null;
            ContextResolver<JAXBContext> resolver
                    = providers.getContextResolver(JAXBContext.class, mediaType);
            if (resolver != null) {
                ctx = resolver.getContext(type);
            }
            if (ctx == null) {
                ctx = JAXBContext.newInstance(type);
            }
            ctx.createMarshaller().marshal(t, entityStream);
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

}
