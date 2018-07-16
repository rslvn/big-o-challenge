/**
 * 
 */
package com.n26.challange.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challange.model.Statistics;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@Mock
	private ScheduledExecutorService scheduledExecutor;

	@InjectMocks
	private TransactionService transactionService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddStatistics() throws InterruptedException {
		Statistics oldStat = transactionService.getStatistics();

		BigDecimal amount = BigDecimal.valueOf(1.2);
		transactionService.addStatistics(amount, Instant.now(Clock.systemUTC()).getEpochSecond());

		Statistics newStat = transactionService.getStatistics();
		Assert.assertEquals("Count not matched", oldStat.getCount() + 1, newStat.getCount());
	}

	@Test
	public void testOutOfRangeStatistics() throws InterruptedException {
		Statistics oldStat = transactionService.getStatistics();

		BigDecimal amount = BigDecimal.valueOf(1.2);
		transactionService.addOutOfRangeStatistics(amount, Instant.now(Clock.systemUTC()).getEpochSecond() + 1);

		Statistics newStat = transactionService.getStatistics();
		Assert.assertEquals("Count not matched", oldStat.getCount(), newStat.getCount());
	}
}
