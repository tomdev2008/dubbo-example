package com.fansz.members.api.provider;

import com.fansz.members.api.service.FileService;
import com.fansz.members.api.utils.Constants;
import com.fansz.members.api.utils.ErrorMessage;
import com.fansz.members.api.utils.ErrorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

/**
 * Created by root on 15-11-3.
 */
@Service
@Component("/serviceProvider")
public class ServiceProvider {

    @Value("${pictures.base}")
    private String picturesSavePath;

    @Autowired
    private FileService fileService;

    @Autowired
    private ErrorParser errorParser;

    @GET
    @Path("/image/{id}")
    @Produces("image/*")
    public Response getImage(@DefaultValue("empty.jpg") @PathParam("id") String id,
                             @DefaultValue("0") @QueryParam("width") int width,
                             @DefaultValue("0") @QueryParam("height") int height)
    {
        File file = null;
        String mt = null;
        List<ErrorMessage> errorMessages = new Vector<>();
        try {
            if (0 == width || 0 == height) {
                //获取原图
                file = fileService.getFile(picturesSavePath + "/" + id);
                mt = new MimetypesFileTypeMap().getContentType(file);
            }
            else {
                //获取缩略图
                file = fileService.getFile(picturesSavePath, id
                        , width, height);
                mt = new MimetypesFileTypeMap().getContentType(file);
            }
        }
        catch(FileNotFoundException fnfe) {
            errorMessages.add(errorParser.phase(fnfe.getMessage()));
        }
        catch (Exception e) {
            errorMessages.add(errorParser.phase("error.unknown"));
        }

        if (errorMessages.size() != 0)
            return Response.ok().entity(errorMessages).build();
        else{
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(Constants.PICTURE_CACHE_TIME);
            cacheControl.setPrivate(false);
            return Response.ok(file, mt).cacheControl(cacheControl).build();}

    }
}
