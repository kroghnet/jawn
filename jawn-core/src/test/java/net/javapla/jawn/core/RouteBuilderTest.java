package net.javapla.jawn.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import app.controllers.UnitTestController;
import net.javapla.jawn.core.exceptions.RouteException;
import net.javapla.jawn.core.reflection.ActionInvoker;
import net.javapla.jawn.core.routes.Route;
import net.javapla.jawn.core.routes.RouteBuilder;

public class RouteBuilderTest {


    @Test(expected=RouteException.class)
    public void should_throw_because_action_not_found() {
        RouteBuilder.get().route("/").to(UnitTestController.class, "getNothing").build(new FiltersHandler(), mock(ActionInvoker.class));
    }
    
    @Test
    public void should_return_route() {
        Route route = RouteBuilder.get().to(UnitTestController.class, "getSimple").route("/different/path").build(new FiltersHandler(), mock(ActionInvoker.class));
        
        assertNotNull(route);
        assertEquals("getSimple", route.getAction());
    }
    
    @Test
    public void should_mapToUnderscoreActionName() {
        Route route = RouteBuilder.get().to(UnitTestController.class, "getLongerAction").route("/different/path").build(new FiltersHandler(), mock(ActionInvoker.class));
        
        assertNotNull(route);
        assertEquals("getLongerAction", route.getAction());
        assertEquals("longer_action", route.getActionName());
        
        route = RouteBuilder.get().to(UnitTestController.class, "getSimpleTestAction").route("/different/path").build(new FiltersHandler(), mock(ActionInvoker.class));
        assertEquals("getSimpleTestAction", route.getAction());
        assertEquals("simple_test_action", route.getActionName());
    }

    @Test(expected=RouteException.class)
    public void strippingTheWrongHttpMethod_should_resultInFailure() {
        RouteBuilder.post().to(UnitTestController.class, "getLongerAction").build(new FiltersHandler(), mock(ActionInvoker.class));
    }
}
