package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Picture;
import ma.ac.emi.ginfo.restfull.services.PicturesStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin("*")
public class PicturesController {

    @Autowired
    PicturesStorageService storageService;

    @PostMapping("/upload/{bikeDirectory}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String bikeDirectory) {
        String message = "";
        Map<String,String> responseMessage = new HashMap<>();
        try {
            storageService.save(file,bikeDirectory);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseMessage.put("message",message);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            responseMessage.put("message",message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseMessage);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<Picture>> getListFiles() {
        List<Picture> pictures = storageService.loadAll(null).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(PicturesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new Picture(url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pictures);
    }

    @GetMapping("/files/{bikeDirectory}")
    public ResponseEntity<List<Picture>> getListFilesByBike(@PathVariable String bikeDirectory) {
        List<Picture> pictures = storageService.loadAll(bikeDirectory).map(path -> {
            String url = MvcUriComponentsBuilder
                    .fromMethodName(PicturesController.class, "getFile", bikeDirectory,path.getFileName().toString()).build().toString();

            return new Picture(url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pictures);
    }

    @GetMapping("/files/{dir}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String dir,@PathVariable String filename) {
        Resource file = storageService.load(dir+"/"+filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
