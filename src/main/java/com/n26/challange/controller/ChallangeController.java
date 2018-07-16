/**
 * 
 */
package com.n26.challange.controller;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

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

	/**
	 * adding transaction API
	 * 
	 * @param transaction new Transaction object
	 * @param response    HTTP response object
	 */
	@RequestMapping(value = PATH_TRANSACTION, method = RequestMethod.POST)
	public void transaction(@RequestBody Transaction transaction, HttpServletResponse response) {
		if (transaction == null || transaction.getAmount() < 0.0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		long epochTime = Instant.now(Clock.systemUTC()).getEpochSecond();
		long timestamp = Instant.ofEpochMilli(transaction.getTimestamp()).atOffset(ZoneOffset.UTC).toEpochSecond();
		long diff = epochTime - timestamp;

		LOG.info("current epoch: {}, epoch: {}, diff: {}, amount: {}", epochTime, timestamp, diff,
				transaction.getAmount());
		// timestamp is older than 1 minute
		if (diff > ChallangeAppConstant.TRANSACTION_EXPIRED_DURATION) {
			LOG.info("Invalid timestamp - currentEpochTime: {}, timestamp: {}, diff: {}", epochTime, timestamp, diff);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}

		// timestamp is greater than 1 minute,
		if (epochTime < timestamp) {
			LOG.info("outOfRange timestamp - currentEpochTime: {}, timestamp: {}, diff: {}", epochTime, timestamp, diff);
			transactionService.addOutOfRangeStatistics(BigDecimal.valueOf(transaction.getAmount()), timestamp);
		} else {
			transactionService.addStatistics(BigDecimal.valueOf(transaction.getAmount()), timestamp);
		}

		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	/**
	 * query statistics for last 60 seconds
	 * 
	 * @param response response HTTP response object
	 * @return
	 */
	@RequestMapping(value = PATH_STATISTICS, method = RequestMethod.GET)
	public Statistics statistics(HttpServletResponse response) {
		return transactionService.getStatistics();
	}
}
