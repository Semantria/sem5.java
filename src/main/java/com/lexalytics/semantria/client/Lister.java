package com.lexalytics.semantria.client;

import com.lexalytics.semantria.client.dto.Paged;

import java.util.function.Consumer;

public class Lister<T> {

    public interface PagedFunction<T> {
        Paged<T> apply(int pageNumber);
    }

    public void go(PagedFunction<T> pagedFunction,
                   Consumer<T> consumer) {
        int pageNumber = 0;
        while(true) {
            Paged<T> results = pagedFunction.apply(pageNumber);
            if (results == null
                || results.getTotalElements() == 0
                || results.getContent() == null
            ) {
                break;
            }
            results.getContent().forEach(consumer);
            if (results.getCurrentPage()+1 == results.getTotalPages()) {
                break;
            }
            pageNumber++;
        }
    }
}
