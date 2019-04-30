package com.oxo.qe.framework.web.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oxo.qe.framework.common.CommonActionHelper;
import com.oxo.qe.framework.common.Constants;
import com.oxo.qe.framework.common.OSInfo;
import com.oxo.qe.framework.common.PropertiesHelper;

import io.github.bonigarcia.wdm.WebDriverManager;


public class WebDriverHelper {
	private static final Logger logger = Logger.getLogger(WebDriverHelper.class);
	public static String homeURL;
	public static RemoteWebDriver driver;
	public WebDriverWait wait;
	public PropertiesHelper webPropHelper = PropertiesHelper.getInstance();
	public static String browserType;
	public static String testtype;
	public static String testURL;
	public static int explicitWaitTime;
	public static int pageLoadWaitTime;
	private String runBrowserHeadLess ;	
	public static int thread_medium;		
	
	
	public void initializeDriver(){

		try{
			testtype = System.getProperty("testtype");
			testURL = System.getProperty("testurl");
			
			testtype = webPropHelper.getConfigPropProperty("AppType");

			logger.debug("platformType:: "+testtype);
			browserType = System.getProperty("browser");

			browserType = webPropHelper.getConfigPropProperty("browserType");
			logger.debug("browserType:: "+browserType);
			if(browserType != null){
				browserType = browserType.toLowerCase();
			}

			if("web".equalsIgnoreCase(testtype)){
				initializeWebDriver(browserType);

				logger.debug("Window size maximise........");
				driver.manage().window().maximize();

			} 
			
			else{
				logger.debug("Platform Type Not defined due to not able to initialize Driver------------------------platformType::"+testtype);
			}

			if(driver!= null ){
				explicitWaitTime=getWaitTime("WEBDRIVER_EXPLICIT_WAITTIME_SECONDS");
				pageLoadWaitTime=getWaitTime("PAGELOAD_WAITTIME_SECONDS");
			}
			
			logger.debug("HEIGHT::"+ driver.manage().window().getSize().getHeight());
			logger.debug("WIDTH::"+ driver.manage().window().getSize().getWidth());
		}catch (Exception e) {
			logger.error("Exception inside initializeRemoteWebDriver() "+e.getMessage());
			e.printStackTrace();
		} 
	}

	public void initializeWebDriver(String browserType){
		logger.debug("initializeWebDriver() inside process......");
		try{

			if("chrome".equalsIgnoreCase(browserType)){
				//launchChromeBrowser(getWebDriverFolderPath("chromedriver"));
				WebDriverManager.chromedriver().setup();
				
				driver = new ChromeDriver();
			}else if("firefox".equalsIgnoreCase(browserType)){
				launchFirefoxBrowser(getWebDriverFolderPath("geckodriver"));
			}else{
				//launchChromeBrowser(getWebDriverFolderPath("chromedriver"));
				
			}

		}catch (Exception e) {
			logger.error("Exception inside initializeRemoteWebDriver() "+e.getMessage());
			e.printStackTrace();
		} catch (Throwable e) {
			logger.error("Throwable inside initializeRemoteWebDriver() "+e.getMessage());
			e.printStackTrace();
		}
	}

	public String getWebDriverFolderPath(String driverFileName){
		String finalPath = null;
		String osName = System.getProperty("osname");
		if(osName != null){
			OSInfo.setOSName(osName);
		}
		if(OSInfo.OS.WINDOWS.equals(OSInfo.getOs())){
			finalPath =Constants.DRIVERSFOLDERPATH=Constants.DRIVERSROOTFOLDERPATH+"windows/"+driverFileName+".exe";
		}else if(OSInfo.OS.MAC.equals(OSInfo.getOs())){
			finalPath =Constants.DRIVERSFOLDERPATH=Constants.DRIVERSROOTFOLDERPATH+"mac/"+driverFileName;
		}else if(OSInfo.OS.UNIX.equals(OSInfo.getOs())){
			finalPath =Constants.DRIVERSFOLDERPATH=Constants.DRIVERSROOTFOLDERPATH+"linux/";
		}else if(OSInfo.OS.POSIX_UNIX.equals(OSInfo.getOs())){
			finalPath =Constants.DRIVERSFOLDERPATH=Constants.DRIVERSROOTFOLDERPATH+"linux/";
		}
		logger.debug("WebDriver Folder Path::"+finalPath);
		return finalPath;
	}
	public boolean openBaseURL(String propKey){ //(String url){
		logger.debug("testURL:: "+testURL);
		if(testURL !=null && !testURL.isEmpty()){
			homeURL = testURL;
		}else{
			homeURL = webPropHelper.getConfigPropProperty(propKey);
		}
		
		logger.debug("Open Chrome browser with URL::"+homeURL);
		
		long starTtime = System.currentTimeMillis();
		getDriver().get(homeURL);
		boolean isPageLoad = CommonActionHelper.waitForPageLoad(driver);
		long endTime = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		logger.debug("URL page load time is: " + formatter.format((endTime - starTtime) / 1000d) + " seconds");
		return isPageLoad;
	}
	
	//Opening FF
	public RemoteWebDriver launchFirefoxBrowser(String driverfilePath) throws Throwable
	{
		logger.debug("Launching Firefox Browser....................");
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions ffOptions = new FirefoxOptions();
		ffOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ffOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		ffOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE );
		ffOptions.addArguments("--disable-popup-blocking");
		if(Constants.isBrowserProxyEnabled){
			logger.debug("&&&&&&&&&&&&&&&&&&& Browser proxy added...............");
			//ffOptions.setCapability(CapabilityType.PROXY, BrowserProxyHelper.getInstance().getSeleniumProxy());
		}
		if("yes".equalsIgnoreCase(runBrowserHeadLess)){
			
			ffOptions.setHeadless(true);
			ffOptions.addArguments("--disable-gpu");
			ffOptions.addArguments("--disable-infobars");
			ffOptions.addArguments("--disable-notifications");
			driver = new FirefoxDriver(ffOptions);
		}else{
			driver = new FirefoxDriver(ffOptions);
		}
		
		return driver;
	}

	//Opening Google_Chrome
	public RemoteWebDriver launchChromeBrowser(String driverfilePath) throws Throwable
	{
		logger.debug("Launching Chrome Browser....................");
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        chromeOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE );
		chromeOptions.addArguments("--disable-popup-blocking");
		if(Constants.isBrowserProxyEnabled){
			logger.debug("&&&&&&&&&&&&&&&&&&& Browser proxy added...............");
			chromeOptions.addArguments("-incognito");
			// chromeOptions.setCapability(CapabilityType.PROXY, BrowserProxyHelper.getInstance().getSeleniumProxy());
		}
		
		if("yes".equalsIgnoreCase(runBrowserHeadLess)){
			chromeOptions.setHeadless(true);
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--disable-notifications");
			driver=new ChromeDriver(chromeOptions);
		}else{
			driver=new ChromeDriver(chromeOptions);
		}
		return driver;
	}
	
	public static void quitDriver() {
		logger.debug("inside teardown()....");
		if (driver != null) {
			driver.quit();
			logger.debug("driver quit success.");
		}
	}

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public int getWaitTime(String key){
		int wait=10;
		try{
			if(webPropHelper.containsKeyFromConfigProp(key)){
				String waitTimeStr = webPropHelper.getConfigPropProperty(key);
				logger.debug(key+"::"+waitTimeStr);
				if(waitTimeStr!= null && waitTimeStr.matches("-?\\d+")){
					wait=Integer.valueOf(waitTimeStr);
				}
			}

		}catch (Exception e) {
			logger.error("getImplicitWaitTime error msg::"+e.getMessage());

		}
		return wait;
	}
	
		public void threadSleep() {
		
			thread_medium = Integer.parseInt(webPropHelper.getTestDataProperty("THREAD_MEDIUM"));
		
		}
		
}


