package ru.connect2me.util.hh.load.helper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.connect2me.util.hh.load.administrative.LocalWriter;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 * Обработка страницы "поиск по запросу"
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class HandlerSearchPage {
  public List<String> get(HtmlPage searchPage) throws LoadAllHhResumeException {
    try {
      new LocalWriter().write("test/searchPage.xhtml", searchPage.asXml());
          //        
          List<HtmlTableRow> rows = (List<HtmlTableRow>) searchPage.getByXPath("//tr[@class='output__item HH-Employer-ResumeFolders-Resume' or @class='output__item HH-Employer-ResumeFolders-Resume output__item_visited']");
          int i = 1;
          for (HtmlTableRow row : rows) {
            String trStr = row.asXml();
            new LocalWriter().write("test/row" + i++ + ".xml", trStr);
            // поиск ссылок резюме - на одной странице их может быть несколько (сколько у пользователя резюме)
            String exp = "href=\"\\/resume\\/([^\"\\?]+)[^\"\\?]*\"";
            Matcher m = Pattern.compile(exp, Pattern.DOTALL).matcher(trStr);
            while (m.find()) {
              System.out.println(m.group(1));
            }
          }
      return null;
    } catch (IOException ex) {
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    } catch (URISyntaxException ex) {
      throw new LoadAllHhResumeException("Не удалось получить доступ к нашей странице работодателя." + ex.getMessage());
    }
  }
  
}
