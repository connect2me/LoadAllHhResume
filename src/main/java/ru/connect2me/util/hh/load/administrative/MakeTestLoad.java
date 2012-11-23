package ru.connect2me.util.hh.load.administrative;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import ru.connect2me.util.hh.load.Agent;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;
import ru.connect2me.util.hh.load.helper.ProfilePage;

/**
 *
 * @author r.zaynullin
 */
public class MakeTestLoad {

  public static void main(String[] args) throws LoadAllHhResumeException, IOException {
    WebClient webClient = new WebClient();
    Properties props = new Properties();
    props.put("page", "http://hh.ru/logon.do");
    props.put("user", "a8019111@yandex.ru");
    props.put("pwd", "YDz5iM");

    Set<String> set = new Agent(webClient, props).execute();
    Iterator<String> iterator = set.iterator();
    
    HtmlPage page = new ProfilePage(props).get(webClient);
    
    while (iterator.hasNext()) {
      HtmlPage p = webClient.getPage("http://hh.ru/resume/" + iterator.next());
      System.out.println(p.asXml());
    }
  }
}
