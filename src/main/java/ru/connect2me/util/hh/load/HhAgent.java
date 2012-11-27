package ru.connect2me.util.hh.load;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.connect2me.util.hh.load.config.*;
import ru.connect2me.util.hh.load.helper.*;

/**
 * Входная точка сервиса.
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
  // Выдает набор id резюме вытащенных из сохраненных запросов
  public Set<String> execute() throws LoadAllHhResumeException {
    Logger logger = LoggerFactory.getLogger(HhAgent.class);
    Set<String> set = new HashSet<String>();
    try {
      // получение страницы профиля нашего работодателя
      HtmlPage profilePage = new ProfilePage(props).get(webClient);
      String profilePageEncoding = profilePage.getPageEncoding();
      
      boolean isFind = profilePage.asXml().contains("клиент 774702");
      
      // получение страницы с заготовленными шаблонами автопоиска
      HtmlPage autoSearchPage = profilePage.getAnchorByHref("/employer/savedSearches.do").click();
      String autoSearchEncoding = profilePage.getPageEncoding();
      URL autoSearchURL = autoSearchPage.getUrl();
      String str = autoSearchURL.getPath();

      HtmlPage savedSearches = webClient.getPage("http://hh.ru/employer/savedSearches.do");

      // разбор страницы autoSearch
      List<HtmlAnchor> searchList = (List<HtmlAnchor>) savedSearches.getByXPath("//div[@class='b-savedsearch-employer-results']/a[1]");
      // разбор полученных ссылок, получение номеров вакансий
      for (HtmlAnchor anchor : searchList) {
        // HtmlAnchor[<a href="/resumesearch/result?actionSearch=actionSearch&amp;areaId=113&amp;p.salaryFrom=0&amp;p.salaryTo=0&amp;p.currencyCode=RUR&amp;p.gender=-1&amp;p.includeNoGender=true&amp;p.ageFrom=0&amp;p.ageTo=0&amp;p.includeNoAge=true&amp;p.educationId=0&amp;p.searchPeriod=30&amp;p.orderByMode=2&amp;p.relocationSearch=true&amp;p.includeNoSalary=true&amp;p.itemsOnPage=20&amp;p.keyword1=%5B%7B%22w%22%3A%22developer%22%2C%22l%22%3A%22normal%22%2C%22p%22%3A%5B%22full_text%22%5D%7D%5D%3D%3D%3D%21%3D%3D%3D">]
        String anchorStr = anchor.toString();
        Matcher m = Pattern.compile("(/resumesearch/.+)(?=\">\\])").matcher(anchorStr);
        String link = null;
        if (m.find()) link = m.group(); 
        else throw new LoadAllHhResumeException("Не смогли получить ссылку на страницу с набором резюме из сохранного запроса.");
        HtmlPage searchPage = webClient.getPage("http://hh.ru" + link); // anchor.click();
        set.addAll(new HandlerSearchPage().get(searchPage));
      }
    } catch (IOException ex) {
      webClient.closeAllWindows();
      logger.error("Произошла ошибка - Agent is down. " + ex.getMessage());
      throw new LoadAllHhResumeException("Agent is down. " + ex.getMessage());
    } 
    logger.info("Набрали " + set.size() + " ссылок на новые резюме.");
    Iterator<String> iterator = set.iterator();
    while(iterator.hasNext()){
      logger.info(iterator.next());
    }
    return set;
  }
}