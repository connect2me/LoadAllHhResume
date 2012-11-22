package ru.connect2me.util.hh.load;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import ru.connect2me.util.hh.load.administrative.LocalWriter;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;
import ru.connect2me.util.hh.load.config.Module;
import ru.connect2me.util.hh.load.config.XMLConfiguration;
import ru.connect2me.util.hh.load.helper.HandlerSearchPage;
import ru.connect2me.util.hh.load.helper.ProfilePage;

/**
 * Входная точка сервиса
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class Agent extends Module implements HhLoad {

  public Agent() throws LoadAllHhResumeException {
    super(new XMLConfiguration(Agent.class.getResourceAsStream("/config-LoadAllHhResume.xml")));
  }

  public String execute(String html) throws LoadAllHhResumeException {
    HtmlPage autoSearch = null;
    try {
      // получение страницы профиля нашего работодателя
      HtmlPage profilePage = new ProfilePage().get(webClient);
      // запись этой страницы для просмотра
      new LocalWriter().write("test/profilePage.xhtml", profilePage.asXml());
      // получение страницы с заготовленными шаблонами автопоиска
      autoSearch = profilePage.getAnchorByHref("/employer/savedSearches.do").click();
      // запись этой страницы для просмотра
      new LocalWriter().write("test/autoSearch.xhtml", autoSearch.asXml());
      // разбор страницы autoSearch
      List<HtmlAnchor> searchList = (List<HtmlAnchor>) autoSearch.getByXPath("//div[@class='b-savedsearch-employer-results']/a[1]");
      // разбор полученных ссылок
      Set<String> set = new HashSet<String>();
      for (HtmlAnchor search : searchList) {
        HtmlPage searchPage = search.click();
        set.addAll(new HandlerSearchPage().get(searchPage));
      }
      Iterator it = set.iterator();
      while (it.hasNext()) {
        System.out.println(it.next());
      }
    } catch (URISyntaxException ex) {
      throw new LoadAllHhResumeException("hello");
    } catch (IOException ex) {
      throw new LoadAllHhResumeException("hello");
    }

    webClient.closeAllWindows();
    return "Hello World!";
  }
}