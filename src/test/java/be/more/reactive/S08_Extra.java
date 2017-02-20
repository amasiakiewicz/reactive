package be.more.reactive;

import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class S08_Extra extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(S08_Extra.class);

    @Test
    public void interval() throws Exception {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(s -> log.debug("Doing some cool processing {}", s.toString()));

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void timeout() throws Exception {
        final Observable<Long> observable = getHeartBeat();
        observable.
                timeout(1, TimeUnit.SECONDS).
                toBlocking().
                forEach(h -> log.debug("Heart beat: {}", h));
    }

}
