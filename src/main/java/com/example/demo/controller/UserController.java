package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.payload.RegisterOrUpdateRequest;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.service.UserService;
import com.example.demo.util.SetterFieldsUtil;
import com.example.demo.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;

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
        ValidationUtil.isNotFound(user == null);
        UserDto resultUserDto = UserMapper.INSTANCE.toDto(user);
        return new ResponseEntity<>(resultUserDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody RegisterOrUpdateRequest updateRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        if (userId == user.getId()) {
            SetterFieldsUtil.setFieldsUser(user, updateRequest);
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            userService.save(user);
            UserDto userDTOUpdated = UserMapper.INSTANCE.toDto(userService.getById(userId));
            return new ResponseEntity<>(userDTOUpdated, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        if (id == user.getId()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>("Incorrect id in URL ", HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "file/save", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveFile(@RequestParam MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByUserName(userDetails.getUsername());
            if (file.getContentType().contains("jpeg")
                    | file.getContentType().contains("png")) {
                user.setFile(FileCopyUtils.copyToByteArray(file.getInputStream()));
                userService.save(user);
                return new ResponseEntity("Successfully uploaded - " +
                        file.getOriginalFilename(), HttpStatus.OK);
            } else {
                return new ResponseEntity("File " + file.getOriginalFilename()
                        + " must contain contentType: jpeg or png", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity("Failed to write the object to the database" +
                    file.getOriginalFilename(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "file/download", method = RequestMethod.GET)
    public ResponseEntity<?> downloadFile(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse resp) throws IOException {
        User user = userService.findByUserName(userDetails.getUsername());
        OutputStream outputStream = resp.getOutputStream();
        FileCopyUtils.copy(user.getFile(), outputStream);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
