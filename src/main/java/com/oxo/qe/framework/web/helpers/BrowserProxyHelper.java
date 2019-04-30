package com.oxo.qe.framework.web.helpers;
/*
 * package com.aso.qe.framework.web.helpers;
 * 
 * import java.io.File;
 * 
 * import org.apache.log4j.Logger; import org.openqa.selenium.Proxy;
 * 
 * import net.lightbody.bmp.BrowserMobProxy; import
 * net.lightbody.bmp.proxy.CaptureType;
 * 
 * public class BrowserProxyHelper { private static final Logger logger =
 * Logger.getLogger(BrowserProxyHelper.class);
 * 
 * public BrowserMobProxy proxy = null; private static Proxy selinumProxy;
 * 
 * private BrowserProxyHelper() { try { if (proxy == null) { } } catch
 * (Exception e) { logger.error("proxy loader exception msg::" +
 * e.getMessage()); } }
 * 
 * private static class LazyHolder { private static final BrowserProxyHelper
 * INSTANCE = new BrowserProxyHelper(); }
 * 
 * public static BrowserProxyHelper getInstance() { return LazyHolder.INSTANCE;
 * }
 * 
 * public Proxy getSeleniumProxy() { return selinumProxy; }
 * 
 * public void setProxyCaptureTypes() {
 * 
 * if (proxy != null && proxy.isStarted()) {
 * logger.debug("setProxyCaptureTypes inside......");
 * proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT,
 * CaptureType.RESPONSE_CONTENT); } }
 * 
 * public void setHarFileName() { if (proxy != null && proxy.isStarted()) {
 * logger.debug("setProxy Har file inside......");
 * proxy.newHar("www.academy.com"); } }
 * 
 * public boolean generateHarFile(String fileName) { boolean harGenerated =
 * false; try { logger.debug("generating har file........."); File file = new
 * File("HarFiles"); if (!file.exists()) { file.mkdir(); } if (proxy != null &&
 * proxy.isStarted()) { File harFile = new File(file.getAbsolutePath() + "/" +
 * fileName); proxy.getHar().writeTo(harFile);
 * logger.debug(".HAR file generated successfully ::" +
 * harFile.getAbsolutePath()); stopProxy(); harGenerated = true; }
 * 
 * } catch (Exception ex) { logger.error(ex.getMessage() +
 * " ::Could not find file " + fileName); } return harGenerated; }
 * 
 * public void stopProxy() { try { if (proxy != null) { proxy.stop(); proxy =
 * null; logger.debug("Proxy Server STOPED."); } } catch (Exception e) {
 * logger.error("stopProxy msg::" + e.getMessage()); } } }
 */