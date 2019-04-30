package com.oxo.qe.framework.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesHelper {
	private static final Logger logger = Logger.getLogger(PropertiesHelper.class);
	private final Properties configProp = new Properties();
	private final Properties testDataProperty = new Properties();

	private PropertiesHelper()
	{
		logger.info("Read all properties from file");
		try {

			FileInputStream configPropFis = getFileInputStrem(System.getProperty("user.dir")+ "/src/test/resources/config/Config.properties");
			if(configPropFis != null){
				configProp.load(configPropFis);
			}
			logger.info("properties file load Done.");

			String testdataPropFileName= System.getProperty("testdatafilename");
			if(testdataPropFileName ==null ){
				testdataPropFileName = getConfigPropProperty("testdatafilename");
			}
			logger.info("testdatafilename::"+testdataPropFileName);
			if(testdataPropFileName != null && testdataPropFileName.length()>1){
				if(!testdataPropFileName.contains(".properties")){
					testdataPropFileName=testdataPropFileName+".properties";
				}
				logger.info("Final testdatafilename::"+testdataPropFileName);
				FileInputStream testDataPropFis = getFileInputStrem(System.getProperty("user.dir")+ "/src/test/resources/TestData/"+testdataPropFileName);
				if(testDataPropFis != null){
					testDataProperty.load(testDataPropFis);
				}
			}

				
		} catch (IOException e) {
			logger.error("PropertiesHelper IOException:: "+e.getMessage());
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			logger.error("PropertiesHelper NumberFormatException:: "+e.getMessage());
			e.printStackTrace();
			
		}
	} 

	
	private static class LazyHolder
	{
		private static final PropertiesHelper INSTANCE = new PropertiesHelper();
	}

	public static PropertiesHelper getInstance()
	{
		return LazyHolder.INSTANCE;
	}

	public String getConfigPropProperty(String key){
		return configProp.getProperty(key);
	}

	public boolean containsKeyFromConfigProp(String key){
		return configProp.containsKey(key);
	}

	public String getTestDataProperty(String key){
		return testDataProperty.getProperty(key);
	}

	public FileInputStream getFileInputStrem(String filePath){
		FileInputStream fileInputStrem = null;
		try{
			fileInputStrem = new FileInputStream(filePath);
		}catch (Exception e) {
			logger.error("getFileInputStrem() exception msg::"+e.getMessage());
			logger.error("FILE NOT FOUND::"+filePath);
		}
		return fileInputStrem;
	}

}
