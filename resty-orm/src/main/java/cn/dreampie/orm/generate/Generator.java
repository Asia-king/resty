package cn.dreampie.orm.generate;

import java.io.Serializable;

/**
 * @author Dreampie
 */
public interface Generator extends Serializable {
  /**
   * 非自动生成主键的产生策略
   *
   * @return object
   */
  public Object generateKey();
}
