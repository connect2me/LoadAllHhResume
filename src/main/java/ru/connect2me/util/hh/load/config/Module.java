package ru.connect2me.util.hh.load.config;

import com.gargoylesoftware.htmlunit.WebClient;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Инициализация логирования, включения Saxon xslt-парсера  
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public abstract class Module {
  protected static ClassLoader classLoader;
  protected static Logger log;
  protected static Properties props;// свойства из config.xml, props короче чем getProperties()
  protected static WebClient webClient = new WebClient();
  public Module(Configuration config) {
    init(config);
  }
  private void init(Configuration config) {
    classLoader = Thread.currentThread().getContextClassLoader();
    props = config.getProperties();
    // Включение логирования
    DOMConfigurator.configure(Module.class.getResource("/log4j.xml"));
    log = Logger.getLogger("ru.connect2me.util.hh");
  }
  public Properties getProperties() {
    return props;
  }
}