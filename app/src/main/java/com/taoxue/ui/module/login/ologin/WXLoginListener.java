package com.taoxue.ui.module.login.ologin;

import java.util.Map;
import java.util.logging.StreamHandler;

/**
 * Created by User on 2017/9/11.
 */

public interface WXLoginListener {
  void onWXInfo(Map<String,String>map);
}
