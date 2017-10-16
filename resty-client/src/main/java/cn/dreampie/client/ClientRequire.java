package cn.dreampie.client;

import java.net.HttpURLConnection;

/**
 * @author Dreampie 
 */
public class ClientRequire {

  /**
   * 重新登录的条件
   *
   * @param result s
   * @return s
   */
  public boolean relogin(ClientResult result) {
    return result.getStatus().getCode() == HttpURLConnection.HTTP_UNAUTHORIZED;
  }
}
