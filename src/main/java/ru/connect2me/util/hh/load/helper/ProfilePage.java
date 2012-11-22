package ru.connect2me.util.hh.load.helper;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.util.List;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 * Входная точка сервиса
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class ProfilePage {
  public HtmlPage get(WebClient webClient) throws LoadAllHhResumeException {
    HtmlPage profilepage = null;
    try {
      webClient.setJavaScriptEnabled(false);
      webClient.setCssEnabled(false);

      HtmlPage page = webClient.getPage("http://hh.ru/logon.do");

      List<HtmlForm> formList = page.getForms();

      HtmlForm neededForm = formList.get(1);

      HtmlTextInput user = neededForm.getInputByName("username");
      user.setValueAttribute("a8019111@yandex.ru");
      HtmlPasswordInput pass = neededForm.getInputByName("password");
      pass.setValueAttribute("YDz5iM");

      HtmlSubmitInput submit = (HtmlSubmitInput) neededForm.getElementsByAttribute("input", "name", "action").get(0);
      profilepage = (HtmlPage) submit.click();
      //System.out.println(profilepage.asXml());
      
    } catch (IOException ex) {
      webClient.closeAllWindows();
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    } catch (FailingHttpStatusCodeException ex) {
      webClient.closeAllWindows();
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    } 
    return profilepage;
  }
}
