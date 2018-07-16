/**
 * 
 */
package com.n26.challange.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.challange.model.Statistics;
import com.n26.challange.model.Transaction;
import com.n26.challange.service.TransactionService;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
public class ChallageControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private ChallangeController challangeController;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(challangeController).build();
	}

	@Test
	public void testAddTransaction() throws Exception {
		Mockito.doNothing().when(transactionService).addStatistics(Mockito.any(BigDecimal.class), Mockito.anyLong());

		Transaction transaction = createTransaction(1.0, System.currentTimeMillis());

		mockMvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transaction)))
				.andExpect(status().is(HttpServletResponse.SC_CREATED));
	}

	@Test
	public void testAddTransactionOutOfRange() throws Exception {
		Mockito.doNothing().when(transactionService).addStatistics(Mockito.any(BigDecimal.class), Mockito.anyLong());

		Transaction transaction = createTransaction(1.0, System.currentTimeMillis() + 61000);

		mockMvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transaction)))
				.andExpect(status().is(HttpServletResponse.SC_CREATED));
	}

	@Test
	public void testAddTransactionInvalidAmount() throws Exception {
		Transaction transaction = createTransaction(-11.0, System.currentTimeMillis());
		mockMvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transaction))).andExpect(status().is4xxClientError());
	}

	@Test
	public void testAddTransactionNoTransaction() throws Exception {
		Transaction transaction = createTransaction(1.0, 0l);

		mockMvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transaction)))
				.andExpect(status().is(HttpServletResponse.SC_NO_CONTENT));
	}

	@Test
	public void testAddTransactionOldTimestamp() throws Exception {
		Transaction transaction = createTransaction(1.0,
				Instant.ofEpochMilli(System.currentTimeMillis() - 61000).atOffset(ZoneOffset.UTC).toEpochSecond());

		mockMvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(transaction)))
				.andExpect(status().is(HttpServletResponse.SC_NO_CONTENT));
	}

	@Test
	public void testGetStatistics() throws Exception {
		Statistics statistics = Statistics.newBuilder().withCount(5).withSum(20.8).withAvg(5.6).withMin(3.4)
				.withMax(6.7).build();
		Mockito.when(transactionService.getStatistics()).thenReturn(statistics);

		mockMvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.count").value(statistics.getCount()))
				.andExpect(jsonPath("$.avg").value(statistics.getAvg()))
				.andExpect(jsonPath("$.sum").value(statistics.getSum()))
				.andExpect(jsonPath("$.min").value(statistics.getMin()))
				.andExpect(jsonPath("$.max").value(statistics.getMax()));
	}

	private Transaction createTransaction(double amount, long timestamp) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTimestamp(timestamp);

		return transaction;
	}

}
