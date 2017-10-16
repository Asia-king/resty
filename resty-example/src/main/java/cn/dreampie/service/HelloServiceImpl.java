package cn.dreampie.service;

import org.springframework.stereotype.Component;

/**
 * @author Dreampie
 */
@Component
public class HelloServiceImpl implements HelloService {
  public String hello() {
    return "hello";
  }
}
