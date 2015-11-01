package net.javapla.jawn.core.test;

import net.javapla.jawn.core.Response;
import net.javapla.jawn.core.http.Context;
import net.javapla.jawn.core.http.Request;

public class LambdaInterfaceTryout {

    public void get(SingleHandler<Context> h) {
        
    }
    public void get(DoubleHandler<Context, Request> c) {
        
    }
    
    private Response handle(Context c ) {
        return null;
    }
    private Response handleDos(Context c, Request r) {
        return null;
    }
    
    public void something() {
        get(this::handle);
        get((c) -> {
            return null;
        });
        
        get(this::handleDos);
        get((c,r) -> {
            return null;
        });
    }
    
    public static void main(String[] args) {
        
    }
    
    public interface SingleHandler<T> {
        Response handle(T t);
    }
    public interface DoubleHandler<T,U> {
        Response handle(T t, U u);
    }
}
