package com.ghera.projects.webdriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteChromeWebDriverWrapper implements WebDriverWrapper {
	private WebDriver webDriver;

	public RemoteChromeWebDriverWrapper(URL remoteSeleniumURL, ChromeOptions chromeOptions)
			throws MalformedURLException {
		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
		webDriver = new RemoteWebDriver(new URL("http://localhost:4444/"), new ChromeOptions());
		
	}

	@Override
	public void connect(String webSiteURL) {
		webDriver.get(webSiteURL);
		
	}
	
	@Override
	public void close() {
		webDriver.quit();
		
	}

	@Override
	public String getTitle() {
		return webDriver.getTitle();
	}

	@Override
	public List<WebElement> getAllWebPageElements() {
		return webDriver.findElements(By.xpath("//*"));
	}

	@Override
	public List<WebElement> getAllWebPageElements(String tagName) {
		return webDriver.findElements(By.tagName(tagName));
	}
	
}
