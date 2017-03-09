package be.more.reactive;

import be.more.reactive.db.Query;
import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.naturalOrder;

public class S07_RealLife extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(S07_RealLife.class);

    @Test
    public void findByQueryTraditional() {
        final List<Query> queries = getQueries();  //there're 6 db queries

        final List<String> results = new ArrayList<>();

        queries.forEach(query -> {
                    log.debug("Firing query {}", query.getId());
                    final String result = db.apply(query);

                    log.debug("Got result {}", result);
                    results.add(result);
                });

        log.debug("Got all results, sorting them");
        results.sort(naturalOrder());

        log.debug("Results are {}", results);
    }

    @Test
    public void findByQueryAsync() {
        final List<Query> queries = getQueries();

        final Observable<Query> queryObservable = Observable.from(queries);

        final Observable<String> resultObservable = queryObservable.flatMap(query ->
                Observable.fromCallable(() -> {
                    log.debug("Firing query {}", query.getId());
                    return db.apply(query);
                }).subscribeOn(scheduler)
        );

        resultObservable
                .doOnNext(result -> log.debug("Got result {}", result))
                .doOnError(t -> {throw new RuntimeException(t);})
                .toList()
                .toBlocking()
                .subscribe(results -> {
                    log.debug("Got all results, sorting them");
                    results.sort(naturalOrder());
                    log.debug("Results are {}", results);
                });
    }

}
