/**
 * 
 */
package com.n26.challange.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.challange.model.Transaction;

/**
 * @author resulav
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChallageControllerIT {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;

	/** Test APIs in a flow
	 * @throws Exception
	 */
	@Test
	public void testAddTransaction() throws Exception {
		
		long delayToBeginningOfNextSecond = 1100 - System.currentTimeMillis() % 1000;
		TimeUnit.MILLISECONDS.sleep(delayToBeginningOfNextSecond);

        long timestamp = System.currentTimeMillis() - 59000;

        Transaction transaction = createTransaction(1.2, timestamp);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.sum").value(1.2))
                .andExpect(jsonPath("$.avg").value(1.2))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.2));
        
        transaction = createTransaction(1.3, timestamp + 1000);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.sum").value(2.5))
                .andExpect(jsonPath("$.avg").value(1.25))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.3));
       
        transaction = createTransaction(1.4, timestamp + 2000);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics - count, sum, avg normal effect - max changed
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.sum").value(3.9))
                .andExpect(jsonPath("$.avg").value(1.3))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.4));
        
        // 2 transaction in a second
        transaction = createTransaction(1.5, timestamp + 3000);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- count, sum, avg normal effect - max changed
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(4))
        .andExpect(jsonPath("$.sum").value(5.4))
        .andExpect(jsonPath("$.avg").value(1.35))
        .andExpect(jsonPath("$.min").value(1.2))
        .andExpect(jsonPath("$.max").value(1.5));
        
        
        transaction = createTransaction(1.6, timestamp + 3000);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- count, sum, avg normal effect - max changed
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(5))
        .andExpect(jsonPath("$.sum").value(7.0))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.2))
        .andExpect(jsonPath("$.max").value(1.6));
        
        
        transaction = createTransaction(1.1, timestamp + 4000);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics- min value changed
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(6))
                .andExpect(jsonPath("$.sum").value(8.1))
                .andExpect(jsonPath("$.avg").value(1.35))
                .andExpect(jsonPath("$.min").value(1.1))
                .andExpect(jsonPath("$.max").value(1.6));

        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(5))
        .andExpect(jsonPath("$.sum").value(6.9))
        .andExpect(jsonPath("$.avg").value(1.38))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
       
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(4))
        .andExpect(jsonPath("$.sum").value(5.6))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(3))
        .andExpect(jsonPath("$.sum").value(4.2))
        .andExpect(jsonPath("$.avg").value(1.4))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.6));
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(1))
        .andExpect(jsonPath("$.sum").value(1.1))
        .andExpect(jsonPath("$.avg").value(1.1))
        .andExpect(jsonPath("$.min").value(1.1))
        .andExpect(jsonPath("$.max").value(1.1));
        
        TimeUnit.SECONDS.sleep(1);
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.count").value(0))
        .andExpect(jsonPath("$.sum").value(0))
        .andExpect(jsonPath("$.avg").value(0))
        .andExpect(jsonPath("$.min").value(0))
        .andExpect(jsonPath("$.max").value(0));
	}
	
	
	@Test
	public void testAddTransactionOutOfRange() throws Exception {
		
		long delayToBeginningOfNextSecond = 1100 - System.currentTimeMillis() % 1000;
		TimeUnit.MILLISECONDS.sleep(delayToBeginningOfNextSecond);

        long timestamp = System.currentTimeMillis() + 1000;

        Transaction transaction = createTransaction(1.2, timestamp);
        // add statistics
        mvc.perform(post(ChallangeController.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
        // validate new statistics
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0.0))
                .andExpect(jsonPath("$.avg").value(0.0))
                .andExpect(jsonPath("$.min").value(0.0))
                .andExpect(jsonPath("$.max").value(0.0));
        
        TimeUnit.SECONDS.sleep(1);
        // validate new statistics
        mvc.perform(get(ChallangeController.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.sum").value(1.2))
                .andExpect(jsonPath("$.avg").value(1.2))
                .andExpect(jsonPath("$.min").value(1.2))
                .andExpect(jsonPath("$.max").value(1.2));
        
       
	}

	private Transaction createTransaction(double amount, long timestamp) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTimestamp(timestamp);

		return transaction;
	}
}
