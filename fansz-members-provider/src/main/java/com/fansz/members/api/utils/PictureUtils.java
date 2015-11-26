package com.fansz.members.api.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;

/**
 * 图片处理工具
 *
 * Created by xuzhen on 15/8/17.
 */
public class PictureUtils {

    public static BufferedImage rotateImage(BufferedImage image, int angle) {

        double ang = Math.toRadians(angle);

        int originWidth = image.getWidth();
        int originHeight = image.getHeight();

        //确定新图的宽高
        int w, h;
        if (angle == 90 || angle == 270) {
            w = originHeight;
            h = originWidth;
        }
        else {
            w = originWidth;
            h = originHeight;
        }
        //确定偏移坐标
        int x = (w / 2) - (originWidth / 2);
        int y = (h / 2) - (originHeight / 2);

        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());

        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2.0, h / 2.0);//旋转图象
        if (x != 0 || y != 0)
            at.translate(x, y);//图像回到原点
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        op.filter(image, rotatedImage);
        return rotatedImage;
    }

    public static int getRotatedAngle(File file) throws ImageProcessingException, IOException, MetadataException {
        int angle = 0;
        //Read MetaData
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        ExifIFD0Directory directory
                = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (directory == null) return 0;
        int orientation = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
        switch (orientation) {
            case 3:
                angle = 180;
                break;
            case 6:
                angle = 90;
                break;
            case 8:
                angle = 270;
                break;
            default:
                angle = 0;
                break;
        }

        return angle;
    }

    public static void resize(BufferedImage image, String imagePath, String newImageFile, int w, int h) throws IOException {

        //Check ImagePath is exist, if not then create it
        java.nio.file.Path saveImagePath = FileSystems.getDefault().getPath(imagePath);
        if (!Files.exists(saveImagePath, LinkOption.NOFOLLOW_LINKS))
            Files.createDirectory(saveImagePath);

        //判断按照宽还是高来压缩，然后计算裁剪位置
        int originWidth = image.getWidth();
        int originHeight = image.getHeight();

        int newWidth, newHeight, newTop, newLeft;
        //取短边进行缩放，然后按照中心点来计算截图位置
        if ((originWidth / originHeight) < (w/h)) {
            //按照宽度同比例压缩
            newWidth = w;
            newHeight = originHeight * w / originWidth;
            //计算截图位置
            newLeft = 0;
            newTop = (newHeight - h) / 2;
        }
        else {
            //按照长度同比例压缩
            newWidth = originWidth * h / originHeight;
            newHeight = h;
            //计算截图位置
            newLeft = (newWidth - w) / 2;
            newTop = 0;
        }
        //对图像进行压缩
        BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        //图像截取
        ImageFilter filter = new CropImageFilter(newLeft, newTop, w, h);
        Image tagImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bufferedImage.getSource(), filter));
        bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(tagImage, 0, 0, null);

        OutputStream outputStream = new FileOutputStream(new File(saveImagePath.toString(), newImageFile));
        ImageIO.write(bufferedImage, "jpg", outputStream);

        outputStream.flush();
        outputStream.close();
    }
}
