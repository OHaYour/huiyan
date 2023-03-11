package com.huiyan.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class ImageController {


    //设置视频，图片等静态资源保存路径
    private static final String HEADIMG_PATH = "D:\\workspace01\\part1\\src\\main\\resources\\static\\video\\";
    private static final String BUSINESSIMG_PATH = "D:\\workspace01\\part1\\src\\main\\resources\\static\\img\\";
    private static final String COMPANYIMG_PATH = "D:\\workspace01\\part1\\src\\main\\resources\\static\\headImg\\";

    /**
     * 视频上传
     */
    @GetMapping(path = "/video/{filename}", produces = { MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE })
    public BufferedImage getImage(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = HEADIMG_PATH + filename;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
        return null;
    }

    /**
     * 封面上传
     */
    @GetMapping(path = "/img/{filename}", produces = { MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE })
    public BufferedImage getBusinessImg(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = BUSINESSIMG_PATH + filename;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
        return null;
    }

    /**
     * 图片上传
     */
    @GetMapping(path = "/headImg/{filename}", produces = { MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE })
    public BufferedImage getCompanyimg(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = COMPANYIMG_PATH + filename;
            ips = new FileInputStream(new File(imgPath));
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
        return null;
    }
}