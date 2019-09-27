package com.lexalytics.semantria.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.lexalytics.semantria.client.OptionHelper.hasAllOptions;

public class Dispatcher<T> {
    private List<Route> routes = new ArrayList<>();

    public void add(Consumer<T> destination, String... options) {
        Route route = new Route(destination, options);
        routes.add(route);
    }

    public void dispatch(Map<String, Object> opts, T t) {
        for (Route route : routes) {
            boolean match = hasAllOptions(opts, route.options);
            if (match) {
                route.destination.accept(t);
                return;
            }
        }
        throw new DispatcherException();
    }

    public static class DispatcherException extends RuntimeException {
    }

    private class Route {
        Consumer<T> destination;
        String[] options;

        public Route(Consumer<T> destination, String... options) {
            this.destination = destination;
            this.options = options;
        }
    }
}
