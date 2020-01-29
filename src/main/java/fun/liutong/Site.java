package fun.liutong;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: Site
 * @Description: 爬虫页面配置
 * @Author: 刘通
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class Site {
    private String domain = Config.DOMAIN;

    private String userAgent = Config.UserAgent;

    private Map<String, String> cookies = new LinkedHashMap<String, String>();

    private Map<String, String> headers = new HashMap<String, String>();

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
