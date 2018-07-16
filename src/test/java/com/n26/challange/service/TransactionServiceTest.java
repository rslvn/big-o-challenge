/**
 * 
 */
package com.n26.challange.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	public void testExecutor() throws InterruptedException {

	}
}
