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

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Request {

    private SSLContext sslcontext ;
    private SSLConnectionSocketFactory sslsf ;
    private CloseableHttpClient httpclient ;

    public Request(String name, String value, String doMain) {
        try {
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
            BasicClientCookie cookie = new BasicClientCookie(name, value);
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
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            System.out.println("Executing request " + httpget.getRequestLine());

            response = httpclient.execute(httpget);
            HttpEntity entity =  response.getEntity();  //获取网页内容
            String result = EntityUtils.toString(entity, "UTF-8");

//            System.out.println(result);
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
