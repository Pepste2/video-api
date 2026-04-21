/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Button
 *  javafx.scene.control.TextArea
 *  javafx.scene.control.TextField
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import plugins.HikVision;
import plugins.YingShi;
import utils.ControllerFactory;

public class MainController
implements Initializable {
    public int num = 1;
    @FXML
    public Button search1;
    @FXML
    public Button search2;
    @FXML
    public Button getPreview;
    @FXML
    public TextArea result1;
    @FXML
    public TextArea result2;
    @FXML
    public TextArea result3;
    @FXML
    private TextField host1;
    @FXML
    private TextField appKey1;
    @FXML
    private TextField appSecret1;
    @FXML
    public TextField cameraIndexCode;
    @FXML
    private TextField appKey2;
    @FXML
    private TextField appSecret2;
    @FXML
    private TextField accessToken2;

    public void initialize(URL location, ResourceBundle resources) {
        ControllerFactory.controllers.put(MainController.class.getSimpleName(), this);
    }

    @FXML
    void checkHikVision(ActionEvent event) throws Exception {
        String host = this.host1.getText();
        String appKey = this.appKey1.getText();
        String appSecret = this.appSecret1.getText();
        if (host.length() != 0 && appKey.length() != 0 && appSecret.length() != 0) {
            HikVision hikvision = new HikVision(host, appKey, appSecret);
            hikvision.checkValid();
        } else {
            this.result1.appendText("\u8f93\u5165\u4e0d\u80fd\u4e3a\u7a7a\n");
        }
    }

    @FXML
    void checkYingShi(ActionEvent event) throws Exception {
        String appKey = this.appKey2.getText();
        String appSecret = this.appSecret2.getText();
        if (appKey.length() != 0 && appSecret.length() != 0) {
            YingShi yingShi = new YingShi(appKey, appSecret);
            yingShi.checkValid();
        } else {
            this.result2.appendText("\u8f93\u5165\u4e0d\u80fd\u4e3a\u7a7a\n");
        }
    }

    @FXML
    void searchHikVision(ActionEvent event) throws Exception {
        String host = this.host1.getText();
        String appKey = this.appKey1.getText();
        String appSecret = this.appSecret1.getText();
        HikVision hikVision = new HikVision(host, appKey, appSecret);
        hikVision.checkSearch();
    }

    @FXML
    void searchYingShi(ActionEvent event) throws Exception {
        String appKey = this.appKey2.getText();
        String appSecret = this.appSecret2.getText();
        String accessToken = this.accessToken2.getText();
        YingShi yingShi = new YingShi(appKey, appSecret);
        yingShi.checkSearch(accessToken);
    }

    @FXML
    void getPreview(ActionEvent event) throws Exception {
        String host = this.host1.getText();
        String appKey = this.appKey1.getText();
        String appSecret = this.appSecret1.getText();
        String cameraIndexCodeText = this.cameraIndexCode.getText();
        HikVision hikVision = new HikVision(host, appKey, appSecret);
        hikVision.checkPreview(cameraIndexCodeText);
    }
}
