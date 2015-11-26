package com.fansz.members.api.service.impl;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.fansz.appservice.service.FileService;
import com.fansz.appservice.utils.FileOperator;
import com.fansz.appservice.utils.PictureUtils;
import com.fansz.appservice.utils.ShortUUIDGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;

/**
 * 公用文件服务
 *
 * Created by xuzhen on 15/7/29.
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String fileUpload(
            @NotBlank(message = "error.file.path") String filePath,
            @NotNull(message = "error.file.stream") InputStream fileInputStream,
            String fileName)
            throws IOException {

        String imageId, imageFile;

        //Create picture path if not exist
        java.nio.file.Path saveImagePath = FileSystems.getDefault().getPath(filePath);
        if (!Files.exists(saveImagePath, LinkOption.NOFOLLOW_LINKS))
            Files.createDirectory(saveImagePath);

        //生成随机文件ID
        imageId = ShortUUIDGenerator.generate();

        //获取文件后缀
        String fileExtension = FileOperator.getExtensionName(fileName);
        //本地文件全名
        imageFile = imageId +"." +fileExtension;

        //文件输出
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(filePath, imageFile));

            int read;
            byte[] bytes = new byte[4096];
            while ((read = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }

        fileInputStream.close();

        return imageFile;
    }

    @Override
    public File getFile(String fullFileName) throws FileNotFoundException {
        java.nio.file.Path realPath = FileSystems.getDefault().getPath(fullFileName);

        if (!Files.exists(realPath)) {
            throw new FileNotFoundException("error.file.stream");
        }

        return realPath.toFile();
    }

    @Override
    public File getFile(String picturePath, String fileId, int width, int height) throws IOException, MetadataException, ImageProcessingException {
    	String[] split = fileId.split("\\.");
    	String newImageFile="";
    	java.nio.file.Path newImage=null;
    	if(split != null &&  split.length != 0){
	        //判断缩略图是否存在
	        newImageFile = split[0] + "_W" + width + "H" + height + "." + split[1];
	        newImage = FileSystems.getDefault().getPath(newImageFile);
	        if (!Files.exists(newImage)) {
	            //判断原图是否存在
	            java.nio.file.Path realPath = FileSystems.getDefault().getPath(picturePath + "/" + split[0] +"."+ split[1]);
	            if (!Files.exists(realPath)) {
	                throw new FileNotFoundException("error.file.stream");
	            }
	            //图形处理
	            BufferedImage image;
	            //Read file meta info
	            File file = realPath.toFile();
	            int angle = PictureUtils.getRotatedAngle(file);
	            image = ImageIO.read(file);
	            //旋转图像
	            if (angle > 0)
	                image = PictureUtils.rotateImage(image, angle);
	            //生成新尺寸图
	            PictureUtils.resize(image, picturePath, newImageFile, width, height);
	            //读取缩略图
	            newImage = FileSystems.getDefault().getPath(picturePath + "/" +newImageFile);
	        }
    	}
        if (!Files.exists(newImage))
            throw new FileNotFoundException("error.file.stream");
        else
            return newImage.toFile();
    }
}
