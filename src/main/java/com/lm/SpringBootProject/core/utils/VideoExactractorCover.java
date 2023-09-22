package com.lm.SpringBootProject.core.utils;//package com.dev.lib.util;
//
//import cn.hutool.core.lang.UUID;
//import com.dev.lib.model.vm.UploadFileVM;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import org.bytedeco.javacv.Frame;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//
//public class VideoExactractorCover {
//    //返回图片生成的路径
//    private static UploadFileVM doExecuteFrame(Frame f) {
//        String imagemat = "png";
//        if (null == f || null == f.image) {
//            return null;
//        }
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//        BufferedImage bi = converter.getBufferedImage(f);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(bi, imagemat, os);
//            InputStream is = new ByteArrayInputStream(os.toByteArray());
//            String fileName = UUID.randomUUID().toString() + "." + imagemat;
//            String contentType = "image/png";
//            UploadFileVM uploadFileVM = MinioUtils.uploadCover(is, fileName,contentType);
//            return uploadFileVM;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static UploadFileVM getVideoCover(String videoPath){
//        String pngPath = "";
//        FFmpegFrameGrabber ff=null;
//        UploadFileVM uploadFileVM = null;
//        try {
//            ff = FFmpegFrameGrabber.createDefault(new File(videoPath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            ff.start();
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        }
//        int ffLength = ff.getLengthInFrames();
//        Frame f;
//        int i = 0;
//        String weizhi="";
//        while (i < ffLength) {
//            try {
//                f = ff.grabFrame();
//                int target= (int) (0.2*ffLength);
//                //截取第20帧
//                if ((i > target) && (f.image != null)) {
//                    //执行截图并放入指定位置
//                    //System.out.println("存储图片 ： " + (dir + pngPath));
//                    uploadFileVM = doExecuteFrame(f);
//                    break;
//                }
//                i++;
//            } catch (FrameGrabber.Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            ff.stop();
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        }
//        return uploadFileVM;
//    }
//
//
//
//}
