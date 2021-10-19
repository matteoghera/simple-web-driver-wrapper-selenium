package com.ghera.projects.webdriver;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverWrapperTest {
	private static WebDriverWrapper myWebDriverWrapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myWebDriverWrapper = new RemoteChromeWebDriverWrapper(new URL("http://localhost:4444/"), new ChromeOptions());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		myWebDriverWrapper.close();
	}


	@Test
	public void testGetWebPageTitle() {
		connectToTestedWebSite(false);
		String webPageTitle=myWebDriverWrapper.getTitle();
		assertTrue(webPageTitle.contains("A very simple webpage"));
	}
	
	@Test
	public void testGetAllWebPageElementsWhenWebPageIsNotEmpty() {
		connectToTestedWebSite(false);
		List<WebElement> currentElements=myWebDriverWrapper.getAllWebPageElements();
		/*
		 * number of < symbols is 87 
		 * number of </ symbols is 33
		 * number of html elements is: 87-33
		 */
		assertEquals(54, currentElements.size());
	}
	
	@Test
	public void testGetAllWebPageElementsWhenWebPageIsEmpty() {	
		connectToTestedWebSite(true);
		/*
		 * <html>
		 * 	<head></head>
		 * 	<body></body>
		 * </html>
		 * 
		 * There are three elements!
		 */
		List<WebElement> currentElements=myWebDriverWrapper.getAllWebPageElements();
		assertEquals(3, currentElements.size());
	}
	
	@Test
	public void testGetWebPageElementsByTypeWhenItDoesNotExist() {
		connectToTestedWebSite(true);
		List<WebElement> elements=myWebDriverWrapper.getAllWebPageElements("p");
		assertTrue(elements.isEmpty());
		
	}
	
	
	@Test
	public void testGetWebPageElementsByTypeWhenItExists() {
		connectToTestedWebSite(false);
		List<WebElement> elements=myWebDriverWrapper.getAllWebPageElements("p");
		assertTrue(!elements.isEmpty());
		
		Iterator<WebElement> elementsIT=elements.iterator();
		boolean isCorrectType=true;
		while (elementsIT.hasNext()) {
			isCorrectType=isCorrectType && elementsIT.next().getTagName().equals("p");
		}
		assertTrue(isCorrectType);
		assertEquals(12, elements.size());
	}
	
	private void connectToTestedWebSite(boolean isEmpty) {
		if(isEmpty) {
			/*
			 * <html>
			 * 	<head></head>
			 * 	<body></body>
			 * </html>
			 */
			myWebDriverWrapper.connect("about:blank");
		} else {
			myWebDriverWrapper.connect("https://web.ics.purdue.edu/~gchopra/class/public/pages/webdesign/05_simple.html");
		}
	}
}
