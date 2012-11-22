package ru.connect2me.util.hh.load;

import java.util.Set;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 * Это интерфейс для использования во внешних (по отношению к библиотеке) приложениях.
 *
 * @author Зайнуллин Радик
 * @version 1.0
 * @since 2012.11.18
 */
public interface HhLoad {
  public Set<String> execute() throws LoadAllHhResumeException;
}