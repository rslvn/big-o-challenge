/**
 * 
 */
package com.n26.challange.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.challange.ChallangeAppConstant;
import com.n26.challange.model.Statistics;
import com.n26.challange.util.StatisticsUtil;

/**
 * @author resulav
 *
 */
@Service
public class TransactionService {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

	private static final Map<Long, Statistics> statisticsMap = new ConcurrentHashMap<>();
	private static final Map<Long, Statistics> outOfRangeStatisticsMap = new ConcurrentHashMap<>();
	private static Statistics latest = Statistics.newBuilder().build();

	/**
	 * adds new transactions
	 * 
	 * @param amount    transaction amount
	 * @param timestamp transaction timestamp as UTC epoch seconds
	 */
	public void addStatistics(BigDecimal amount, long timestamp) {
		StatisticsUtil.addStatistics(latest, amount);
		Statistics statistics = statisticsMap.computeIfAbsent(timestamp,
				key -> Statistics.newBuilder().build());
		StatisticsUtil.addStatistics(statistics, amount);
	}

	/**
	 * stores transactions whose timestamp bigger than curent timestamp
	 * 
	 * @param amount    amount transaction amount
	 * @param timestamp transaction timestamp as UTC epoch seconds
	 */
	public void addOutOfRangeStatistics(BigDecimal amount, long timestamp) {
		Statistics statistics = outOfRangeStatisticsMap.computeIfAbsent(timestamp,
				key -> Statistics.newBuilder().build());
		StatisticsUtil.addStatistics(statistics, amount);
	}

	/**
	 * returns current statistics
	 * 
	 * @return
	 */
	public Statistics getStatistics() {
		return StatisticsUtil.cloneStatistics(latest);
	}

	/**
	 * The periodic job to remove old transaction records
	 * 
	 */
	@Scheduled(cron = "* * * * * ?")
	private void removeOldRecords() {
		long currentEpoch = Instant.now(Clock.systemUTC()).getEpochSecond();

		// add current time statistics If exist in out of range map
		Statistics uncalculated = outOfRangeStatisticsMap.remove(currentEpoch);
		if (uncalculated != null) {
			StatisticsUtil.addStatistics(latest, uncalculated);
			statisticsMap.put(currentEpoch, uncalculated);
		}

		long before60seconds = currentEpoch - ChallangeAppConstant.TRANSACTION_EXPIRED_DURATION;
		Statistics statistics = statisticsMap.remove(before60seconds);
		if (statistics == null) {
			return;
		}

		LOG.info("Triggerred at {} for {}", currentEpoch, before60seconds);

		List<Double> maxMinAmountList = statisticsMap.entrySet().parallelStream().map(e -> e.getValue())
				.flatMapToDouble(s -> Arrays.asList(s.getMin(), s.getMax()).parallelStream().mapToDouble(t -> t))
				.boxed().collect(Collectors.toList());

		StatisticsUtil.remove(latest, statistics, maxMinAmountList);

		LOG.info("Size: {}, count: {}, avg: {}, sum: {}, max: {}, min: {}", statisticsMap.size(), latest.getCount(),
				latest.getAvg(), latest.getSum(), latest.getMax(), latest.getMin());
	}
}
