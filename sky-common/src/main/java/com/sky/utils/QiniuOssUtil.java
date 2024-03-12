package com.sky.utils;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sky.constant.QiniuRegionConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.qiniu.storage.Configuration;

@Data
@AllArgsConstructor
@Slf4j
public class QiniuOssUtil {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    private String zone;

    public String upload(byte[] bytes, String objectName) {
        //创建带有地域的配置类
        Configuration cfg = new Configuration(getRegion());
        //创建上传管理器
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        //设置要上传的空间
        String upToken = auth.uploadToken(bucket);
        try{
            Response res = uploadManager.put(bytes, objectName, upToken);
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 计算这张存储照片的地址并返回, 暂时用http协议
                //TODO: 以后可以改成https
                String imgURL = "http://" + domain + "/" + objectName;
                log.info("上传文件成功：" + imgURL);
                return imgURL;
            } else {
                log.error("七牛云异常：" + res.bodyString());
                return null;
            }
        }catch (Exception e){
            log.error("上传文件失败", e);
        }
        return null;
    }
    public Region getRegion(){
        if(QiniuRegionConstant.HUADONG.equals(zone)){ //华东
            return Region.huadong();
        }else if(QiniuRegionConstant.HUABEI.equals(zone)){ //华北
            return Region.huabei();
        }else if(QiniuRegionConstant.HUANAN .equals(zone)){ //华南
            return Region.huanan();
        }else if(QiniuRegionConstant.BEIMEI.equals(zone)){ //北美
            return Region.beimei();
        }else if(QiniuRegionConstant.DONGNANYA.equals(zone)){ //东南亚
            return Region.xinjiapo();
        }
        return null;
    }
}
