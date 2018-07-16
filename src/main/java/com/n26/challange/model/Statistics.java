/**
 * 
 */
package com.n26.challange.model;

/**
 * Model for Statistics objects
 * 
 * @author resulav
 *
 */
public class Statistics {

	private double sum;
	private double avg;
	private double max;
	private double min;
	private long count;

	private Statistics(Builder builder) {
		setSum(builder.sum);
		setAvg(builder.avg);
		setMax(builder.max);
		setMin(builder.min);
		setCount(builder.count);
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
	 * @param sum the sum to set
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * @return the avg
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @param avg the avg to set
	 */
	public void setAvg(double avg) {
		this.avg = avg;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
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
		 * Sets the {@code sum} and returns a reference to this Builder so that the methods can be chained together.
		 *
		 * @param sum the {@code sum} to set
		 * @return a reference to this Builder
		 */
		public Builder withSum(double sum) {
			this.sum = sum;
			return this;
		}

		/**
		 * Sets the {@code avg} and returns a reference to this Builder so that the methods can be chained together.
		 *
		 * @param avg the {@code avg} to set
		 * @return a reference to this Builder
		 */
		public Builder withAvg(double avg) {
			this.avg = avg;
			return this;
		}

		/**
		 * Sets the {@code max} and returns a reference to this Builder so that the methods can be chained together.
		 *
		 * @param max the {@code max} to set
		 * @return a reference to this Builder
		 */
		public Builder withMax(double max) {
			this.max = max;
			return this;
		}

		/**
		 * Sets the {@code min} and returns a reference to this Builder so that the methods can be chained together.
		 *
		 * @param min the {@code min} to set
		 * @return a reference to this Builder
		 */
		public Builder withMin(double min) {
			this.min = min;
			return this;
		}

		/**
		 * Sets the {@code count} and returns a reference to this Builder so that the methods can be chained together.
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
		 * @return a {@code Statistics} built with parameters of this {@code Statistics.Builder}
		 */
		public Statistics build() {
			return new Statistics(this);
		}
	}
}
