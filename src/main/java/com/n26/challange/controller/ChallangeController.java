/**
 * 
 */
package com.n26.challange.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challange.ChallangeAppConstant;
import com.n26.challange.model.Statistics;
import com.n26.challange.model.Transaction;
import com.n26.challange.service.TransactionService;

/**
 * @author resulav
 *
 */
@RestController
public class ChallangeController {

	private static final Logger LOG = LoggerFactory.getLogger(ChallangeController.class);

	public static final String PATH_TRANSACTION = "/transactions";
	public static final String PATH_STATISTICS = "/statistics";
	 

	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = PATH_TRANSACTION, method = RequestMethod.POST)
	public void transaction(@RequestBody Transaction transaction, HttpServletResponse response) {
		if (transaction == null || transaction.getAmount() < 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		if (notValidTimeStamp(transaction.getTimestamp())) {
			LOG.info("currentTimeStamp: {}, timestamp: {}, amount: {}", System.currentTimeMillis(),
					transaction.getTimestamp(), transaction.getAmount());
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}

		transactionService.addTransaction(transaction);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	@RequestMapping(value = PATH_STATISTICS, method = RequestMethod.GET)
	public Statistics statistics(HttpServletResponse response) {
		return transactionService.getStatistics();
	}

	private boolean notValidTimeStamp(long timestamp) {
		return System.currentTimeMillis() > timestamp + ChallangeAppConstant.TRANSACTION_EXPIRED_DURATION;
	}

}
