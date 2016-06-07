package Branch1;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class DownloadDriver {
	private static final String DLTarget = "https://www.youtube2mp3.cc/";
	public static WebDriver driver = null;
	 
	public static void runDownloadQueue(List<String> urls, String browser) throws InterruptedException{
		String testURL = "https://www.youtube.com/watch?v=_mWPPBW4DU8";
		if(browser.contains("Chrome")){
			driver = getDriverProfileChrome();
		}else if(browser.contains("Firefox")){
			driver = getDriverProfileFirefox();
		}else{
			System.out.println("ERROR IN BROWSER STRING RESOLVE");
			System.exit(0);
		}
		
		for(String url : urls){
			driver.get("http://www.youtube-mp3.org/");
			driver.findElement(By.id("youtube-url")).clear();
			driver.findElement(By.id("youtube-url")).click();
			driver.findElement(By.id("youtube-url")).sendKeys(url);
			driver.findElement(By.id("submit")).click();
		
			WebDriverWait _wait = new WebDriverWait(driver, 30);
			_wait.until(ExpectedConditions.elementToBeClickable(By.id("dl_link")));
		
			driver.findElement(By.id("dl_link")).findElement(By.partialLinkText("Download")).click();
			Thread.sleep(1000);
		}
	}
	
	
	public static WebDriver getDriverProfileChrome(){
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", YQController.dlDir);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("download.extensions_to_open", "");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(cap);
		return driver;
	}
	
	public static WebDriver getDriverProfileFirefox(){
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAssumeUntrustedCertificateIssuer(false);
        profile.setEnableNativeEvents(false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", YQController.dlDir);
		profile.setPreference("browser.download.downloadDir", YQController.dlDir);
		profile.setPreference("browser.download.defaultFolder", YQController.dlDir);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "audio/mpeg");
		WebDriver driver = new FirefoxDriver(profile);
		return driver;
	}
}
