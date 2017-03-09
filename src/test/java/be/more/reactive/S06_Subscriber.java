package be.more.reactive;

import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;

public class S06_Subscriber extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(S06_Subscriber.class);

    @Test
    public void subscriber() {
        Observable.just(1, 2, 3, 4)
                .subscribe(i -> log.debug(i.toString()));
    }

    @Test
    public void fluentSubscriber() {
        Observable.just(1, 2, 3, 4)
                .doOnNext(result -> log.debug("Got result {}", result))
                .subscribe();
    }

    @Test
    public void toList() {
        Observable.just(1, 2, 3, 4)
                .doOnNext((Integer result) -> log.debug("Got result {}", result))
                .toList()
                .subscribe((List<Integer> results) -> log.debug("Results are {}", results));

    }
}
