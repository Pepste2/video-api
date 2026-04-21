package com.hikvision.artemis.sdk.util;

import com.hikvision.artemis.sdk.Response;
import com.hikvision.artemis.sdk.constant.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

    public static Response httpGet(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("GET", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        Response r = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
                .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
                .build();
            HttpGet get = new HttpGet(HttpUtil.initUrl(host, path, querys));
            get.setConfig(requestConfig);
            for (Map.Entry<String, String> e : headers.entrySet()) {
                get.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
            }
            HttpResponse rp = httpClient.execute(get);
            r = HttpUtil.convert(rp);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return r;
    }

    public static Response httpImgGet(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("GET", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        Response r = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
                .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
                .build();
            HttpGet get = new HttpGet(HttpUtil.initUrl(host, path, querys));
            get.setConfig(requestConfig);
            for (Map.Entry<String, String> e : headers.entrySet()) {
                get.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
            }
            HttpResponse rp = httpClient.execute(get);
            r = HttpUtil.convertImg(rp);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return r;
    }

    public static Response httpPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, Map<String, String> bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, bodys, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPost post = new HttpPost(HttpUtil.initUrl(host, path, querys));
        post.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        UrlEncodedFormEntity formEntity = HttpUtil.buildFormEntity(bodys);
        if (formEntity != null) {
            post.setEntity(formEntity);
        }
        Response response = HttpUtil.convert(httpClient.execute(post));
        httpClient.close();
        return response;
    }

    public static Response httpImgPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, Map<String, String> bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, bodys, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPost post = new HttpPost(HttpUtil.initUrl(host, path, querys));
        post.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        UrlEncodedFormEntity formEntity = HttpUtil.buildFormEntity(bodys);
        if (formEntity != null) {
            post.setEntity(formEntity);
        }
        Response response = HttpUtil.convertImg(httpClient.execute(post));
        httpClient.close();
        return response;
    }

    public static Response httpPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        String contentType = headers.get("Content-Type");
        if ("application/x-www-form-urlencoded;charset=UTF-8".equals(contentType)) {
            Map<String, String> paramMap = HttpUtil.strToMap(body);
            String modelDatas = paramMap.get("modelDatas");
            if (StringUtils.isNotBlank(modelDatas)) {
                paramMap.put("modelDatas", URLDecoder.decode(modelDatas));
            }
            headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, paramMap, signHeaderPrefixList, appKey, appSecret);
        } else {
            headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        }
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPost post = new HttpPost(HttpUtil.initUrl(host, path, querys));
        post.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        if (StringUtils.isNotBlank(body)) {
            post.setEntity(new StringEntity(body, "UTF-8"));
        }
        Response response = HttpUtil.convert(httpClient.execute(post));
        httpClient.close();
        return response;
    }

    public static Response httpImgPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        String contentType = headers.get("Content-Type");
        if ("application/x-www-form-urlencoded;charset=UTF-8".equals(contentType)) {
            Map<String, String> paramMap = HttpUtil.strToMap(body);
            String modelDatas = paramMap.get("modelDatas");
            if (StringUtils.isNotBlank(modelDatas)) {
                paramMap.put("modelDatas", URLDecoder.decode(modelDatas));
            }
            headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, paramMap, signHeaderPrefixList, appKey, appSecret);
        } else {
            headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        }
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPost post = new HttpPost(HttpUtil.initUrl(host, path, querys));
        post.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        if (StringUtils.isNotBlank(body)) {
            post.setEntity(new StringEntity(body, "UTF-8"));
        }
        Response response = HttpUtil.convertImg(httpClient.execute(post));
        httpClient.close();
        return response;
    }

    public static Response httpPost(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, byte[] bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("POST", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPost post = new HttpPost(HttpUtil.initUrl(host, path, querys));
        post.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            post.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        if (bodys != null) {
            post.setEntity(new ByteArrayEntity(bodys));
        }
        Response response = HttpUtil.convert(httpClient.execute(post));
        httpClient.close();
        return response;
    }

    public static Response httpPut(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, String body, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("PUT", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPut put = new HttpPut(HttpUtil.initUrl(host, path, querys));
        put.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            put.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        if (StringUtils.isNotBlank(body)) {
            put.setEntity(new StringEntity(body, "UTF-8"));
        }
        Response response = HttpUtil.convert(httpClient.execute(put));
        httpClient.close();
        return response;
    }

    public static Response httpPut(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, byte[] bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("PUT", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpPut put = new HttpPut(HttpUtil.initUrl(host, path, querys));
        put.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            put.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        if (bodys != null) {
            put.setEntity(new ByteArrayEntity(bodys));
        }
        Response response = HttpUtil.convert(httpClient.execute(put));
        httpClient.close();
        return response;
    }

    public static Response httpDelete(String host, String path, int connectTimeout, Map<String, String> headers, Map<String, String> querys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws Exception {
        headers = HttpUtil.initialBasicHeader("DELETE", path, headers, querys, null, signHeaderPrefixList, appKey, appSecret);
        CloseableHttpClient httpClient = HttpUtil.wrapClient(host);
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(HttpUtil.getTimeout(connectTimeout))
            .setSocketTimeout(HttpUtil.getTimeout(connectTimeout))
            .build();
        HttpDelete delete = new HttpDelete(HttpUtil.initUrl(host, path, querys));
        delete.setConfig(requestConfig);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            delete.addHeader(e.getKey(), MessageDigestUtil.utf8ToIso88591(e.getValue()));
        }
        Response response = HttpUtil.convert(httpClient.execute(delete));
        httpClient.close();
        return response;
    }

    private static UrlEncodedFormEntity buildFormEntity(Map<String, String> formParam) throws UnsupportedEncodingException {
        if (formParam != null) {
            ArrayList<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
            for (String key : formParam.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, formParam.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            formEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            return formEntity;
        }
        return null;
    }

    public static String initUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (StringUtils.isBlank(query.getKey())) continue;
                sbQuery.append(query.getKey());
                if (StringUtils.isBlank(query.getValue())) continue;
                sbQuery.append("=");
                sbQuery.append(URLEncoder.encode(query.getValue(), "UTF-8"));
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }

    private static Map<String, String> initialBasicHeader(String method, String path, Map<String, String> headers, Map<String, String> querys, Map<String, String> bodys, List<String> signHeaderPrefixList, String appKey, String appSecret) throws MalformedURLException {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put("x-ca-timestamp", String.valueOf(new Date().getTime()));
        headers.put("x-ca-nonce", UUID.randomUUID().toString());
        headers.put("x-ca-key", appKey);
        headers.put("x-ca-signature", SignUtil.sign(appSecret, method, path, headers, querys, bodys, signHeaderPrefixList));
        return headers;
    }

    private static int getTimeout(int timeout) {
        if (timeout == 0) {
            return Constants.DEFAULT_TIMEOUT;
        }
        return timeout;
    }

    private static Response convert(HttpResponse response) throws IOException {
        Response res = new Response();
        if (null != response) {
            res.setStatusCode(response.getStatusLine().getStatusCode());
            for (Header header : response.getAllHeaders()) {
                res.setHeader(header.getName(), MessageDigestUtil.iso88591ToUtf8(header.getValue()));
            }
            res.setContentType(res.getHeader("Content-Type"));
            res.setRequestId(res.getHeader("X-Ca-Request-Id"));
            res.setErrorMessage(res.getHeader("X-Ca-Error-Message"));
            if (response.getEntity() == null) {
                res.setBody(null);
            } else {
                res.setBody(HttpUtil.readStreamAsStr(response.getEntity().getContent()));
            }
        } else {
            res.setStatusCode(500);
            res.setErrorMessage("No Response");
        }
        return res;
    }

    private static Response convertImg(HttpResponse response) throws IOException {
        Response res = new Response();
        if (null != response) {
            if (302 == response.getStatusLine().getStatusCode()) {
                Header header = response.getFirstHeader("location");
                String newUrl = header.getValue();
                HttpGet httpget = new HttpGet(newUrl);
                CloseableHttpClient httpClient = HttpUtil.wrapClient(httpget.getURI().getScheme() + "://" + httpget.getURI().getHost());
                response = httpClient.execute(httpget);
            }
            res.setStatusCode(response.getStatusLine().getStatusCode());
            for (Header header : response.getAllHeaders()) {
                res.setHeader(header.getName(), MessageDigestUtil.iso88591ToUtf8(header.getValue()));
            }
            res.setContentType(res.getHeader("Content-Type"));
            res.setRequestId(res.getHeader("X-Ca-Request-Id"));
            res.setErrorMessage(res.getHeader("X-Ca-Error-Message"));
            res.setResponse(response);
        } else {
            res.setStatusCode(500);
            res.setErrorMessage("No Response");
        }
        return res;
    }

    public static String readStreamAsStr(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel dest = Channels.newChannel(bos);
        ReadableByteChannel src = Channels.newChannel(is);
        ByteBuffer bb = ByteBuffer.allocate(4096);
        while (src.read(bb) != -1) {
            bb.flip();
            dest.write(bb);
            bb.clear();
        }
        src.close();
        dest.close();
        return new String(bos.toByteArray(), "UTF-8");
    }

    public static String readImageAsStr(byte[] src) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String inStream2String(InputStream src) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = src.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return new String(baos.toByteArray());
    }

    private static CloseableHttpClient wrapClient(String host) {
        HttpClientBuilder builder = HttpClients.custom();
        if (host.startsWith("https://")) {
            builder = HttpUtil.sslClient(builder);
        }
        return builder.build();
    }

    private static HttpClientBuilder sslClient(HttpClientBuilder builder) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            builder.setSSLSocketFactory(ssf);
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        return builder;
    }

    private static Map<String, String> strToMap(String str) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            String[] params = str.split("&");
            for (String param : params) {
                String[] a = param.split("=");
                if (a.length >= 2) {
                    map.put(a[0], a[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}