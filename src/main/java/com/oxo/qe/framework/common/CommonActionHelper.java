package com.oxo.qe.framework.common;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oxo.qe.framework.web.helpers.WebDriverHelper;

import cucumber.api.Scenario;


public class CommonActionHelper extends WebDriverHelper
{

	private static final  Logger logger = Logger.getLogger(CommonActionHelper.class);
	private WebElement objElement;
	private Select objSelectDropdown;
	
	private boolean isStepPass = false;


	public CommonActionHelper() 
	{

	}

	public By getbjectLocator(WebElement ele)
	{
		String type = "";
		String value =  "";

		By locator = null;
		String elementStr = ele.toString();
		logger.debug("Element String:: "+elementStr);
		if(elementStr != null && elementStr.contains("->")){
			String sraary[] = elementStr.split("->");
			elementStr = sraary[1];
			int index=elementStr.indexOf(":");
			type = elementStr.substring(0,index).trim();
			value = elementStr.substring(index+1,elementStr.length()-1);
		}else if(elementStr != null && elementStr.contains("Proxy element for: DefaultElementLocator '")){
			int index = elementStr.indexOf("Locator '");
			elementStr = elementStr.substring(index+9,elementStr.length());
			int ss1= elementStr.indexOf(":", 1);
			value = elementStr.substring(ss1+2, elementStr.length()-1);
			elementStr = elementStr.substring(0, ss1);
			type = elementStr.substring(elementStr.indexOf(".", 1)+1, elementStr.length());
		}
		logger.debug("Locator Type::"+type);
		if(type !=null && !type.equalsIgnoreCase("xpath")){
			value = "'"+value+"'";
		}
		logger.debug("Locator Value:: "+value);
		LocatorTypeEnum ltEnum = LocatorTypeEnum.valueOf(type);
		switch(ltEnum)
		{
		case id:
			locator = By.id(value);
			break;
		case name:
			locator = By.name(value);
			break;
		case cssSelector:
			locator = By.cssSelector(value);
			break;
		case linkText:
			locator = By.linkText(value);
			break;
		case partialLinkText:
			locator = By.partialLinkText(value);
			break;
		case tagName:
			locator = By.tagName(value);
			break;
		case xpath:
			locator = By.xpath(value);
			break;
		}
		return locator;

	}


	public WebElement waitForElement(WebElement element) 
	{
		logger.info("Waiting for element : " + element);
		if (driver != null) 
		{
			wait = new WebDriverWait(driver, explicitWaitTime);
		} 
		if(element != null)
		{
			//getbjectLocator(element);

			WebDriverWait wait = new WebDriverWait(driver, explicitWaitTime);
			//wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.presenceOfElementLocated(getbjectLocator(element)));
			scrollPageToWebElement(element);
		}


		return element;
	}

	protected boolean moveHover(WebElement element) 
	{
		boolean flag = false;
		try{
			//Thread.sleep(2000);
			logger.info("Hover on an element");
			this.objElement = waitForElement(element);
			if (isClickable(element)) 
			{

				Actions actions = new Actions(driver);
				actions.moveToElement(objElement).build().perform();
				//objElement.click();
				//Thread.sleep(1000);
				flag = true;
				
			}
		}catch (Exception e) {
			logger.error("Hover on element::"+e.getMessage());
			//e.printStackTrace();
		}
		return flag;
	}

	public boolean isClickable(WebElement element)
	{
		try	{

			this.objElement = waitForElement(element);
			wait = new WebDriverWait(getDriver(), explicitWaitTime);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.debug("Element is clickable true");
			return true;
		}catch (Exception e) {
			logger.debug("Element is clickable false");
			return false;
		}
	}
	/*public static void waitForPageLoad() {
	    WebDriverWait wait = new WebDriverWait(driver, 60);

	    Predicate<RemoteWebDriver> pageLoaded = new Predicate<RemoteWebDriver>() {

	        @Override
	        public boolean apply(RemoteWebDriver input) {
	            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	        }

	    };
	    wait.until(pageLoaded);
	}*/


	public static boolean waitForPageLoad(RemoteWebDriver driver) 
	{
		boolean pageLoadwaitFlag= false;
		try
		{
			ExpectedCondition<Boolean> pageLoadCondition = new
					ExpectedCondition<Boolean>() 
			{
				//	@Override
				public Boolean apply(WebDriver driver) 
				{
					return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
				}
			};

			WebDriverWait wait = new WebDriverWait(driver, pageLoadWaitTime);
			wait.until(pageLoadCondition);
			pageLoadwaitFlag= true;
		}catch (Exception e) 
		{
			logger.error("Page Load Wait exception msg::"+e.getMessage());
		}
		logger.debug("Page load wait time seconds:"+pageLoadWaitTime+" :: isPageLoaded:"+pageLoadwaitFlag);
		return pageLoadwaitFlag;
	}

	/**
	 * Get the title of a page
	 * 
	 * @return title of the screen
	 * @throws Exception
	 */
	public String getTitle() {
		String screenTitle = driver.getTitle();
		logger.info("The title is " + screenTitle);

		return screenTitle;
	}

	/**
	 * Get the current URL of the application
	 * 
	 * @return returns the url of the page
	 * @throws Exception
	 */
	public String getCurrentPageURL() {
		logger.info("Get the current page url");
		String appURL = driver.getCurrentUrl();
		return appURL;
	}

	/**
	 * Get the text of a label
	 * 
	 * @param WebElement
	 *            as the paramter
	 * @return text associated with the WebElement
	 */
	protected String getText(WebElement element) {
		String actualText = null;
		try{
			this.objElement = waitForElement(element);
			if(objElement.isEnabled()){
				actualText = objElement.getText();
				
			}
		}catch (Exception e) {
			logger.error("gettext exception msg");
		}
		logger.info("The text associated with the WebElement is " + actualText);
		return actualText;
	}


	/**
	 * Set the textfield value
	 * 
	 * @param element
	 * @param text
	 * @throws Exception
	 */
	protected void setInputText(WebElement element, String text) {
		logger.info("Input the text box value : " + text);
		try{
			this.objElement = waitForElement(element);
			if(objElement.isEnabled())
			{ 
				objElement.clear();
				objElement.sendKeys(text);
				
			}
		}catch (Exception e) {
			logger.error("Exception in setInputText msg::"+e.getMessage());
		}

	}

	protected void setInputTextWithEnterKey(WebElement element, String text) {
		logger.info("Input the text box value : " + text);
		try{
			this.objElement = waitForElement(element);
			if(objElement.isEnabled()){
				objElement.sendKeys(text+"\n");
				
			}
		}catch (Exception e) {
			logger.error("Exception in setInputTextWithEnterKey msg::"+e.getMessage());
		}

	}

	public void scrollPageToWebElement(WebElement element) 
	{
		logger.info("ScrollPage to Element");
		try{
			if(element != null)
			{
				Thread.sleep(500);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
				Thread.sleep(500);
				((JavascriptExecutor)driver).executeScript("window.scrollBy(0, -200)", "");

				
			}
		}catch (Exception e) {
			logger.error("ScrollToElement Exception Msg:: "+e.getMessage());
		}
	}

	/**
	 * Press the TAB button
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected void tabInputBox(WebElement element) {
		logger.info("Press the TAB");
		try{
			this.objElement = waitForElement(element);
			if(objElement.isEnabled()){
				objElement.sendKeys(Keys.TAB);
				
			}
		}catch (Exception e) {
			logger.error("Exception in  tabInputBox msg::"+e.getMessage());
		}
	}

	/**
	 * Clears the value from the TextBox
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected void clearInputBox(WebElement element) {
		logger.info("Clear the Input box value");
		try{
			this.objElement = waitForElement(element);
			if(objElement.isEnabled()){
				objElement.clear();
				
			}
		}catch (Exception e) {
			logger.error("Exception in  clearInputBox msg::"+e.getMessage());
		}
	}


	/**
	 * Clicks on the Button
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected boolean clickOnButton(WebElement element) {
		boolean flag = false;
		try{
			//Thread.sleep(2000);
			logger.info("Click on the button");
			this.objElement = waitForElement(element);
			if (isClickable(element)) 
			{

				Actions actions = new Actions(driver);
				actions.moveToElement(objElement).click().build().perform();
				//objElement.click();
				//Thread.sleep(1000);
				flag = true;
				
			} 
		}catch (Exception e) {
			logger.error("clickOnButton exception msg::"+e.getMessage());
			//e.printStackTrace();
		}
		return flag;
	}

	/*public static void popupCheck() {
		 
        WebElement btnClosePopup = driver.findElement(By.xpath("//*[contains(@id,'evergage-tooltip')]//*[@title='Close Message']"));
        if(btnClosePopup.isDisplayed())
        {
               btnClosePopup.click();
        }else {
               System.out.println("Pop up not displayed");
        }
 }*/
	

	public WebElement getfindElementByXPath(String xpath){
		WebElement element = null;
		try{

			if(xpath != null){
				element = getDriver().findElementByXPath(xpath);
			}
		}catch (Exception e) {
			logger.error("getfindElementByXPath exception msg::"+e.getMessage());
			e.printStackTrace();
		}
		return element;
	}
	/**
	 * Clicks on the Radio Button
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected void clickOnRadioButton(WebElement element) {
		logger.info("Click on the Radio button");
		try{
			this.objElement = waitForElement(element);
			if (objElement.isEnabled()) {
				objElement.click();
				
			} 
		}catch (Exception e) {
			logger.error("Exception in  ClickonRadioBtn msg::"+e.getMessage());
		}
	}

	/**
	 * Clicks on the Link
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void clickOnLink(WebElement element) {
		logger.info("Click on the Link");
		try{
			this.objElement = waitForElement(element);
			if(objElement != null){
				objElement.click();
				
			}
		}catch (Exception e) {
			logger.error("Exception in  setInputText msg::"+e.getMessage());
		}
	}

	/**
	 * Selects an dropdown value by visible text
	 * 
	 * @param element
	 * @param text
	 * @throws Exception
	 */
	protected void selectByText(WebElement element, String text) {
		logger.info("The Checkbox value to be selected is : " + text);
		try{
			this.objElement = waitForElement(element);
			this.objSelectDropdown = new Select(objElement);
			if (objElement.isEnabled()) {
				objSelectDropdown.selectByVisibleText(text);
				
			} 
		}catch (Exception e) {
			logger.error("Exception in  setInputText msg::"+e.getMessage());
		}
	}

	/**
	 * Selects an dropdown value by Value attribute of the entry
	 * 
	 * @param element
	 * @param text
	 * @throws Exception
	 */
	protected void selectByValue(WebElement element, String text) {
		logger.info("The Checkbox value(by Value) to be selected is : " + text);
		try{
			this.objElement = waitForElement(element);
			this.objSelectDropdown = new Select(objElement);
			if (objElement.isEnabled()) {
				objSelectDropdown.selectByValue(text);
				
			} 
		}catch (Exception e) {
			logger.error("Exception in  setInputText msg::"+e.getMessage());
		}
	}

	/**
	 * Checks if the Webelement is enabled
	 * 
	 * @param WebElement
	 * @throws Exception
	 */
	protected boolean isEnabled(WebElement element) {
		logger.info("Check if the Webelement is enabled");
		boolean flag = false;
		try{
			this.objElement = waitForElement(element);
			flag = this.objElement.isEnabled();

		}catch (Exception e) {
			logger.error("Exception in  isEnabled msg::"+e.getMessage());
		}
		return flag;
	}

	/**
	 * Checks if the Webelement is displayed
	 * 
	 * @param WebElement
	 * @throws Exception
	 */
	
	protected boolean VerifyTextPersent(String expectedText) 
	{
		boolean flag = false;
		logger.info("Check if the Text is persent on the page");
		try 
		{
			 waitForPageLoad(getDriver());
			 String pageText = driver.getPageSource().replaceAll("[^a-zA-Z0-9]", "");
			 String exptxt = expectedText.replaceAll("[^a-zA-Z0-9]", "");
			 Assert.assertTrue(pageText.contains(exptxt));
			
			 logger.info("Text/Message -> " + expectedText + " <- is found on the current page.");
			 flag = true;
			 
		}
		catch (java.lang.NullPointerException e)
		{
			logger.error("NullPointerException in  isDisplay msg::"+e.getMessage());
			flag = false;
			
		}
		catch(AssertionError e)
		 {
			logger.error("AssertionError in  isDisplay msg::"+e.getMessage());
			flag = false;
			
			throw new AssertionError("Text/Message -> " + expectedText + " <- is not found on the current page.");
		 }
		catch(Exception e)
		 {
			logger.error("Exception in  isDisplay msg::"+e.getMessage());
			flag = false;
		 }
		return flag;
		
	}
	
	protected boolean VerifyTextNotPersent(String expectedText) 
	{
		boolean flag = false;
		logger.info("Check if the Text is persent on the page");
		try 
		{
			 waitForPageLoad(getDriver());
			 String pageText = driver.getPageSource();
			 Assert.assertFalse(pageText.contains(expectedText));
			
			 logger.info("Text/Message -> " + expectedText + " <- is not found on the current page.");
			 flag = true;
		}
		catch (java.lang.NullPointerException e)
		{
			logger.error("NullPointerException in  isDisplay msg::"+e.getMessage());
			flag = false;
			
		}
		catch(AssertionError e)
		 {
			logger.error("AssertionError in  isDisplay msg::"+e.getMessage());
			flag = false;
			
			throw new AssertionError("Text/Message -> " + expectedText + " <- is found on the current page.");
		 }
		catch(Exception e)
		 {
			logger.error("Exception in  isDisplay msg::"+e.getMessage());
			flag = false;
			
		 }
		return flag;
		
	}
	
	
	protected boolean isDisplayed(WebElement element) {
		logger.info("Check if the Webelement is displayed");
		boolean flag = false;
		try{
			this.objElement = waitForElement(element);
			flag = this.objElement.isDisplayed();
			if (flag) {
				
			}else{
				
			}
		}catch (Exception e) {
			logger.error("Exception in  isDisplay msg::"+e.getMessage());
		}
		return flag;
	}

	/**
	 * Checks if the Webelement is selected
	 * 
	 * @param WebElement
	 * @throws Exception
	 */
	protected boolean isSelected(WebElement element) {
		logger.info("Check if the Webelement is selected");
		boolean flag = false;
		try{
			this.objElement = waitForElement(element);
			flag = this.objElement.isSelected();

		}catch (Exception e) {
			logger.error("Exception in  isSelected msg::"+e.getMessage());
		}
		return flag;
	}

	protected boolean isSelected(WebElement element, String elementDescription) {
		logger.info("Check if the Webelement is selected");
		boolean flag;
		this.objElement = waitForElement(element);
		flag = this.objElement.isSelected();
		if (flag) {
			
		}
		return flag;
	}

	
	/**
	 * clears text form input field
	 * 
	 * @param webElement
	 */
	protected void clearText(WebElement webElement) {
		try{
			webElement.clear();
		}catch (Exception e) {
			logger.error("Exception in  clearText msg::"+e.getMessage());
		}
	}

	public static boolean embedScreenshot(Scenario scenario) {  
		boolean flag =false;
		try { 
			takeScreenshot();
			scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");  
			flag =true;
		} catch (WebDriverException wde) { 
			logger.error("embedScreenshot() inside WebDriverException while execution::"+wde.getMessage());
		} catch (ClassCastException cce) { 
			logger.error("embedScreenshot() inside ClassCastException while execution::"+cce.getMessage());
		}  
		logger.debug("EmbedScreenshot flag::"+flag);
		return flag;
	}  
	
	public static void verifyLinks(String urlLink) {
		try {
			URL link = new URL(urlLink);
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				//System.out.println(urlLink + " - " + httpConn.getResponseMessage());
				logger.info(urlLink + " - " + httpConn.getResponseMessage());
			}
			if (httpConn.getResponseCode() == 404) {
				//System.out.println(urlLink + " - " + httpConn.getResponseMessage());
				logger.info(urlLink + " - " + httpConn.getResponseMessage());
			}
		}
		catch (Exception e) {
			// e.printStackTrace();
		}
	}


	/*
	 * public void captureScreenShot(String status) {
	 * logger.debug("Status::"+status);
	 * 
	 * if(Constants.PASS.equalsIgnoreCase(status)){ this.isStepPass = true; }else{
	 * this.isStepPass = false; }
	 * 
	 * if("yes".equalsIgnoreCase(webPropHelper.captureScreenShot)){
	 * 
	 * if("no".equalsIgnoreCase(webPropHelper.captureOnlyFAIL) && !isStepPass){
	 * //(Constants.FAIL.equalsIgnoreCase(status) ||
	 * Constants.PARTIALLYPASS.equalsIgnoreCase(status))){ takeScreenshot(); } } }
	 */


	public static void takeScreenshot(){

		try {
			File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("Report/Screenshots/"+Constants.screenShortTagNames+"_"+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isStepPass() {
		return isStepPass;
	}

	public void setStepPass(boolean isStepPass) {
		this.isStepPass = isStepPass;
	}

	public void close() {
		try{
			
			if(driver != null){
				driver.quit();
			}
		}catch (Exception e) {
			logger.error("close driver() inside Exception while execution::"+e.getMessage());
			//captureScreenShot(Constants.FAIL);
		}
	}
}