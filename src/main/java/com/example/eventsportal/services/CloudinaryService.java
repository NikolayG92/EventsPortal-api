package com.example.eventsportal.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {


    String uploadImg(MultipartFile multipartFile) throws IOException;
}
