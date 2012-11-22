package ru.connect2me.util.hh.load.administrative;

import java.util.Properties;
import ru.connect2me.util.hh.load.Agent;
import ru.connect2me.util.hh.load.config.LoadAllHhResumeException;

/**
 *
 * @author r.zaynullin
 */
public class MakeTestLoad {
  public static void main(String[] args) throws LoadAllHhResumeException{
    Properties props = new Properties();
    props.put("page", "http://hh.ru/logon.do");
    props.put("user", "a8019111@yandex.ru");
    props.put("pwd", "YDz5iM");
    
    new Agent(props).execute();
  }
}
