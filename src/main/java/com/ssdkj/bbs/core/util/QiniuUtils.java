package com.ssdkj.bbs.core.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ssdkj.bbs.common.constants.QiNiuVariable;
import lombok.extern.log4j.Log4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@Log4j
public class QiniuUtils {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static String ACCESS_KEY = QiNiuVariable.accessKey; //这两个登录七牛 账号里面可以找到
    private static String SECRET_KEY = QiNiuVariable.secretKey;

    //要上传的空间
    private static String bucketname = QiNiuVariable.bucket; //对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）

    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static Configuration cfg = new Configuration(Zone.huanan());
    //创建上传对象

    private static UploadManager uploadManager = new UploadManager(cfg);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken(){
        return auth.uploadToken(bucketname);
    }


    public static String uploadPic(String FilePath,String FileName){
        Configuration cfg = new Configuration(Zone.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = QiNiuVariable.accessKey;      //AccessKey的值
        String secretKey = QiNiuVariable.secretKey;      //SecretKey的值
        String bucket = QiNiuVariable.bucket;                                          //存储空间名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(FilePath, FileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return QiNiuVariable.domain+FileName;
        }catch (QiniuException ex){
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public static String updateFile(MultipartFile file, String filename) throws Exception {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            InputStream inputStream=file.getInputStream();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[600]; //buff用于存放循环读取的临时数据
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }

            byte[] uploadBytes  = swapStream.toByteArray();
            try {
                Response response = uploadManager.put(uploadBytes,filename,getUpToken());
                //解析上传成功的结果
                DefaultPutRet putRet;
                putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                return QiNiuVariable.domain+putRet.key;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                }
            }
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public static boolean deletePic(String picUrl){
        try {
            Configuration cfg = new Configuration(Zone.huanan());
            BucketManager bucketManager = new BucketManager(auth,cfg);
            Response response = bucketManager.delete(bucketname,picUrl);
            if (response.statusCode==200){
                return true;
            }else {
                return false;
            }
        } catch (QiniuException e) {
            log.error(e);
            e.printStackTrace();
            return  false;
        }
    }

    public static boolean deletePics(String[] keyList){
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            if (keyList.length>1000){
                return false;
            }
            //单次批量请求的文件数量不得超过1000
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucketname, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            boolean isSuccess = true;
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code != 200) {
                    isSuccess = false;
                    log.info("qiniu delete error:"+keyList[i]+"-"+status.data.error);
                }
            }
            return isSuccess;
        } catch (QiniuException ex) {
            log.error("QiniuException "+ex.response.toString());
            return false;
        }
    }

    public static void main(String[] args) {
//        String result = uploadPic("C:\\Users\\gouquancheng\\Desktop\\test.jpg","test.jpg");
//        System.out.println(result);

//        System.out.println(deletePic("test.jpg"));
    }
}
