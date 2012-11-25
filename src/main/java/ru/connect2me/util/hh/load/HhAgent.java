package ru.connect2me.util.hh.load;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import ru.connect2me.util.hh.load.config.*;
import ru.connect2me.util.hh.load.helper.*;

/**
 * Входная точка сервиса
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class HhAgent extends Module implements HhLoad {
  private Properties props;
  private WebClient webClient;

  public HhAgent(WebClient webClient, Properties props) throws LoadAllHhResumeException {
    super(new XMLConfiguration(HhAgent.class.getResourceAsStream("/config-LoadAllHhResume.xml")));
    this.props = props;
    this.webClient = webClient;
  }

  public Set<String> execute() throws LoadAllHhResumeException {
    Set<String> set = new HashSet<String>();
    try {
      // получение страницы профиля нашего работодателя
      HtmlPage profilePage = new ProfilePage(props).get(webClient);
      // получение страницы с заготовленными шаблонами автопоиска
      HtmlPage autoSearch = profilePage.getAnchorByHref("/employer/savedSearches.do").click();
      // разбор страницы autoSearch
      List<HtmlAnchor> searchList = (List<HtmlAnchor>) autoSearch.getByXPath("//div[@class='b-savedsearch-employer-results']/a[1]");
      // разбор полученных ссылок, получение номеров вакансий
      for (HtmlAnchor search : searchList) {
        HtmlPage searchPage = search.click();
        set.addAll(new HandlerSearchPage().get(searchPage));
      }
    } catch (IOException ex) {
      webClient.closeAllWindows();
      throw new LoadAllHhResumeException("Agent is down. " + ex.getMessage());
    } 
    return set;
  }
}