package net.javapla.jawn.core.api;

import net.javapla.jawn.core.Response;
import net.javapla.jawn.core.http.Context;


public interface Filter {

    /**
     * Filter the request. Filters should invoke the {@link FilterChain#before(Context)}
     * method if they wish the request to proceed.
     * 
     * @param chain
     *      The filter chain
     * @param context
     *      The context
     * @return
     *      A response if anything needs to be redirected or 404'd
     */
    Response before(FilterChain chain, Context context);
    
    /**
     * Called by framework after executing a controller
     */
    void after(FilterChain chain, Context context);
    
    /**
     * Called by framework in case there was an exception inside a controller
     *
     * @param e exception.
     */
    void onException(FilterChain chain, Exception e);
}