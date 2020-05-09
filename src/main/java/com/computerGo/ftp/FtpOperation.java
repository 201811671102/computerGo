package com.computerGo.ftp;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;


/**
 * 类说明：文件上传下载工具类
 *
 */
@Component
@Log4j2
public class FtpOperation {

    //默认文件类型为二进制（图片）
    private static int filetype = FTP.BINARY_FILE_TYPE;

    @Autowired
    FtpConfig config;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private FtpPool pool;

    /**
     *
     * 功能：上传文件附件到文件服务器
     * @param buffIn:上传文件流
     * @param fileName：保存文件名称
     * @return
     * @throws IOException
     */
    public  boolean uploadToFtp(InputStream buffIn, String fileName, String filepath){
        FTPClient ftpClient = pool.getFTPClient();
        // 上传文件
        try {
            setFileType(filetype,ftpClient);
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply))
            {
                ftpClient.disconnect();
                return false;
            }
            ftpClient.enterLocalPassiveMode();
            if(!ftpClient.changeWorkingDirectory(filepath)){
                String[] dirs = filepath.split("/");
                String tempPath = "";
                for (String dir : dirs){
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/"+dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)){
                        if (!ftpClient.makeDirectory(tempPath)){
                            return false;
                        }else{
                            ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }

            // 上传文件到ftp
            // 输出操作结果信息
            fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
            if (ftpClient.storeFile(fileName, buffIn)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            log.error("ERR : upload file  to ftp : failed! ", e);
        } finally {
            try {
                if (buffIn != null) {
                    buffIn.close();
                }
                pool.returnFTPClient(ftpClient);
            } catch (Exception e) {
                log.error("ftp关闭输入流时失败！", e);
            }
        }
        return true;
    }


    /**
     *
     * 功能：根据文件名称，下载文件流
     * @param filename
     * @return
     * @throws IOException
     */
    public  InputStream  downloadFile(String filename,String filepath){
        InputStream in=null;
        FTPClient ftpClient = pool.getFTPClient();
        try {
            ftpClient.enterLocalPassiveMode();
            // 设置传输二进制文件
            setFileType(filetype,ftpClient);
            if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
            {
                return null;
            }
            if (!ftpClient.changeWorkingDirectory(filepath)){
                return null;
            }
            // ftp文件获取文件
            filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
            in=ftpClient.retrieveFileStream(filename);
            return in;
        }catch (Exception e) {
            log.error("ERR : upload file "+ filename+ " from ftp : failed!", e);
            return null;
        }finally {
            pool.returnFTPClient(ftpClient);
        }
    }


    public  boolean delectFile(String filename,String filepath){
        FTPClient ftpClient = pool.getFTPClient();
        try {
            filename = new String(filename.getBytes("utf-8"),"iso-8859-1");
            ftpClient.changeWorkingDirectory(filepath);
            if (!ftpClient.deleteFile(filename)){
                return false;
            }
            return true;
        }catch (Exception e) {
            log.error("ERR : delete file "+ filename+ " from ftp : failed!", e);
            return false;
        }finally {
            pool.returnFTPClient(ftpClient);
        }
    }
    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType
     *            --BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public  void setFileType(int fileType,FTPClient ftpClient) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            log.error("ftp设置传输文件的类型时失败！", e);
        }finally {

        }
    }
/*
    *//**
     *
     * 功能：关闭连接
     *//*
    public void closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            log.error("ftp连接关闭失败！", e);
        }
    }

    *//**
     * 连接到ftp服务器
     *//*
    public void connectToServer() throws FTPConnectionClosedException,Exception {
        if (!ftpClient.isConnected()) {
            int reply;
            try {
                ftpClient=new FTPClient();
                ftpClient.connect(ip,port);
                ftpClient.login(userName,passWord);
                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log.info("connectToServer FTP server refused connection.");
                }
            }catch(FTPConnectionClosedException ex){
                log.error("服务器:IP："+ip+"没有连接数！there are too many connected users,please try later", ex);
                throw ex;
            }catch (Exception e) {
                log.error("登录ftp服务器【"+ip+"】失败", e);
                throw e;
            }
        }
    }*/
}
