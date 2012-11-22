package ru.connect2me.util.hh.load.administrative;

import ru.connect2me.util.hh.load.Agent;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 *
 * @author r.zaynullin
 */
public class MakeTestLoad {
  public static void main(String[] args) throws LoadAllHhResumeException{
    new Agent().execute();
  }
}
