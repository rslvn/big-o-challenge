/**
 * 
 */
package com.n26.challange.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.n26.challange.model.Statistics;

/**
 * @author resulav
 *
 */
public class StatisticsUtilTest {

	@Test
	public void testConstructor() {
		StatisticsUtil util = new StatisticsUtil();
		Assert.assertNotNull("util can not be null", util);
	}

	@Test
	public void testCloneStatistics() {
		Statistics statistics = Statistics.newBuilder().withCount(2).build();
		Statistics clone = StatisticsUtil.cloneStatistics(statistics);
		Assert.assertEquals("Count not matched", statistics.getCount(), clone.getCount());
		Assert.assertEquals(statistics.getSum(), clone.getSum(), 0.0);
		Assert.assertEquals(statistics.getAvg(), clone.getAvg(), 0.0);
		Assert.assertEquals(statistics.getMin(), clone.getMin(), 0.0);
		Assert.assertEquals(statistics.getMax(), clone.getMax(), 0.0);
	}

	@Test
	public void testAddStatisticsFirst() {
		Statistics statistics = Statistics.newBuilder().build();
		double amount = 1.2;
		StatisticsUtil.addStatistics(statistics, BigDecimal.valueOf(amount));

		Assert.assertEquals("Count not matched", statistics.getCount(), 1);
		Assert.assertEquals(statistics.getSum(), amount, 0.0);
		Assert.assertEquals(statistics.getAvg(), amount, 0.0);
		Assert.assertEquals(statistics.getMin(), amount, 0.0);
		Assert.assertEquals(statistics.getMax(), amount, 0.0);
	}

	@Test
	public void testAddStatistics() {
		Statistics statistics = Statistics.newBuilder().withCount(2).withSum(2.4).withAvg(1.2).withMin(1.1).withMax(1.3)
				.build();
		double amount = 1.2;
		StatisticsUtil.addStatistics(statistics, BigDecimal.valueOf(amount));

		Assert.assertEquals("Count not matched", statistics.getCount(), 3);
		Assert.assertEquals(statistics.getSum(), 3.6, 0.0);
		Assert.assertEquals(statistics.getAvg(), 1.2, 0.0);
		Assert.assertEquals(statistics.getMin(), 1.1, 0.0);
		Assert.assertEquals(statistics.getMax(), 1.3, 0.0);
	}

	@Test
	public void testAddStatisticsFrom() {
		Statistics to = Statistics.newBuilder().build();
		Statistics from = Statistics.newBuilder().withCount(2).withSum(2.4).withAvg(1.2).withMin(1.1).withMax(1.3)
				.build();
		StatisticsUtil.addStatistics(to, from);

		Assert.assertEquals("Count not matched", to.getCount(), from.getCount());
		Assert.assertEquals(to.getSum(), from.getSum(), 0.0);
		Assert.assertEquals(to.getAvg(), from.getAvg(), 0.0);
		Assert.assertEquals(to.getMin(), from.getMin(), 0.0);
		Assert.assertEquals(to.getMax(), from.getMax(), 0.0);
	}

	@Test
	public void testAddStatisticsFrom2() {
		Statistics to = Statistics.newBuilder().withCount(3).withSum(3.6).withAvg(1.2).withMin(1.0).withMax(1.4)
				.build();
		Statistics from = Statistics.newBuilder().withCount(2).withSum(2.4).withAvg(1.2).withMin(1.1).withMax(1.3)
				.build();
		StatisticsUtil.addStatistics(to, from);

		Assert.assertEquals("Count not matched", to.getCount(), 5);
		Assert.assertEquals(to.getSum(), 6.0, 0.0);
		Assert.assertEquals(to.getAvg(), 1.2, 0.0);
		Assert.assertEquals(to.getMin(), 1.0, 0.0);
		Assert.assertEquals(to.getMax(), 1.4, 0.0);
	}

	@Test
	public void testRemove() {
		Statistics statistics = Statistics.newBuilder().withCount(5).withSum(6.0).withAvg(1.2).withMin(0.8).withMax(1.8)
				.build();
		Statistics clone = Statistics.newBuilder().withCount(2).withSum(2.7).withAvg(1.35).withMin(0.9).withMax(1.8)
				.build();

		StatisticsUtil.remove(statistics, clone, Arrays.asList(0.8, 1.8));

		Assert.assertEquals("Count not matched", statistics.getCount(), 3);
		Assert.assertEquals(statistics.getSum(), 3.3, 0.0);
		Assert.assertEquals(statistics.getAvg(), 1.1, 0.0);
		Assert.assertEquals(statistics.getMin(), 0.8, 0.0);
		Assert.assertEquals(statistics.getMax(), 1.8, 0.0);
	}

	@Test
	public void testRemoveZero() {
		Statistics statistics = Statistics.newBuilder().withCount(1).withSum(1.2).withAvg(1.2).withMin(1.2).withMax(1.2)
				.build();
		Statistics clone = StatisticsUtil.cloneStatistics(statistics);

		StatisticsUtil.remove(statistics, clone, new ArrayList<>());

		Assert.assertEquals("Count not matched", statistics.getCount(), 0);
		Assert.assertEquals(statistics.getSum(), 0.0, 0.0);
		Assert.assertEquals(statistics.getAvg(), 0.0, 0.0);
		Assert.assertEquals(statistics.getMin(), 0.0, 0.0);
		Assert.assertEquals(statistics.getMax(), 0.0, 0.0);
	}

	@Test
	public void testRemoveOne() {
		Statistics statistics = Statistics.newBuilder().withCount(2).withSum(3.0).withAvg(1.5).withMin(1.2).withMax(1.8)
				.build();
		Statistics clone = Statistics.newBuilder().withCount(1).withSum(1.2).withAvg(1.2).withMin(1.2).withMax(1.2)
				.build();

		StatisticsUtil.remove(statistics, clone, new ArrayList<>());

		Assert.assertEquals("Count not matched", statistics.getCount(), 1);
		Assert.assertEquals(statistics.getSum(), 1.8, 0.0);
		Assert.assertEquals(statistics.getAvg(), 1.8, 0.0);
		Assert.assertEquals(statistics.getMin(), 1.8, 0.0);
		Assert.assertEquals(statistics.getMax(), 1.8, 0.0);
	}
}
