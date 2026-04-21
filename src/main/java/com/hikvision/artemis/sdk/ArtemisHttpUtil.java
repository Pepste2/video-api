/*
 * Decompiled with CFR 0.152.
 */
package com.hikvision.artemis.sdk;

import com.hikvision.artemis.sdk.Client;
import com.hikvision.artemis.sdk.Request;
import com.hikvision.artemis.sdk.Response;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.hikvision.artemis.sdk.constant.Constants;
import com.hikvision.artemis.sdk.enums.Method;
import com.hikvision.artemis.sdk.util.MessageDigestUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemisHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(ArtemisHttpUtil.class);
    private static final String SUCC_PRE = "2";
    private static final String REDIRECT_PRE = "3";

    public static String doGetArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            logger.info(path.get(httpSchema));
            Request request = new Request(Method.GET, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            Response response = Client.execute(request);
            responseStr = response.getBody();
        }
        catch (Exception e) {
            logger.error("the Artemis GET Request is failed[doGetArtemis]", e);
        }
        return responseStr;
    }

    public static String doGetArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            logger.info(path.get(httpSchema));
            Request request = new Request(Method.GET, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            Response response = Client.execute(request);
            responseStr = response.getBody();
        }
        catch (Exception e) {
            logger.error("the Artemis GET Request is failed[doGetArtemis]", e);
        }
        return responseStr;
    }

    public static HttpResponse doGetResponse(Map<String, String> path, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        HttpResponse httpResponse = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.GET_RESPONSE, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            Response response = Client.execute(request);
            httpResponse = response.getResponse();
        }
        catch (Exception e) {
            logger.error("the Artemis GET Request is failed[doGetArtemis]", e);
        }
        return httpResponse;
    }

    public static String doPostFormArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.POST_FORM, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setBodys(paramMap);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostForm Request is failed[doPostFormArtemis]", e);
        }
        return responseStr;
    }

    public static String doPostFormArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            }
            Request request = new Request(Method.POST_FORM, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setBodys(paramMap);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostForm Request is failed[doPostFormArtemis]", e);
        }
        return responseStr;
    }

    public static HttpResponse doPostFormImgArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        HttpResponse response = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.POST_FORM_RESPONSE, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setBodys(paramMap);
            Response response1 = Client.execute(request);
            response = response1.getResponse();
        }
        catch (Exception e) {
            logger.error("the Artemis PostForm Request is failed[doPostFormImgArtemis]", e);
        }
        return response;
    }

    public static String doPostStringArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.POST_STRING, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setStringBody(body);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", e);
        }
        return responseStr;
    }

    public static String doPostStringArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            Request request = new Request(Method.POST_STRING, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setStringBody(body);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", e);
        }
        return responseStr;
    }

    public static HttpResponse doPostStringImgArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        HttpResponse responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.POST_STRING_RESPONSE, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setStringBody(body);
            Response response = Client.execute(request);
            responseStr = response.getResponse();
        }
        catch (Exception e) {
            logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", e);
        }
        return responseStr;
    }

    public static String doPostBytesArtemis(Map<String, String> path, byte[] bytesBody, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (bytesBody != null) {
                headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            if (header != null) {
                headers.putAll(header);
            }
            Request request = new Request(Method.POST_BYTES, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setBytesBody(bytesBody);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostBytes Request is failed[doPostBytesArtemis]", e);
        }
        return responseStr;
    }

    public static String doPostBytesArtemis(Map<String, String> path, byte[] bytesBody, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (bytesBody != null) {
                headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            Request request = new Request(Method.POST_BYTES, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            request.setBytesBody(bytesBody);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PostBytes Request is failed[doPostBytesArtemis]", e);
        }
        return responseStr;
    }

    public static String doPutStringArtemis(Map<String, String> path, String body, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(body)) {
                headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(body));
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            Request request = new Request(Method.PUT_STRING, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setStringBody(body);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PutString Request is failed[doPutStringArtemis]", e);
        }
        return responseStr;
    }

    public static String doPutBytesArtemis(Map<String, String> path, byte[] bytesBody, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (bytesBody != null) {
                headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            } else {
                headers.put("Content-Type", "application/text;charset=UTF-8");
            }
            Request request = new Request(Method.PUT_BYTES, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setBytesBody(bytesBody);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis PutBytes Request is failed[doPutBytesArtemis]", e);
        }
        return responseStr;
    }

    public static String doDeleteArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema == null || StringUtils.isEmpty(httpSchema)) {
            throw new RuntimeException("http\u548chttps\u53c2\u6570\u9519\u8befhttpSchema: " + httpSchema);
        }
        String responseStr = null;
        try {
            HashMap<String, String> headers = new HashMap<String, String>();
            if (StringUtils.isNotBlank(accept)) {
                headers.put("Accept", accept);
            } else {
                headers.put("Accept", "*/*");
            }
            if (StringUtils.isNotBlank(contentType)) {
                headers.put("Content-Type", contentType);
            }
            Request request = new Request(Method.DELETE, httpSchema + ArtemisConfig.host, path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, Constants.DEFAULT_TIMEOUT);
            request.setHeaders(headers);
            request.setQuerys(querys);
            Response response = Client.execute(request);
            responseStr = ArtemisHttpUtil.getResponseResult(response);
        }
        catch (Exception e) {
            logger.error("the Artemis DELETE Request is failed[doDeleteArtemis]", e);
        }
        return responseStr;
    }

    private static String getResponseResult(Response response) {
        String responseStr = null;
        int statusCode = response.getStatusCode();
        if (String.valueOf(statusCode).startsWith(SUCC_PRE) || String.valueOf(statusCode).startsWith(REDIRECT_PRE)) {
            responseStr = response.getBody();
            logger.info("the Artemis Request is Success,statusCode:" + statusCode + " SuccessMsg:" + response.getBody());
        } else {
            String msg = response.getErrorMessage();
            responseStr = response.getBody();
            logger.error("the Artemis Request is Failed,statusCode:" + statusCode + " errorMsg:" + msg);
        }
        return responseStr;
    }
}
