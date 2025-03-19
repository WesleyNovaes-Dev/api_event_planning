package com.eventPlanning.api_event_planning.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.domain.event.EventRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    // Injeção do AmazonS3 diretamente, sem a necessidade do @Lazy
    private final AmazonS3 s3Client;

    // Construtor para a injeção do AmazonS3
    @Autowired
    public EventService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public Event createEvent(EventRequestDTO data) {
        String imgUrl = "";

        if (data.image() != null) {
            imgUrl = this.uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imgUrl);

        return newEvent;
    }

    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();

        } catch (Exception e) {
            System.out.println("Erro ao subir arquivo");
            return null;
        }
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File ConvFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(ConvFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return ConvFile;
    }
}
