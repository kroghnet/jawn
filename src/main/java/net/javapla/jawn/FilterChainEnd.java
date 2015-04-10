package net.javapla.jawn;

import com.google.inject.Inject;

/**
 * Indicates the end of a filter chain.
 * This should always be the last filter to be called.
 * 
 * This is also the caller of the controller action.
 * 
 * @author MTD
 */
class FilterChainEnd implements FilterChain {

    private final ControllerActionInvoker invoker;
    
    @Inject
    public FilterChainEnd(ControllerActionInvoker invoker) {
        this.invoker = invoker;
    }
    
    @Override
    public ControllerResponse before(Context context) {
        return invoker.executeAction(context);
    }

    @Override
    public void after(Context context) {}
    
    @Override
    public void onException(Exception e) {}
}
