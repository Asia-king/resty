package cn.dreampie.route.config;


/**
 * Config.
 * 
 * Config order: configConstant(), configResource(), configPlugin(), configInterceptor(), configHandler()
 */
public class Config {

  /**
   * Config constant
   * @param constantLoader s
   */
  public void configConstant(ConstantLoader constantLoader) {
  }

  /**
   * Config resource
   * @param resourceLoader s
   */
  public void configResource(ResourceLoader resourceLoader) {
  }

  /**
   * Config plugin
   *  @param pluginLoader s
   */
  public void configPlugin(PluginLoader pluginLoader) {
  }

  /**
   * Config interceptor applied to all actions.
   * @param interceptorLoader s
   */
  public void configInterceptor(InterceptorLoader interceptorLoader) {
  }

  /**
   * Config handler
   * @param handlerLoader s
   */
  public void configHandler(HandlerLoader handlerLoader) {
  }


  /**
   * Call back after Resty start
   */
  public void afterStart() {
  }

  /**
   * Call back before Resty stop
   */
  public void beforeStop() {
  }

}