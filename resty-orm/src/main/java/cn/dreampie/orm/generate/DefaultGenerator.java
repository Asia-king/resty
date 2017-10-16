package cn.dreampie.orm.generate;

import java.util.UUID;

/**
 * @author Dreampie
 */
public class DefaultGenerator implements Generator {

  /**
   * 非自动生成主键的产生策略 UUID
   * 8-4-4-4-12 格式
   *
   * @return object
   */
  public Object generateKey() {
    return UUID.randomUUID().toString();
  }

}
