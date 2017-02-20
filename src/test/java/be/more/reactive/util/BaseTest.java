package be.more.reactive.util;

import be.more.reactive.db.DB;
import be.more.reactive.db.Query;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseTest {

	private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

	protected final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory());
	protected final Scheduler scheduler = Schedulers.from(executorService);
	protected final DB db = new DB();
	protected final Query query = new Query("1");

	@Rule
	public TestName testName = new TestName();

	private ThreadFactory threadFactory() {
		return new ThreadFactoryBuilder().setNameFormat("CircleK-pool-%d").build();
	}

	@Before
	public void logTestStart() {
		log.debug("Starting: {}", testName.getMethodName());
	}

	@After
	public void stopPool() throws InterruptedException {
		executorService.shutdownNow();
	}

	protected List<Query> getQueries() {
		return ImmutableList.of(new Query("1"), new Query("2"), new Query("3"), new Query("4"), new Query("5"), new Query("6"));
	}

	protected Observable<Long> getSmallNumbersEmittedAsync() {
		return Observable
				.interval(100, TimeUnit.MILLISECONDS)
				.map(i -> i + 1)
				.take(4);
	}

	protected Observable<Long> getBiggerNumbersEmittedAsync() {
		return Observable
				.interval(100, TimeUnit.MILLISECONDS)
				.map(n -> n * 100)
				.take(5);
	}

	protected Integer throwExcInsteadOf3() {
		throw new RuntimeException("Should be 3");
	}

	protected Observable<Long> getHeartBeat() {
		AtomicInteger i = new AtomicInteger(0);
		return Observable.interval(1, TimeUnit.MILLISECONDS)
				.map(l -> {
					try {
						TimeUnit.MILLISECONDS.sleep(i.addAndGet(1) * 100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return l;
				});
	}

	protected CompletableFuture<String> getFutureQueryResult(final String queryId) {
		return CompletableFuture.supplyAsync(
				() -> db.apply(new Query(queryId)),
				executorService
		);
	}

}
