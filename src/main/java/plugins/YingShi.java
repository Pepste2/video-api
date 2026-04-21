/*
 * Decompiled with CFR 0.152.
 */
package plugins;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import controller.MainController;
import java.util.HashMap;
import utils.ControllerFactory;
import utils.Util;

public class YingShi {
    private final MainController mainController = (MainController)ControllerFactory.controllers.get(MainController.class.getSimpleName());
    private static final String HOST = "https://open.ys7.com";
    private String appKey;
    private String appSecret;

    public YingShi(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public JSONObject getAccessToken() {
        String url = "https://open.ys7.com/api/lapp/token/get";
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appKey", this.appKey);
        data.put("appSecret", this.appSecret);
        String res = Util.doPost(url, headers, data);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject;
    }

    public JSONObject search(String accessToken) {
        String url = "https://open.ys7.com/api/lapp/live/video/list";
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("accessToken", accessToken);
        String res = Util.doPost(url, headers, query);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject;
    }

    public void checkValid() {
        this.mainController.result2.appendText("\u5f00\u59cb\u9a8c\u8bc1api\u6709\u6548\u6027\n");
        try {
            JSONObject resp = this.getAccessToken();
            System.out.println(resp);
            if (resp.get("code").equals("200")) {
                System.out.println("[+] api\u6709\u6548");
                this.mainController.result2.appendText("[+] api\u6709\u6548\n");
                String pretty = JSON.toJSONString((Object)resp, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                this.mainController.result2.appendText(pretty + "\n\n");
                this.mainController.search2.setDisable(false);
            } else {
                System.out.println("[-] api\u65e0\u6548");
                this.mainController.result2.appendText("[-] api\u65e0\u6548\n\n");
                this.mainController.search2.setDisable(true);
            }
        }
        catch (Exception e) {
            System.out.println("[-] api\u65e0\u6548");
            this.mainController.result2.appendText("[-] api\u65e0\u6548\n\n");
            this.mainController.search2.setDisable(true);
        }
    }

    public void checkSearch(String accessToken) {
        this.mainController.result2.appendText("\u5f00\u59cb\u9a8c\u8bc1\u641c\u7d22\u89c6\u9891\u8d44\u6e90\n");
        try {
            JSONObject resp = this.search(accessToken);
            System.out.println(resp);
            if (resp.get("code").equals("200")) {
                System.out.println("[+] \u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
                this.mainController.result2.appendText("[+] \u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n");
                String pretty = JSON.toJSONString((Object)resp, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                this.mainController.result2.appendText(pretty + "\n\n");
            } else {
                System.out.println("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
                this.mainController.result2.appendText("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n\n");
            }
        }
        catch (Exception e) {
            System.out.println("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
            this.mainController.result2.appendText("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n\n");
        }
    }

    public static void main(String[] args) {
        String appKey = "01c64bb3f7734e369068b51cf21b1adb";
        String appSecret = "7a227d31591bb1f0ed3f2a0d0e418333";
        YingShi yingShi = new YingShi(appKey, appSecret);
        String accessToken = "at.cbpbnhbo0hwc4fdh8152t52v2cjikgxs-230jzi2qvg-1aq2vrd-uoq8dxukd";
        yingShi.search(accessToken);
    }
}
