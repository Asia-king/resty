package cn.dreampie.quartz;

import cn.dreampie.orm.DataSourceMeta;
import cn.dreampie.orm.Metadata;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dreampie
 */
public class QuartzConnectionProvider implements ConnectionProvider {
  private DataSourceMeta dataSourceMeta;

  public Connection getConnection() throws SQLException {
    return dataSourceMeta.getConnection();
  }

  public void shutdown() throws SQLException {
    if (QuartzPlugin.isDsAlone()) {
      dataSourceMeta.close();
    }
  }

  public void initialize() throws SQLException {
    dataSourceMeta = Metadata.getDataSourceMeta(QuartzPlugin.getDsName());
  }
}
