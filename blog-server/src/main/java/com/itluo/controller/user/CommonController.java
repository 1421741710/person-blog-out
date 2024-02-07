package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.CommonService;
import com.itluo.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/common")
@Api(tags = "上传相关接口")
@Slf4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result<String> upload(MultipartFile file){
        if(file == null || file.isEmpty()){
            return Result.error(MessageConstant.FIEID_IS_EMPTY);
        }
        try {
            File tempFile = FileUtil.convertAndDeleteOnExit(file);
            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectNames = UUID.randomUUID().toString() + substring;
            String upload = commonService.uploads(tempFile, objectNames);

            return Result.success(MessageConstant.UPLOAD_SUCCESSFUL,upload);
        } catch (IOException e) {
            log.error("文件转换或上传过程中发生错误", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }



}
