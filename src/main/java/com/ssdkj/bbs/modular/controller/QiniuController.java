package com.ssdkj.bbs.modular.controller;


import com.alibaba.fastjson.JSON;
import com.qiniu.util.Auth;
import com.ssdkj.bbs.common.constants.QiNiuVariable;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.core.util.QiniuUtils;
import com.ssdkj.bbs.core.util.SnowflakeIdWorker;
import com.ssdkj.bbs.core.util.StringUtils;
import com.ssdkj.bbs.modular.api.ArticleService;
import com.ssdkj.bbs.modular.api.ParseRecordService;
import com.ssdkj.bbs.modular.model.Article;
import com.ssdkj.bbs.modular.model.ParseRecord;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/qiniu")
@RestController
@Log4j
public class QiniuController {

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse getQiniuToken() {
        Map<String, Object> result = new HashMap<>();
        try {
            //验证七牛云身份是否通过
            Auth auth = Auth.create(QiNiuVariable.accessKey, QiNiuVariable.secretKey);
            //生成凭证
            String upToken = auth.uploadToken(QiNiuVariable.bucket);
            result.put("token", upToken);
            //存入外链默认域名，用于拼接完整的资源外链路径
//            result.put("domain", domain);

            // 是否可以上传的图片格式
            /*boolean flag = false;
            String[] imgTypes = new String[]{"jpg","jpeg","bmp","gif","png"};
            for(String fileSuffix : imgTypes) {
                if(suffix.substring(suffix.lastIndexOf(".") + 1).equalsIgnoreCase(fileSuffix)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                throw new Exception("图片：" + suffix + " 上传格式不对！");
            }*/

            //生成实际路径名
//            String randomFileName = UUID.randomUUID().toString() + suffix;
//            result.put("imgUrl", randomFileName);
            result.put("success", 1);
        } catch (Exception e) {
            return HttpResponse.fail(null,null,"获取七牛云凭证失败");
        } finally {
            return HttpResponse.ok(result);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse delete(@RequestBody QueryArticleVo articleVo) {
        log.info("QiniuUtils.deletePic input pararm"+JSON.toJSONString(articleVo));
        if (articleVo==null || StringUtils.isBlank(articleVo.getArticleImgURL())){
            return HttpResponse.fail(null,null,"要删除的图片地址为空");
        }
        boolean result = QiniuUtils.deletePic(articleVo.getArticleImgURL());
        log.info("QiniuUtils.deletePic resp pararm"+result);
        return HttpResponse.ok(result);
    }

}

