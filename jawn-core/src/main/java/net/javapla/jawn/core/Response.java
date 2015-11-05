package net.javapla.jawn.core;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import net.javapla.jawn.core.exceptions.PathNotFoundException;

//Result
public class Response {

    /**
     * Http status code
     */
    private int statusCode;
    
    /**
     * Object to be handled by a rendering engine
     */
    private Object renderable;
    
    /**
     * Something like: "text/html" or "application/json"
     */
    private String contentType;
    
    /**
     * Something like: "utf-8" => will be appended to the content-type. eg
     * "text/html; charset=utf-8"
     */
    private String charset;
    
    /**
     * A list of content types this result will handle. If you got a general
     * person object you can render it via application/json and application/xml
     * without changing anything inside your controller for instance.
     */
    private final List<String> supportedContentTypes;
    
    private final Map<String, String> headers;
    
    //TODO convert this to be a part of the renderable
    //renderable(Map<>), renderable(String key, Object value), renderable(Entry<String, Object)
    //Keep state of the renderable at all times - never overwrite, just add
    private Map<String, Object> viewObjects;
    
    private String template;
    
    //README perhaps this ought to be a boolean, as it is solely used as a flag whether to use the 
    //defacto layout or not
    private String layout = "index.html";//Configuration.getDefaultLayout();
    
    public Response(int statusCode) {
        supportedContentTypes = new ArrayList<>();
        headers = new HashMap<>();
        viewObjects = new HashMap<>();
        
//        charset = Constants.ENCODING;
        this.statusCode = statusCode;
    }
    
    
    public Response renderable(Object obj) {
        this.renderable = obj;
        return this;
    }
    public Object renderable() {
        return renderable;
    }
    
    
    public Response contentType(String type) {
        this.contentType = type;
        return this;
    }
    public String contentType() {
        return contentType;
    }
    
    public Response status(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }
    public int status() {
        return statusCode;
    }
    
    public Response charset(String charset) {
        this.charset = charset;
        return this;
    }
    public String charset() {
        return charset;
    }
    
    
    public Response template(String template) {
        this.template = template;
        if (template.endsWith(".st"))
            this.template = template.substring(0, template.length()-3);//FIXME this ought to be up to the templateengine to remove
        return this;
    }
    public String template() {
        return template;
    }
    
    public Response layout(String layout) {
        this.layout = layout;
        if (layout != null) {
            if (layout.endsWith(".st"))
                this.layout = layout.substring(0, layout.length()-3);
            if (!this.layout.endsWith(".html"))
                this.layout += ".html";
        }
        return this;
    }
    public String layout() {
        return layout;
    }
    public Response noLayout() {
        layout = null;
        return this;
    }
    
    public Map<String, String> headers() {
        return headers;
    }
    public Response addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }
    
    public Response view(String name, Object value) {
        viewObjects.put(name, value);
        return this;
    }
    public Response view(Map<String, Object> values) {
        viewObjects.putAll(values);
        return this;
    }
    public Map<String, Object> getViewObjects() {
        return viewObjects;
    }
    
    
    public Response addSupportedContentType(String contentType) {
        this.supportedContentTypes.add(contentType);
        return this;
    }
    public boolean supportsContentType(String contentType) {
        return supportedContentTypes.contains(contentType);
    }
    public List<String> supportedContentTypes() {
        return supportedContentTypes;
    }
    
    
    
    /**
     * Empty container for rendering purposes.
     * 
     * <p>
     * It causes the {@link ResponseRunner} to render no body, just the header. Useful
     * when issuing a redirect and no corresponding content should be shown.
     * 
     * @author MTD
     */
    public static class NoHttpBody {
        // intentionally left empty. Just a marker class.
    }
    
    
    /**
     * This method will send the text to a client verbatim. It will not use any layouts. Use it to build app.services
     * and to support AJAX.
     *
     * @param text text of response.
     * @return {@link HttpSupport.HttpBuilder}, to accept additional information.
     */
    public Response text(String text) {
        contentType(MediaType.TEXT_PLAIN).renderable(text);
        return this;
    }
    /**
     * This method will send the text to a client verbatim. It will not use any layouts. Use it to build app.services
     * and to support AJAX.
     * 
     * @param text A string containing &quot;{index}&quot;, like so: &quot;Message: {0}, error: {1}&quot;
     * @param objects A varargs of objects to be put into the <code>text</code>
     * @return {@link HttpSupport.HttpBuilder}, to accept additional information.
     * @see MessageFormat#format
     */
    public Response text(String text, Object...objects) {
        return text(MessageFormat.format(text, objects));
    }
    
    public Response text(byte[] text) {
        contentType(MediaType.TEXT_PLAIN).renderable(text);
        return this;
    }
    public Response text(char[] text) {
//        holder.setControllerResponse(response);
        contentType(MediaType.TEXT_PLAIN).renderable(text);
        return this;
    }
    
    /**
     * This method will send a JSON response to the client.
     * It will not use any layouts.
     * Use it to build app.services and to support AJAX.
     * 
     * @param obj
     * @return {@link Response}, to accept additional information. The response is automatically
     * has its content type set to "application/json"
     */
    public final Response json(Object obj) {
//        holder.setControllerResponse(response);
        contentType(MediaType.APPLICATION_JSON).renderable(obj);
        return this;
    }
    
    /**
     * This method will send a XML response to the client.
     * It will not use any layouts.
     * Use it to build app.services.
     * 
     * @param obj
     * @return {@link Response}, to accept additional information. The response is automatically
     * has its content type set to "application/xml"
     */
    public Response xml(Object obj) {
//        holder.setControllerResponse(response);
        contentType(MediaType.APPLICATION_XML).renderable(obj);
        return this;
    }
    
    /**
     * Convenience method for downloading files. This method will force the browser to find a handler(external program)
     *  for  this file (content type) and will provide a name of file to the browser. This method sets an HTTP header
     * "Content-Disposition" based on a file name.
     *
     * @param file file to download.
     * @return builder instance.
     * @throws PathNotFoundException thrown if file not found.
     */
    public Response sendFile(File file) throws PathNotFoundException {
        try{
              addHeader("Content-Disposition", "attachment; filename=" + file.getName());
              renderable(new FileInputStream(file));
              contentType(MediaType.APPLICATION_OCTET_STREAM);
            return this;
        }catch(Exception e){
            throw new PathNotFoundException(e);
        }
    }
}
