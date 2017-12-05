package com.taotao.service;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;

@Service
public class PictureSrviceImpl implements PictureService {
    @Value("${FTP_HOST}")
    private String FTP_HOST;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASEPATH}")
    private String FTP_BASEPATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map upLoadPicture(MultipartFile uploadFile) {
        Map resultMap = new HashMap<>();
        String oldName = uploadFile.getOriginalFilename();
        String newName = IDUtils.genImageName();
        newName = newName + oldName;
        try {
            FtpUtil ftp = new FtpUtil();
            String filePath = new DateTime().toString("/yyyy/MM/dd");
            boolean result = ftp.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASEPATH,
                    filePath, newName, uploadFile.getInputStream());


            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_BASE_URL + filePath + "/" + newName);

        } catch (Exception e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传异常");
        }
        return resultMap;


    }

}
