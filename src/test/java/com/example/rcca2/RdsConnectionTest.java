package com.example.rcca2;

import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RdsConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testRdsConnection() throws SQLException, JSchException {
        // 1. 配置SSH隧道连接
        String sshHost = "ec2-3-104-152-3.ap-southeast-2.compute.amazonaws.com";
        int sshPort = 22;
        String sshUser = "ec2-user";
        String sshPrivateKey = "F:\\ec2_key.pem";

        String rdsHost = "whdb.cru06gay4wfa.ap-southeast-2.rds.amazonaws.com";
        int rdsPort = 3306;
        String rdsDatabase = "rcca";
        String rdsUser = "root";
        String rdsPassword = "Yue02086329803.";

        Session sshSession = createSshSession(sshHost, sshPort, sshUser, sshPrivateKey);
        sshSession.connect();

        // 2. 通过SSH隧道连接RDS
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + rdsHost + ":" + rdsPort + "/" + rdsDatabase,
                rdsUser,
                rdsPassword
        );

        // 3. 测试RDS连接
        assertNotNull(connection, "RDS connection should not be null");
        assertNotNull(jdbcTemplate, "JdbcTemplate should not be null");

        // 4. 关闭SSH会话
        sshSession.disconnect();
    }

    private Session createSshSession(String host, int port, String user, String privateKey) {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(privateKey);
            Session session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            return session;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSH session", e);
        }
    }
}
