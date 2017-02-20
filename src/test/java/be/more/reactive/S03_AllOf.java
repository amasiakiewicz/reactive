package be.more.reactive;

import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class S03_AllOf extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(S03_AllOf.class);

    private final CompletableFuture<String> futureResult1 = getFutureQueryResult("1"); //.exceptionally() ??
    private final CompletableFuture<String> futureResult2 = getFutureQueryResult("2");
    private final CompletableFuture<String> futureResult3 = getFutureQueryResult("3");
    private final CompletableFuture<String> futureResult4 = getFutureQueryResult("4");

    @Test
    public void allOf() throws Exception {
        final CompletableFuture<Void> futureResult = CompletableFuture.allOf(   //Void ?? I want List<String>
                futureResult1, futureResult2, futureResult3, futureResult4
        );

//        futureResult.thenAccept((Void vd) -> vd.??)   //no, it won't work

        futureResult.thenRun(() -> {
            try {
                log.debug("Query result 1: '{}'", futureResult1.get());
                log.debug("Query result 2: '{}'", futureResult2.get());
                log.debug("Query result 3: '{}'", futureResult3.get());
                log.debug("Query result 4: '{}'", futureResult4.get());   //a lot of manual work

                log.debug("Now do on complete");    //handling onComplete
            } catch (Exception e) {
                log.error("", e);
            }
        });

        TimeUnit.SECONDS.sleep(4);

    }

}

