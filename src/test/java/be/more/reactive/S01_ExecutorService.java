package be.more.reactive;

import be.more.reactive.util.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class S01_ExecutorService extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(S01_ExecutorService.class);

	@Test
	public void traditionalCall() throws Exception {
		final String result = db.apply(query);     //blocks main thread
		log.debug("Query result: '{}'", result);
	}

	@Test
	public void executorService() throws Exception {
		final Callable<String> task = () -> db.apply(query);
		final Future<String> resultInFuture = executorService.submit(task);

		final String result = resultInFuture.get();   //still blocking
		log.debug("Query result: '{}'", result);
	}

}

