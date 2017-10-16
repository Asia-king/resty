package cn.dreampie.orm;

import cn.dreampie.log.Logger;
import cn.dreampie.orm.dialect.Dialect;
import cn.dreampie.orm.exception.TransactionException;
import cn.dreampie.orm.provider.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ConnectionAccess
 */
public class DataSourceMeta {

  private static final Logger logger = Logger.getLogger(DataSourceMeta.class);
  //不能使用static 让每个数据源都有一个connectionTL
  private final ThreadLocal<Connection> connectionTL = new ThreadLocal<Connection>();
  private final ThreadLocal<TransactionManager> transactionManagerTL = new ThreadLocal<TransactionManager>();
  private final ThreadLocal<Integer> transactionDeepTL = new ThreadLocal<Integer>();
  private DataSourceProvider dataSourceProvider;

  public DataSourceMeta(DataSourceProvider dataSourceProvider) {
    this.dataSourceProvider = dataSourceProvider;
  }

  public String getDsName() {
    return dataSourceProvider.getDsName();
  }

  DataSource getDataSource() {
    return dataSourceProvider.getDataSource();
  }

  public Dialect getDialect() {
    return dataSourceProvider.getDialect();
  }

  public boolean isShowSql() {
    return dataSourceProvider.isShowSql();
  }

  /**
   * 获取连接对象
   *
   * @return 连接对象
   * @throws SQLException s
   */
  public Connection getConnection() throws SQLException {
    Connection conn = connectionTL.get();
    if (conn != null) {
      return conn;
    }
    return getDataSource().getConnection();
  }

  /**
   * 当前连接对象
   *
   * @return connection
   */
  Connection getCurrentConnection() {
    return connectionTL.get();
  }

  /**
   * 设置当前连接对象
   *
   * @param connection connection
   */
  void setCurrentConnection(Connection connection) {
    connectionTL.set(connection);
  }

  /**
   * 移除连接对象
   */
  void rmCurrentConnection() {
    connectionTL.remove();
  }

  /**
   * 初始化事务对象
   * @param readonly s
   * @param level s
   * 
   */
  public void initTransaction(boolean readonly, int level) {
    if (transactionManagerTL.get() == null) {
      transactionManagerTL.set(new TransactionManager(this, readonly, level));
      transactionDeepTL.set(1);
    } else {
      transactionDeepTL.set(transactionDeepTL.get() + 1);
    }
  }

  /**
   * 开始事务
   *
   * @throws TransactionException s
   */ 
  public void beginTransaction() throws TransactionException {
    TransactionManager transactionManager = transactionManagerTL.get();
    //当前事务管理对象
    if (transactionManager != null && !transactionManager.isBegined()) {
      transactionManager.begin();
    }
  }

  /**
   * 提交事务
   *
   * @throws TransactionException s
   */
  public void commitTransaction() throws TransactionException {
    if (transactionDeepTL.get() == 1) {
      TransactionManager transactionManager = transactionManagerTL.get();
      if (transactionManager != null) {
        transactionManager.commit();
      }
    }
  }

  /**
   * 回滚事务
   *
   * @throws TransactionException s
   */
  public void rollbackTransaction() {
    if (transactionDeepTL.get() == 1) {
      TransactionManager transactionManager = transactionManagerTL.get();
      if (transactionManager != null) {
        transactionManager.rollback();
      }
    }
  }

  /**
   * 结束事务
   *
   * @throws TransactionException s
   */
  public void endTranasaction() {
    if (transactionDeepTL.get() == 1) {
      TransactionManager transactionManager = transactionManagerTL.get();
      if (transactionManager != null) {
        transactionManager.end();
      }
      transactionManagerTL.remove();
    } else {
      transactionDeepTL.set(transactionDeepTL.get() - 1);
    }
  }

  /**
   * 关闭数据源
   */
  public final void close() {
    dataSourceProvider.close();
  }

  /**
   * 关ResultSet闭结果级对象
   *
   * @param rs   ResultSet
   * @param st   Statement
   * @param conn Connection
   */
  public final void close(ResultSet rs, Statement st, Connection conn) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        logger.warn("Could not close resultSet!", e);
      }
    }
    //关闭连接
    close(st, conn);
  }

  /**
   * 关闭Statement
   *
   * @param st   Statement
   * @param conn Connection
   */
  public final void close(Statement st, Connection conn) {
    if (st != null) {
      try {
        st.close();
      } catch (SQLException e) {
        logger.warn("Could not close statement!", e);
      }
    }
    //关闭连接
    close(conn);
  }

  /**
   * 关闭Connection
   *
   * @param conn Connection
   */
  public final void close(Connection conn) {
    if (connectionTL.get() == null) {   // in transaction if conn in threadlocal
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e) {
          logger.warn("Could not close connection!", e);
        }
    }
  }
}
