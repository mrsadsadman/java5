package poly.edu.assignment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String fileName) {
        if (fileName != null) {
            File file = new File(uploadPath + fileName);
            if (file.exists())
                file.delete();
        }
    }
}