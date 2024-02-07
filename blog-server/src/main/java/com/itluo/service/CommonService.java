package com.itluo.service;

import java.io.File;

/**
 * 上传文件
 * @author Administrator
 */
public interface CommonService {

    /**
     * 上传文件
     * @param file
     * @param objectNames
     * @return
     */
    String uploads(File file, String objectNames);
}
