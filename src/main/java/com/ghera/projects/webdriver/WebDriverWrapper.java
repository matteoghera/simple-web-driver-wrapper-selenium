package com.ghera.projects.webdriver;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WebDriverWrapper {

	public void connect(String webSiteURL);
	public void close();
	public String getTitle();
	public List<WebElement> getAllWebPageElements();
	public List<WebElement> getAllWebPageElements(String tagName);

}
