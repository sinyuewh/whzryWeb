package com.jsj.webapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jinshouji on 2019/4/24.
 */
@Component
public class ServerConfig implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    private int serverPort;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event)
    {
        this.serverPort = event.getEmbeddedServletContainer().getPort();
    }

    /**
     * 得到服务器的端口
     * @return
     */
    public int getPort()
    {
        return this.serverPort;
    }

    /**
     * 得到服务器的ip
     * @return
     */
    public String getServer()
    {
        String host=null;
        try
        {
            //host = InetAddress.getLocalHost().getHostAddress();
            host=getOuterNetIp();
        }
        catch (Exception e) {
            logger.error("get server host Exception e:", e);
        }
        return  host;
    }



    private static final String QUERY_ADDRESS = "http://www.icanhazip.com";


    /**
     * 获取外网ip
     */
    public static String getOuterNetIp() {
        String result = "";
        URLConnection connection;
        BufferedReader in = null;
        try {
            URL url = new URL(QUERY_ADDRESS);
            connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "KeepAlive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }
}
