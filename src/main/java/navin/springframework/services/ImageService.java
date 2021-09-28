package navin.springframework.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipieId, MultipartFile file);
}
