package fun.liutong;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: Downloader
 * @Description: 下载器
 * @Author: 刘通
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class Downloader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    //单线程下载
    public String download(String url, Site site) {
        CloseableHttpClient httpClient = getHttpClient(site);;
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpget = new HttpGet(url);
            for (Map.Entry<String, String> entry : site.getHeaders().entrySet()) {
                httpget.addHeader(entry.getKey(), entry.getValue());
            }
            httpResponse = httpClient.execute(httpget);
            logger.info("请求：" + url);
            HttpEntity entity = httpResponse.getEntity();  //获取网页内容
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (Exception e) {
            return "error";
        }finally {
            try {
                httpClient.close();
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //多线程下载
    public void downloaderPool(List<String> seatUrlist, Site site) {
        List<Thread> threads = new ArrayList<Thread>();
        for (final String oneUrl : seatUrlist) {
            final CloseableHttpClient httpClient = getHttpClient(site);;
            final CloseableHttpResponse[] httpResponse = {null};
            final HttpGet httpget = new HttpGet(oneUrl);
            for (Map.Entry<String, String> entry : site.getHeaders().entrySet()) {
                httpget.addHeader(entry.getKey(), entry.getValue());
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        httpResponse[0] = httpClient.execute(httpget);
                        logger.info("请求：" + oneUrl);
//                        Thread.sleep(1000);
                        HttpEntity entity = httpResponse[0].getEntity();  //获取网页内容
                        String result = EntityUtils.toString(entity, "UTF-8");
                        logger.info("结果：" + result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            httpClient.close();
                            httpResponse[0].close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            threads.add(thread);
        }
        for (final Thread thread : threads) {
            thread.start();
        }
    }

    //获取httpClient
    private CloseableHttpClient getHttpClient(Site site) {
        try {
            SSLContext sslcontext;
            SSLConnectionSocketFactory sslsf;
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                            return true;
                        }
                    })
                    .build();
            sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie.setDomain(site.getDomain());
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
            }
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            if (site.getUserAgent() != null) {
                httpClientBuilder.setUserAgent(site.getUserAgent());
            } else {
                httpClientBuilder.setUserAgent("");
            }
            CloseableHttpClient httpClient = httpClientBuilder
                    .setSSLSocketFactory(sslsf)
                    .setDefaultCookieStore(cookieStore)
                    .build();
            return httpClient;
        } catch (Exception e) {
            return null;
        }
    }

}
