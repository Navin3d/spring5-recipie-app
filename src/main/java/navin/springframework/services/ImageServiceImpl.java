package navin.springframework.services;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.domain.Recipie;
import navin.springframework.repositories.RecipieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipieService recipieService;
    private final RecipieRepository recipieRepository;

    public ImageServiceImpl(RecipieService recipieService, RecipieRepository recipieRepository) {
        this.recipieService = recipieService;
        this.recipieRepository = recipieRepository;
    }

    @Override
    public void saveImageFile(Long recipieId, MultipartFile file) {

        log.debug("Image rceived...");

        try{
            Recipie recipie = recipieRepository.findById(Long.valueOf(recipieId)).get();
            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b : file.getBytes())
                byteObject[i++] = b;

            recipie.setImage(byteObject);
            recipieRepository.save(recipie);

        } catch (Exception e) {
            log.error("Error acquired", e);
            e.printStackTrace();
        }
    }
}
