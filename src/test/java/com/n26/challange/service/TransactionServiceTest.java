/**
 * 
 */
package com.n26.challange.service;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challange.model.Transaction;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void testRemoveOlds() throws InterruptedException {
		Transaction transaction = new Transaction();
		transaction.setAmount(2.1);
		transaction.setTimestamp(System.currentTimeMillis() - 26000);

		//
		transactionService.addTransaction(transaction);
		System.out.println(transactionService.getStatistics().getCount());

		TimeUnit.SECONDS.sleep(5);
		System.out.println(transactionService.getStatistics().getCount());
		TimeUnit.SECONDS.sleep(5);
	}

}
