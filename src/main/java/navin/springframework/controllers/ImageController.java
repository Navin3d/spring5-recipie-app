package navin.springframework.controllers;

import navin.springframework.commands.RecipieCommand;
import navin.springframework.services.ImageService;
import navin.springframework.services.RecipieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;


@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipieService recipieService;

    @Autowired
    public ImageController(ImageService imageService, RecipieService recipieService) {
        this.imageService = imageService;
        this.recipieService = recipieService;
    }

    @GetMapping("recipie/{id}/image")
    public String saveUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipie", recipieService.findCommandById(Long.valueOf(id)));
        return "recipie/imageuploadform";
    }

    @PostMapping("recipie/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipie/"+id+"/show";
    }

    @GetMapping("recipie/{id}/recipieimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipieCommand recipieCommand = recipieService.findCommandById(Long.valueOf(id));

        if (recipieCommand.getImage() != null) {
            byte[] byteArray = new byte[recipieCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipieCommand.getImage()) {
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
