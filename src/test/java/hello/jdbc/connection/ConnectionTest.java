package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        Connection conn1 = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        Connection conn2 = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        log.info("connection={}, class={}", conn1, conn1.getClass());
        log.info("connection={}, class={}", conn2, conn2.getClass());
        Assertions.assertThat(conn1).isNotEqualTo(conn2);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        userDataSource(dataSource);
        Thread.sleep(1000);
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        DataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        userDataSource(dataSource);
    }

    private void userDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }
}
