package com.oxo.qe.framework.common;
/*
 * package com.aso.qe.framework.common;
 * 
 * import org.apache.log4j.Logger; import
 * org.openqa.selenium.JavascriptExecutor; import org.openqa.selenium.WebDriver;
 * import org.openqa.selenium.support.ui.ExpectedCondition; import
 * org.openqa.selenium.support.ui.WebDriverWait;
 * 
 * import com.aso.qe.framework.web.helpers.WebDriverHelper;
 * 
 * public class WaiterHelper extends WebDriverHelper { private static final
 * Logger logger = Logger.getLogger(WaiterHelper.class);
 * 
 * // Wait for JQuery Load public static boolean waitForJQueryLoad() { boolean
 * jQueryFlag=false;try { ExpectedCondition<Boolean> pageLoadCondition = new
 * ExpectedCondition<Boolean>() { public Boolean apply(WebDriver driver) {
 * return ((Long) ((JavascriptExecutor)
 * driver).executeScript("return jQuery.active") == 0); } };
 * 
 * WebDriverWait wait = new WebDriverWait(driver, pageLoadWaitTime);
 * wait.until(pageLoadCondition); logger.debug("JQuery is Ready!"); jQueryFlag =
 * true; }catch( Exception e) { logger.error("JQuery Wait exception msg::" +
 * e.getMessage()); }logger.debug("JQuery wait time seconds:"
 * +pageLoadWaitTime+" :: isPageLoaded:"+jQueryFlag);return jQueryFlag;}
 * 
 * // Wait for Angular Load public static boolean waitForAngularLoad() { boolean
 * angularFlag=false; String angularReadyScript =
 * "return angular.element(document).injector().get('$http').pendingRequests.length === 0"
 * ;try { ExpectedCondition<Boolean> pageLoadCondition = new
 * ExpectedCondition<Boolean>() {
 * 
 * @Override public Boolean apply(WebDriver driver) { return
 * Boolean.valueOf(((JavascriptExecutor)
 * driver).executeScript(angularReadyScript).toString()); } };
 * 
 * WebDriverWait wait = new WebDriverWait(driver, pageLoadWaitTime);
 * wait.until(pageLoadCondition); logger.debug("ANGULAR is Ready!"); angularFlag
 * = true; }catch( Exception e) { logger.error("ANGULAR Wait exception msg::" +
 * e.getMessage()); }logger.debug("ANGULAR wait time seconds:"
 * +pageLoadWaitTime+" :: isPageLoaded:"+angularFlag);return angularFlag;}
 * 
 * // Wait Until JS Ready public static boolean waitUntilJSReady() { boolean
 * pageLoadwaitFlag=false;try{ ExpectedCondition<Boolean> pageLoadCondition=new
 * ExpectedCondition<Boolean>(){public Boolean apply(WebDriver
 * driver){return((JavascriptExecutor)driver).
 * executeScript("return document.readyState").equals("complete");}};
 * 
 * WebDriverWait wait = new WebDriverWait(driver,
 * pageLoadWaitTime);wait.until(pageLoadCondition);pageLoadwaitFlag=true;}catch(
 * Exception
 * e){logger.error("Page Load Wait exception msg::"+e.getMessage());}logger.
 * debug("Page load wait time seconds:"+pageLoadWaitTime+" :: isPageLoaded:"
 * +pageLoadwaitFlag);return pageLoadwaitFlag;}
 * 
 * // Wait Until JQuery and JS Ready public static void waitUntilJQueryReady() {
 * JavascriptExecutor jsExec = (JavascriptExecutor) driver;
 * 
 * // First check that JQuery is defined on the page. If it is, then wait AJAX
 * Boolean jQueryDefined = (Boolean)
 * jsExec.executeScript("return typeof jQuery != 'undefined'");if(jQueryDefined=
 * =true){ // Wait JQuery Load // waitForJQueryLoad();
 * 
 * // Wait JS Load waitUntilJSReady(); } else {
 * logger.debug("jQuery is not defined on this site!");}}
 * 
 * // Wait Until Angular and JS Ready public static void waitUntilAngularReady()
 * { JavascriptExecutor jsExec = (JavascriptExecutor) driver;
 * 
 * // First check that ANGULAR is defined on the page. If it is, then wait
 * ANGULAR Boolean angularUnDefined=(Boolean)jsExec.
 * executeScript("return window.angular === undefined");if(!angularUnDefined){
 * Boolean angularInjectorUnDefined = (Boolean) jsExec
 * .executeScript("return angular.element(document).injector() === undefined");
 * if(!angularInjectorUnDefined) {
 * 
 * // Wait Angular Load waitForAngularLoad();
 * 
 * // Wait JS Load waitUntilJSReady();
 * 
 * }else { logger.debug("Angular injector is not defined on this site!"); }
 * }else{logger.debug("Angular is not defined on this site!");}}
 * 
 * // Wait Until JQuery Angular and JS is ready public static void
 * waitJQueryAngular() { waitUntilJQueryReady(); waitUntilAngularReady(); }
 * 
 * public static void sleep(Integer seconds) { long secondsLong = (long)
 * seconds; try { Thread.sleep(secondsLong); } catch (InterruptedException e) {
 * e.printStackTrace(); } } }
 */