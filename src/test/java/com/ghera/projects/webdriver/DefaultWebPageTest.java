package com.ghera.projects.webdriver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

public class DefaultWebPageTest {
	private static WebDriverWrapper myWebDriverWrapper;
	private static String defaultWebPageURL="https://web.ics.purdue.edu/~gchopra/class/public/pages/webdesign/05_simple.html";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myWebDriverWrapper = new RemoteChromeWebDriverWrapper(new URL("http://localhost:4444/"), new ChromeOptions());
		myWebDriverWrapper.connect(defaultWebPageURL);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		myWebDriverWrapper.close();
	}

	@Test
	public void testHead() {
		String webPageTitle=myWebDriverWrapper.getTitle();
		assertTrue(webPageTitle.contains("A very simple webpage"));
	}
	
	@Test
	public void testIfTitlesAreVisible() {
		Map<String, Integer> titleOccurrence=new HashMap<>();
		titleOccurrence.put("h1", 1);
		titleOccurrence.put("h2", 4);
		titleOccurrence.put("h6", 1);
		
		for(String key: titleOccurrence.keySet()) {
			List<WebElement> titles=myWebDriverWrapper.getAllWebPageElements(key);
			assertEquals((int) titleOccurrence.get(key), (int) titles.size());
			
		}
	}
	
	@Test
	public void testIfWebPageLinksWork() throws IOException {
		List<WebElement> links=myWebDriverWrapper.getAllWebPageElements("a");
		assertEquals(2, links.size());
		
		Iterator<WebElement> linksIT=links.iterator();
		int index=0;
		while(linksIT.hasNext()) {
			String currentURL=linksIT.next().getAttribute("href");
			int responseCode=validateURI(currentURL, "HEAD");
			if(index==0) {
				assertTrue(responseCode<400);
			} else if(index==1) {
				assertTrue(responseCode>=400);
			}
			index++;
		}
	}
	
	@Test
	public void testIfImageIsBroken() throws MalformedURLException, IOException {
		List<WebElement> imagesTags=myWebDriverWrapper.getAllWebPageElements("img");
		assertEquals(2, imagesTags.size());
		
		Iterator<WebElement> imagesTagsIT=imagesTags.iterator();
		while(imagesTagsIT.hasNext()) {
			String imageURI=imagesTagsIT.next().getAttribute("src");
			int responseCode = validateURI(imageURI, "GET");
			assertTrue(responseCode<400);
		}
	}

	private int validateURI(String imageURI, String requestMethod) throws IOException, MalformedURLException, ProtocolException {
		HttpURLConnection huc = (HttpURLConnection)(new URL(imageURI).openConnection());
		huc.setRequestMethod(requestMethod);
		huc.connect();
		return huc.getResponseCode();
	}

}
