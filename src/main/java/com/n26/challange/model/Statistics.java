/**
 * 
 */
package com.n26.challange.model;

/**
 * @author resulav
 *
 */
public class Statistics {
	
	private final double sum;
	private final double avg;
	private final double max;
	private final double min;
	private final long count;

	private Statistics(Builder builder) {
		sum = builder.sum;
		avg = builder.avg;
		max = builder.max;
		min = builder.min;
		count = builder.count;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * @return the avg
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * {@code Statistics} builder static inner class.
	 */
	public static final class Builder {
		private double sum;
		private double avg;
		private double max;
		private double min;
		private long count;

		private Builder() {
		}

		/**
		 * Sets the {@code sum} and returns a reference to this Builder so that the
		 * methods can be chained together.
		 *
		 * @param sum the {@code sum} to set
		 * @return a reference to this Builder
		 */
		public Builder withSum(double sum) {
			this.sum = sum;
			return this;
		}

		/**
		 * Sets the {@code avg} and returns a reference to this Builder so that the
		 * methods can be chained together.
		 *
		 * @param avg the {@code avg} to set
		 * @return a reference to this Builder
		 */
		public Builder withAvg(double avg) {
			this.avg = avg;
			return this;
		}

		/**
		 * Sets the {@code max} and returns a reference to this Builder so that the
		 * methods can be chained together.
		 *
		 * @param max the {@code max} to set
		 * @return a reference to this Builder
		 */
		public Builder withMax(double max) {
			this.max = max;
			return this;
		}

		/**
		 * Sets the {@code min} and returns a reference to this Builder so that the
		 * methods can be chained together.
		 *
		 * @param min the {@code min} to set
		 * @return a reference to this Builder
		 */
		public Builder withMin(double min) {
			this.min = min;
			return this;
		}

		/**
		 * Sets the {@code count} and returns a reference to this Builder so that the
		 * methods can be chained together.
		 *
		 * @param count the {@code count} to set
		 * @return a reference to this Builder
		 */
		public Builder withCount(long count) {
			this.count = count;
			return this;
		}

		/**
		 * Returns a {@code Statistics} built from the parameters previously set.
		 *
		 * @return a {@code Statistics} built with parameters of this
		 *         {@code Statistics.Builder}
		 */
		public Statistics build() {
			return new Statistics(this);
		}
	}
}
