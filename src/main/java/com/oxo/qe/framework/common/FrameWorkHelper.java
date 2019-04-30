
package com.oxo.qe.framework.common;

import org.apache.commons.lang3.RandomStringUtils;



public class FrameWorkHelper {
	
	public static String getRandomAlphabetic(int randomStrLength){
		
		return RandomStringUtils.randomAlphabetic(randomStrLength);
	}

	public static String getRandomAlphanumeric(int randomStrLength){
		return RandomStringUtils.randomAlphanumeric(randomStrLength);
	}
	
	
	public static String getRandomNumber(int randomIntLength){
		return RandomStringUtils.randomNumeric(randomIntLength);
	}
	
}
