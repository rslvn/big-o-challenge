package com.n26.challange;

import com.n26.challange.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallangeApplicationTests {

    @Autowired
    private TransactionService transactionService;

	@Test
	public void contextLoads() {
        Assert.assertNotNull("transactionService can not be null", transactionService);
	}

}
