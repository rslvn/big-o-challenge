/**
 *
 */
package com.n26.challange.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

import com.n26.challange.ChallangeAppConstant;
import com.n26.challange.model.Statistics;

/**
 * @author resulav
 *
 */
public class StatisticsUtil {

	protected StatisticsUtil() {
		// for sonar
	}

	/**
	 * clones a Statistics object
	 *
	 * @param statistics object to clone
	 * @return a new Statistics object
	 */
	public static Statistics cloneStatistics(Statistics statistics) {
		return Statistics.newBuilder().withAvg(statistics.getAvg()).withCount(statistics.getCount())
				.withMax(statistics.getMax()).withMin(statistics.getMin()).withSum(statistics.getSum()).build();
	}

	/**
	 * adds the amount to a Statistics object
	 *
	 * @param to     object to add
	 * @param amount the new amount
	 */
	public static void addStatistics(Statistics to, BigDecimal amount) {
		to.setCount(to.getCount() + 1);
		double dAmount = amount.doubleValue();
		if (to.getCount() == 1) {
			to.setSum(dAmount);
			to.setAvg(dAmount);
			to.setMin(dAmount);
			to.setMax(dAmount);
		} else {
			BigDecimal sum = BigDecimal.valueOf(to.getSum()).add(amount).setScale(ChallangeAppConstant.SCALE,
					RoundingMode.HALF_UP);
			to.setSum(sum.doubleValue());
			to.setAvg(sum.divide(BigDecimal.valueOf(to.getCount()), RoundingMode.HALF_UP).doubleValue());
			to.setMin(Math.min(to.getMin(), dAmount));
			to.setMax(Math.max(to.getMax(), dAmount));
		}
	}

	/**
	 * adds the amount to a Statistics object
	 *
	 * @param to     object to add
	 * @param amount the new amount
	 */
	public static void addStatistics(Statistics to, Statistics from) {
		to.setCount(to.getCount() + from.getCount());
		if (to.getCount() == from.getCount()) {
			to.setSum(from.getSum());
			to.setAvg(from.getAvg());
			to.setMin(from.getMin());
			to.setMax(from.getMax());
		} else {
			BigDecimal sum = BigDecimal.valueOf(to.getSum()).add(BigDecimal.valueOf(from.getSum()))
					.setScale(ChallangeAppConstant.SCALE, RoundingMode.HALF_UP);
			to.setSum(sum.doubleValue());
			to.setAvg(sum.divide(BigDecimal.valueOf(to.getCount()), RoundingMode.HALF_UP).doubleValue());
			to.setMin(Math.min(to.getMin(), from.getMin()));
			to.setMax(Math.max(to.getMax(), from.getMax()));
		}
	}

	/**
	 * removes expired statistics from total statistics
	 *
	 * @param from             object to remove
	 * @param to               removing object
	 * @param maxMinAmountList min and max amount values of all seconds
	 */
	public static void remove(Statistics from, Statistics to, List<Double> maxMinAmountList) {
		BigDecimal sum = BigDecimal.valueOf(from.getSum()).subtract(BigDecimal.valueOf(to.getSum()))
				.setScale(ChallangeAppConstant.SCALE, RoundingMode.HALF_UP);
		from.setCount(from.getCount() - to.getCount());
		from.setSum(sum.doubleValue());

		if (from.getCount() == 1) {
			from.setAvg(from.getSum());
			from.setMin(from.getSum());
			from.setMax(from.getSum());
		} else if (from.getCount() > 0) {
			from.setAvg(
					sum.divide(BigDecimal.valueOf(from.getCount()), ChallangeAppConstant.SCALE, RoundingMode.HALF_UP)
							.doubleValue());
			Supplier<DoubleStream> doubleStreamSupplier = () -> maxMinAmountList.parallelStream().mapToDouble(d -> d);
			from.setMin(doubleStreamSupplier.get().min().orElse(0.0));
			from.setMax(doubleStreamSupplier.get().max().orElse(0.0));
		} else {
			from.setAvg(0.0);
			from.setMin(0.0);
			from.setMax(0.0);
		}
	}
}
