package ru.connect2me.util.hh.load;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

  public String execute() throws LoadAllHhResumeException {
    HtmlPage autoSearch = null;
    try {
      // получение страницы профиля нашего работодателя
      HtmlPage profilePage = new ProfilePage().get(webClient);
      // получение страницы с заготовленными шаблонами автопоиска
      autoSearch = profilePage.getAnchorByHref("/employer/savedSearches.do").click();
      // разбор страницы autoSearch
      List<HtmlAnchor> searchList = (List<HtmlAnchor>) autoSearch.getByXPath("//div[@class='b-savedsearch-employer-results']/a[1]");
      // разбор полученных ссылок, получение номеров вакансий
      Set<String> set = new HashSet<String>();
      for (HtmlAnchor search : searchList) {
        HtmlPage searchPage = search.click();
        set.addAll(new HandlerSearchPage().get(searchPage));
      }
      
      Iterator it = set.iterator();
      while (it.hasNext()) {
        System.out.println(it.next());
      }
    }catch (IOException ex) {
      throw new LoadAllHhResumeException("hello");
    }

    webClient.closeAllWindows();
    return "Hello World!";
  }
}