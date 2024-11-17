//package com.lacossolidario.doacao.app.resource;
//
//import com.lacossolidario.doacao.app.config.FileStorageProperties;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/file")
//public class ArquivoResource {
//
//    private final Path fileStorageLocation;
//
//    public ArquivoResource(FileStorageProperties fileStorageProperties) {
//        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                .toAbsolutePath().normalize();
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadPhoto(@RequestParam("imagem") MultipartFile foto){
//        String fileName = StringUtils.cleanPath(foto.getOriginalFilename());
//
//        try{
//            Path targetLocation = fileStorageLocation.resolve(fileName);
//            foto.transferTo(targetLocation);
//
//            String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/api/doacao/download/")
//                    .path(fileName)
//                    .toUriString();
//
//            return ResponseEntity.ok("Download concluído: " + fileDowloadUri);
//
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//
//    }
//
//    @GetMapping("/dowload")
//    public ResponseEntity<Resource> dowloadPhoto(@PathVariable String fileName, HttpServletRequest request) {
//        Path filePath = fileStorageLocation.resolve(fileName).normalize();
//
//        try{
//            Resource resource = new UrlResource(filePath.toUri());
//            String contenType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//
//            if(contenType == null)
//                contenType = "application/octet-stream";
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contenType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename() + "\"")
//                    .body(resource);
//
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//
//    }
//
//}
