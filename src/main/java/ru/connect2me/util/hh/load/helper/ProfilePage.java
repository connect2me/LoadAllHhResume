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
import java.util.Properties;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 * Входная точка сервиса
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class ProfilePage {
  private Properties props;
  public ProfilePage(Properties props){
    this.props = props;
  }
  public HtmlPage get(WebClient webClient) throws LoadAllHhResumeException {
    HtmlPage profilepage = null;
    try {
      webClient.setJavaScriptEnabled(false);
      webClient.setCssEnabled(false);

      HtmlPage page = webClient.getPage(props.getProperty("page"));

      List<HtmlForm> formList = page.getForms();

      HtmlForm neededForm = formList.get(1);

      HtmlTextInput user = neededForm.getInputByName("username");
      user.setValueAttribute(props.getProperty("user"));
      HtmlPasswordInput pass = neededForm.getInputByName("password");
      pass.setValueAttribute(props.getProperty("pwd"));

      HtmlSubmitInput submit = (HtmlSubmitInput) neededForm.getElementsByAttribute("input", "name", "action").get(0);
      profilepage = (HtmlPage) submit.click();
    } catch (IOException ex) {
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    } catch (FailingHttpStatusCodeException ex) {
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    } finally{
      webClient.closeAllWindows();      
    }
    return profilepage;
  }
}
