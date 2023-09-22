package com.lm.SpringBootProject.services;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.lm.SpringBootProject.core.config.MinioConfig;
import com.lm.SpringBootProject.core.utils.NanoIdUtils;
import com.lm.SpringBootProject.models.vm.UploadFileVM;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PostPolicy;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.minio.http.Method;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Desc: 操作minio的服务
 **/
@Service
@Slf4j
public class MinIoService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig configuration;


    /**
     * 获取上传文件临时签名，作用是：前端获取到签名信息后可以直接将文件上传到minio
     *
     * @param fileName
     * @param time
     * @return
     */
    public Map getPolicyUrl(String fileName, ZonedDateTime time) {
        PostPolicy postPolicy = new PostPolicy(configuration.getBucketName(), time);
        postPolicy.addEqualsCondition("key", fileName);
        try {
            Map<String, String> map = minioClient.getPresignedPostFormData(postPolicy);
            HashMap<String, String> policyMap = new HashMap<>();
            map.forEach((k, v) -> {
                policyMap.put(k.replaceAll("-", ""), v);
            });
            policyMap.put("host", configuration.getAccessKey() + "/" + configuration.getBucketName());
            return policyMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param objectName
     * @param time
     * @param timeUnit
     * @return
     */
    public String getFileUrl(String objectName, int time, TimeUnit timeUnit) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(configuration.getBucketName())
                    .object(objectName)
                    .expiry(time, timeUnit).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 针对前端直接上传的是文件的格式
     *
     * @param multipartFile 文件
     */
    public UploadFileVM uploadToFile(MultipartFile multipartFile) {
        UploadFileVM uploadFileVM = new UploadFileVM();
        var url = "";
        //这块可能有浏览器不支持getOriginalFilename(注意)
        String fileName = multipartFile.getOriginalFilename();
        uploadFileVM.setFileName(fileName);
        String[] split = fileName.split("\\.");
        //获取文件对应扩展名
        String extName = FileNameUtil.extName(fileName);
        //根据扩展名获取对应ContentType 路径，再拼装对应文件名
        //需要修改fileName +
        String getContentType = GetContentType(extName);
        extName = "." + extName;
        fileName = "upload/" + getContentType + "/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + NanoIdUtils.randomNanoId() + "/" + fileName;
        url = configuration.getEndpoint() + "/" + configuration.getBucketName() + "/" + fileName;
        // mredisTemplate.opsForValue().set(url, multipartFile.getSize());
        uploadFileVM.setFileUrl(url);
        uploadFileVM.setExt(extName);
        InputStream in = null;
        try {
            in = multipartFile.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(configuration.getBucketName())
                    .object(fileName)
                    .stream(in, in.available(), -1)
                    .contentType(multipartFile.getContentType())
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return uploadFileVM;
    }

    public static String GetContentType(String fileName) {
        //图片视频类
        if (fileName.equalsIgnoreCase("jpg")) {
            return "image/jpg";
        } else if (fileName.equalsIgnoreCase("jpeg")) {
            return "image/jpeg";
        } else if (fileName.equalsIgnoreCase("png")) {
            return "image/png";
        } else if (fileName.equalsIgnoreCase("gif")) {
            return "image/gif";
        } else if (fileName.equalsIgnoreCase("mp4")) {
            return "image/mp4";
        } else if (fileName.equalsIgnoreCase("avi")) {
            return "image/avi";
        } else if (fileName.equalsIgnoreCase("wmv")) {
            return "image/wmv";
        } else if (fileName.equalsIgnoreCase("mpg")) {
            return "image/mpg";
        } else if (fileName.equalsIgnoreCase("mov")) {
            return "image/mov";
        } else if (fileName.equalsIgnoreCase("rm")) {
            return "image/rm";
        } else if (fileName.equalsIgnoreCase("swf")) {
            return "image/swf";
        } else if (fileName.equalsIgnoreCase("flv")) {
            return "image/flv";
        } else if (fileName.equalsIgnoreCase("mkv")) {
            return "image/mkv";
        }//压缩类
        else if (fileName.equalsIgnoreCase("zip")) {
            return "application/zip";
        } else if (fileName.equalsIgnoreCase("7z")) {
            return "application/7z";
        } else if (fileName.equalsIgnoreCase("tar")) {
            return "application/tar";
        } else if (fileName.equalsIgnoreCase("gz")) {
            return "application/gz";
        } else if (fileName.equalsIgnoreCase("bz2")) {
            return "application/bz2";
        }//文本类
        else if (fileName.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        } else if (fileName.equalsIgnoreCase("txt")) {
            return "text/txt";
        } else if (fileName.equalsIgnoreCase("docx")) {
            return "application/docx";
        } else if (fileName.equalsIgnoreCase("doc")) {
            return "application/doc";
        } else if (fileName.equalsIgnoreCase("xls")) {
            return "application/xls";
        } else if (fileName.equalsIgnoreCase("xlsx")) {
            return "application/xlsx";
        } else {
            return "application/octet-stream";
        }
    }

    /**
     * 根据文件名访问指定有效期的文件访问链接
     *
     * @param objectName
     * @param time
     * @param timeUnit
     * @return
     */
    public String getUrl(String objectName, int time, TimeUnit timeUnit) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(configuration.getBucketName())
                    .object(objectName)
                    .expiry(time, timeUnit).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
