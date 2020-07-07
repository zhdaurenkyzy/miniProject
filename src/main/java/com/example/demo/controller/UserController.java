package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.payload.RegisterOrUpdateRequest;
import com.example.demo.security.jwt.JWTTokenProvider;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Value("${upload.folder}")
    private String uploadFolder;
    private JWTTokenProvider jwtTokenProvider;
    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserController(JWTTokenProvider jwtTokenProvider, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto resultUserDto = UserMapper.INSTANCE.toDto(user);
        return new ResponseEntity<>(resultUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody RegisterOrUpdateRequest updateRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        if (userId == user.getId()) {
            user.setUserName(updateRequest.getUserName());
            user.setEmail(updateRequest.getEmail());
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            userService.save(user);
            UserDto userDTOUpdated = UserMapper.INSTANCE.toDto(userService.getById(user.getId()));
            return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveFile(@RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        } else if (file.getContentType().contains("doc") | file.getContentType().contains("docx") |
                file.getContentType().contains("pdf")) {
            try {
                byte[] bytes = file.getBytes();
                String resultFileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
                Path path = Paths.get(uploadFolder + resultFileName);
                Files.write(path, bytes);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity("Successfully uploaded - " +
                    file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
        } else return new ResponseEntity("please select a file with doc, docx or pdf type!", HttpStatus.OK);
    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) throws IOException {
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"").body(resource);

        return responseEntity;
    }

}
