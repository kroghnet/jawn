package net.javapla.jawn.core.renderers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.javapla.jawn.core.Context;
import net.javapla.jawn.core.MediaType;

@Singleton
final class JsonRendererEngine implements RendererEngine {

    private final ObjectMapper mapper;
    
    @Inject
    JsonRendererEngine(final ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    public void invoke(final Context context, final Object obj) throws Exception {
        
        if (obj instanceof byte[]) {
            context.resp().send((byte[])obj);
        } else if (obj instanceof String) {
            context.resp().send(((String) obj).getBytes(context.req().charset()));
        } else {
            context.resp().send(mapper.writeValueAsBytes(obj));
        }
    }

    @Override
    public MediaType[] getContentType() {
        return new MediaType[]{ MediaType.JSON };
    }
}
