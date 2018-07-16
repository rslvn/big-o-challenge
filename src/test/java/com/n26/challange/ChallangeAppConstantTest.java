package com.n26.challange;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by resulav on 16.07.2018.
 */
public class ChallangeAppConstantTest {

	@Test
	public void testConstructor() {
		ChallangeAppConstant constants = new ChallangeAppConstant();
		Assert.assertNotNull("constants can not be null", constants);
	}

}
