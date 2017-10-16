package cn.dreampie.security.builder;

import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.security.AuthenticateService;
import cn.dreampie.security.SessionBuilder;

/**
 * @author Dreampie
 */
public class CookieSessionBuilder extends SessionBuilder {

  public CookieSessionBuilder(AuthenticateService authenticateService) {
    super(authenticateService);
  }

  /**
   * @param cookieName s
   * @param authenticateService s
   */
  public CookieSessionBuilder(String cookieName, AuthenticateService authenticateService) {
    super(cookieName, authenticateService);
  }

  /**
   * @param limit s
   * @param authenticateService s
   */

  public CookieSessionBuilder(int limit, AuthenticateService authenticateService) {
    super(limit, authenticateService);
  }

  /**
   * @param limit s
   * @param rememberDay s
   * @param authenticateService s
   */
  public CookieSessionBuilder(int limit, int rememberDay, AuthenticateService authenticateService) {
    super(limit, rememberDay, authenticateService);
  }

  /**
   * @param expires s
   * @param limit s
   * @param rememberDay s
   * @param authenticateService s
   */
  public CookieSessionBuilder(long expires, int limit, int rememberDay, AuthenticateService authenticateService) {
    super(expires, limit, rememberDay, authenticateService);
  }

  /**
   * @param cookieName s
   * @param expires s
   * @param limit s
   * @param rememberDay s
   * @param authenticateService s
   */
  public CookieSessionBuilder(String cookieName, long expires, int limit, int rememberDay, AuthenticateService authenticateService) {
    super(cookieName, expires, limit, rememberDay, authenticateService);
  }

  /**
   * @param request s
   * @return s
   */
  public String inputSessionKey(HttpRequest request) {
    return request.getCookiesMap().get(sessionName);
  }

  /**
   * @param response s 
   * @param sessionKey s
   * @param expires s
   */
  public void outputSessionKey(HttpResponse response, String sessionKey, int expires) {
    response.addCookie(sessionName, sessionKey, expires);
  }
}

