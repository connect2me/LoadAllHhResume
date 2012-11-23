package ru.connect2me.util.hh.load;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AgentTest {

  public AgentTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

//  @Test
//  public void submittingForm() throws Exception {
//    final WebClient webClient = new WebClient();
//
//    // Get the first page
//    final HtmlPage page1 = webClient.getPage("http://some_url");
//
//    // Get the form that we are dealing with and within that form, 
//    // find the submit button and the field that we want to change.
//    final HtmlForm form = page1.getFormByName("myform");
//
//    final HtmlSubmitInput button = form.getInputByName("submitbutton");
//    final HtmlTextInput textField = form.getInputByName("userid");
//
//    // Change the value of the text field
//    textField.setValueAttribute("root");
//
//    // Now submit the form by clicking the button and get back the second page.
//    final HtmlPage page2 = button.click();
//
//    webClient.closeAllWindows();
//  }

//  @Test
//  public void homePage_proxy() throws Exception {
//    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10, "http://myproxyserver", myProxyPort);
//
//    //set proxy username and password 
//    final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
//    credentialsProvider.addCredentials("username", "password");
//
//    final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
//    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
//
//    webClient.closeAllWindows();
//  }
  @Test
  public void xpath() throws Exception {
    final WebClient webClient = new WebClient();
    final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");

    //get list of all divs
    final List<?> divs = page.getByXPath("//div");

    //get div which has a 'name' attribute of 'John'
    // final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@name='John']").get(0);

    webClient.closeAllWindows();
  }

  @Test
  public void homePage_Firefox() throws Exception {
    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);
    final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

    webClient.closeAllWindows();
  }

  @Test
  public void homePage() throws Exception {
    final WebClient webClient = new WebClient();
    final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

    final String pageAsXml = page.asXml();
    Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

    final String pageAsText = page.asText();
    Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

    webClient.closeAllWindows();
  }
}