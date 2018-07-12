/**
 * 
 */
package com.n26.challange.model;

/**
 * @author resulav
 *
 */
public class Transaction {
	private double amount;
	private long timestamp;

	/**
	 * 
	 */
	public Transaction() {
		super();
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
