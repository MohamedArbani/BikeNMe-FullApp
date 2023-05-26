package ma.ac.emi.ginfo.restfull.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class PicturesStorageService {

    private final Path root = Paths.get("uploads");

    public void createDirectory(String dir){
        Path directoryPath = root.resolve(dir);

        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (Exception e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }

    public void init() {
        try {
            Files.createDirectories(root);
            System.out.println("Directory "+root+" created");
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file, String bikeDirectory) {
        try {
            createDirectory(bikeDirectory);
            Files.copy(file.getInputStream(), this.root.resolve(bikeDirectory+"/"+file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }


    public void deleteBikeFolder(String dir) {
        FileSystemUtils.deleteRecursively(root.resolve(dir).toFile());
    }

    public Stream<Path> loadAll(String dir) {
        Path directoryPath = root.resolve(dir);
        try {
            return Files.walk(directoryPath, 1).filter(path -> !path.equals(directoryPath)).map(directoryPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
