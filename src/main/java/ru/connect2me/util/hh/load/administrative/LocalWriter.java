package ru.connect2me.util.hh.load.administrative;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

/**
 * Инициализация логирования, включения Saxon xslt-парсера  
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public class LocalWriter {
  public void write(String path, String data) throws IOException, URISyntaxException {
    URL url = Thread.currentThread().getContextClassLoader().getResource(path);
    File file = new File(url.toURI().getPath());
    FileUtils.writeStringToFile(file, data, "UTF-8");
  }
}