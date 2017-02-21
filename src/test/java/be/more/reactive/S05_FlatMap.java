package be.more.reactive;

import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class S05_FlatMap extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(S05_FlatMap.class);

    @Test
    public void flatMap() throws Exception {
        final Observable<Integer> smallNumbers = Observable.just(1, 2, 3, 4);

        smallNumbers
                .flatMap(small -> Observable.just(small * 100, small * -100))
                .subscribe(big -> log.debug(big.toString()));
    }

}
