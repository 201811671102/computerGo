package com.computerGo.ftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName FTPProperties
 * @Description TODO
 * @Author QQ163
 * @Date 2020/5/8 14:37
 **/
@Data
@Component
@PropertySource("classpath:ftp.properties")
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {
    private String userName;
    private String passWord;
    private String host;
    private Integer port;
    private Integer passiveMode = FTP.BINARY_FILE_TYPE;
    private String encoding="UTF-8";
    private int clientTimeout=120000;
    private int transferFileType=FTP.BINARY_FILE_TYPE;
    private String root;
    private int MaxTotal;
    private int MinIdel;
    private int MaxIdle;
    private int MaxWaitMillis;

}
