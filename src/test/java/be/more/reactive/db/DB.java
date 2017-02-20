package be.more.reactive.db;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

public class DB {
    public String apply(Query query) {
        try {
            TimeUnit.SECONDS.sleep(nextLong(1, 3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("%s_%s", randomAlphabetic(nextInt(4, 12)), query.getId());
    }
}
