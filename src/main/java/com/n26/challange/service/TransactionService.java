/**
 * 
 */
package com.n26.challange.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.challange.ChallangeAppConstant;
import com.n26.challange.model.Statistics;
import com.n26.challange.model.Transaction;

/**
 * @author resulav
 *
 */
@Service
public class TransactionService {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
	private static final Map<Long, List<Transaction>> transactionMap = new ConcurrentHashMap<>();

	/**
	 * @param transaction
	 */
	public void addTransaction(Transaction transaction) {
		transactionMap.computeIfAbsent(transaction.getTimestamp(), key -> new ArrayList<>()).add(transaction);
		LOG.info("Size of cache: {}", transactionMap.size());
	}

	/**
	 * @return
	 */
	public Statistics getStatistics() {
		long before30seconds = System.currentTimeMillis() - ChallangeAppConstant.TRANSACTION_EXPIRED_DURATION;
		LOG.info("Size: {}, ts: {}", transactionMap.size(), before30seconds);
		Map<Long, List<Transaction>> tmpMap = new HashMap<>(transactionMap);

		List<Double> amountList = tmpMap.entrySet().stream().filter(e -> e.getKey().longValue() >= before30seconds)
				.map(e -> e.getValue()).flatMapToDouble(l -> l.stream().mapToDouble(t -> t.getAmount())).boxed()
				.collect(Collectors.toList());

		LOG.info("amounts: {}", amountList.size());

		Supplier<DoubleStream> doubleStreamSupplier = () -> amountList.parallelStream().mapToDouble(d -> d);

		return Statistics.newBuilder().withAvg(doubleStreamSupplier.get().average().orElse(0.0))
				.withCount(amountList.size()).withMax(doubleStreamSupplier.get().max().orElse(0.0))
				.withMin(doubleStreamSupplier.get().min().orElse(0.0)).withSum(doubleStreamSupplier.get().sum())
				.build();
	}

	/**
	 * 
	 */
	@Scheduled(fixedRate = 5000)
	private void removeOlds() {
		long before30seconds = System.currentTimeMillis() - ChallangeAppConstant.TRANSACTION_EXPIRED_DURATION;
		new HashMap<>(transactionMap).entrySet().stream().filter(e -> e.getKey().longValue() < before30seconds)
				.forEach(e -> transactionMap.remove(e.getKey()));
	}

}
