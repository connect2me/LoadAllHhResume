package ru.connect2me.util.hh.load.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Реализация конфигурации через обработку xml файла конфигурации
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class XMLConfiguration implements Configuration {
  private Properties properties = new Properties();

  public XMLConfiguration(InputStream src) throws LoadAllHhResumeException {
    if (src == null) throw new LoadAllHhResumeException("Не могу найти конфигурационного файла xml.");
    try {
      init(src);
    } catch(SAXException e) {
      throw new LoadAllHhResumeException("Не могу распарсить конфигурационный файл xml.");
    }catch(IOException e) {
      throw new LoadAllHhResumeException("Не могу распарсить конфигурационный файл xml.");
    }catch(ParserConfigurationException e) {
      throw new LoadAllHhResumeException("Не могу распарсить конфигурационный файл xml.");
    }
  }

  private void init(InputStream src) throws SAXException, IOException, ParserConfigurationException {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean name = false;
        String currentAttribute;
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
          if (qName.equalsIgnoreCase("parameter")) {
            name = true;
            currentAttribute = attributes.getValue("name");
          }
        }
        public void characters(char ch[], int start, int length) throws SAXException {
          if (name) {
            properties.put(currentAttribute, new String(ch, start, length));
            name = false;
          }
        }
      };
      saxParser.parse(src, handler);
  }
  @Override
  public Properties getProperties() {
    return properties;
  }
}