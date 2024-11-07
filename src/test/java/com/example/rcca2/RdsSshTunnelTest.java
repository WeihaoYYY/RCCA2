package com.example.rcca2;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class RdsSshTunnelTest {

    private static Session sshSession;

    public static void main(String[] args) {
        try {
            setUpSshTunnel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUpSshTunnel() throws Exception {
        // SSH 配置
        String sshHost = "ec2-3-104-152-3.ap-southeast-2.compute.amazonaws.com"; // EC2 实例的公有 IP 地址
        String sshUser = "ec2-user"; // 登录用户名，通常为 ec2-user 或 ubuntu
//        String sshPrivateKey = "F:/ec2_key.pem"; // 私钥文件路径
        String sshPrivateKey = "F:/openssh_key.pem"; // 私钥文件路径
        int sshPort = 22;

        // RDS 配置
        String rdsEndpoint = "whdb.cru06gay4wfa.ap-southeast-2.rds.amazonaws.com"; // RDS 终端节点
        int rdsPort = 3306;
        int localPort = 3307; // 本地端口，用于转发

        // 设置日志记录
        JSch.setLogger(new MyLogger());

        // 使用 JSch 创建 SSH 会话
        JSch jsch = new JSch();
        jsch.addIdentity(sshPrivateKey);
        sshSession = jsch.getSession(sshUser, sshHost, sshPort);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 跳过主机密钥检查
        config.put("PreferredAuthentications", "publickey"); // 设置仅使用公钥认证

        sshSession.setConfig(config);

        // 尝试连接 SSH
        try {
            sshSession.connect();
        } catch (JSchException e) {
            System.err.println("连接到 SSH 时出现问题: " + e.getMessage());
            throw e;
        }

        // 设置本地端口转发到 RDS
        sshSession.setPortForwardingL(localPort, rdsEndpoint, rdsPort);
        System.out.println("隧道已建立，您可以使用 JDBC 连接到数据库: jdbc:mysql://127.0.0.1:" + localPort + "/whdb");
    }

    // 自定义日志记录器
    public static class MyLogger implements com.jcraft.jsch.Logger {
        static java.util.Hashtable<Integer, String> name = new java.util.Hashtable<>();

        static {
            name.put(DEBUG, "DEBUG: ");
            name.put(INFO, "INFO: ");
            name.put(WARN, "WARN: ");
            name.put(ERROR, "ERROR: ");
            name.put(FATAL, "FATAL: ");
        }

        @Override
        public boolean isEnabled(int level) {
            return true;
        }

        @Override
        public void log(int level, String message) {
            System.out.print(name.get(level));
            System.out.println(message);
        }
    }

    // 在测试完成后关闭 SSH 连接
    public static void tearDown() {
        if (sshSession != null && sshSession.isConnected()) {
            sshSession.disconnect();
            System.out.println("SSH 会话已断开");
        }
    }
}

