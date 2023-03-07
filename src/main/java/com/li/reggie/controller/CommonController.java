package com.li.reggie.controller;

import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestControllerAdvice
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.basePath}")
    private String basePath;

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split("\\.");

        String filename = UUID.randomUUID().toString() + "." + split[1];

        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(filename);
    }


    /**
     * 下载图片
     *
     * @param name
     * @param response
     * @return
     */
    @GetMapping("/download")
    public Result download(@RequestParam("name") String name, HttpServletResponse response) {

        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;



        try {

            inputStream = new FileInputStream(basePath + name);
            outputStream = response.getOutputStream();

//            response.setContentType("image/jpeg");

            int len = 0;
            byte[] data = new byte[1024 * 100];

            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }


        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Result.success("下载成功");
    }
}
