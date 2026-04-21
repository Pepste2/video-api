/*
 * Decompiled with CFR 0.152.
 */
package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

public class Util {
    private static CloseableHttpAsyncClient asyncClient;

    public static Map<String, Object> getStringToMap(String str) {
        JSONObject parseObject = JSONArray.parseObject(str);
        return parseObject;
    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> querys) {
        HttpPost post = new HttpPost();
        try {
            post.setURI(new URI(url));
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25", e);
        }
        Set<Map.Entry<String, String>> entries = null;
        if (headers != null && !headers.isEmpty()) {
            entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (querys != null) {
            UrlEncodedFormEntity urlEncodedFormEntity;
            ArrayList<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
            for (String key : querys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, querys.get(key)));
            }
            Object var6_12 = null;
            try {
                urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25", e);
            }
            urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            post.setEntity(urlEncodedFormEntity);
        }
        try {
            asyncClient.start();
            HttpResponse response = asyncClient.execute(post, null).get();
            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25" + response);
            }
            HttpEntity httpEntity = response.getEntity();
            String res = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            return res;
        }
        catch (IOException e) {
            throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25", e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25", e);
        }
        catch (ExecutionException e) {
            throw new RuntimeException("\u8bf7\u6c42\u5931\u8d25", e);
        }
    }

    static {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).setSocketTimeout(50000).setConnectionRequestTimeout(10).build();
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).setSoKeepAlive(true).build();
        DefaultConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        }
        catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connManager.setMaxTotal(10);
        connManager.setDefaultMaxPerRoute(10);
        asyncClient = HttpAsyncClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig).build();
    }
}
