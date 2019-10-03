import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
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
import java.util.Map;

public class Request {

    private String name;
    private SSLContext sslcontext ;
    private SSLConnectionSocketFactory sslsf ;
    private CloseableHttpClient httpclient ;
    private Logger logger = LogManager.getLogger("test");

    public Request(String name ,String cookieName, String value, String doMain) {
        try {
            this.name = name;
            logger = LogManager.getLogger(name);
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustStrategy()
                    {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                        {
                            return true;
                        }
                    })
                    .build();
            sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie(cookieName, value);
            cookie.setDomain(doMain);
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
            httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setDefaultCookieStore(cookieStore)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public String sentHttps(String url, Map<String,String> header) {
        HttpGet httpget;
        CloseableHttpResponse response = null;
        try {
            httpget = new HttpGet(url);
            for(Map.Entry<String, String> entry : header.entrySet()){
                httpget.addHeader(entry.getKey(),entry.getValue());
            }
            logger.info("执行请求" + httpget.getRequestLine());
            response = httpclient.execute(httpget);
            HttpEntity entity =  response.getEntity();  //获取网页内容
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
}
