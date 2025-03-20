package com.eventPlanning.api_event_planning.service;

import com.amazonaws.services.s3.AmazonS3;
import com.eventPlanning.api_event_planning.domain.address.Address;
import com.eventPlanning.api_event_planning.domain.coupon.Coupon;
import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.domain.event.EventDetailsDTO;
import com.eventPlanning.api_event_planning.domain.event.EventRequestDTO;
import com.eventPlanning.api_event_planning.domain.event.EventResponseDTO;
import com.eventPlanning.api_event_planning.repositories.EventRepository;
import com.sun.java.accessibility.util.EventID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private AddressService addressService;


    @Autowired
    private EventRepository repository;
    @Autowired
    private CouponService couponService;

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
        newEvent.setRemote(data.remote());

        repository.save(newEvent);

        if(!data.remote())
        {
            this.addressService.createAddress(data, newEvent);
        }

        return newEvent;
    }

    //Listar todos os eventos
    /*public List<EventResponseDTO> getEvents(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.repository.findAll(pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(), event.getDescription(),event.getDate(), "", "", event.getRemote(), event.getEventUrl(), event.getImgUrl()))
                .stream().toList();
    }*/
    //findAll -> Listagem geral do meu repositorio(banco)

    //Listar eventos futuros, que não ocorreram


     /*   public List<EventResponseDTO> getUpcomingEvents(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.repository.findUpcomingEvents(new Date(),pageable);
        return eventsPage.map(event -> new EventResponseDTO(event.getId(), event.getTitle(), event.getDescription(),event.getDate(), event.getAddress().getCity(), event.getAddress().getUf(), event.getRemote(), event.getEventUrl(), event.getImgUrl()))
                .stream().toList();
    }*/

    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Page<Event> eventsPage = repository.findUpcomingEvents(currentDate, pageable);

        return eventsPage.stream()
                .map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity() : "",
                        event.getAddress() != null ? event.getAddress().getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl()))
                .toList();
    }


    public List<EventResponseDTO> getFilteredEvents(int page, int size,String title, String city, String uf, Date startDate, Date endDate){
        title = (title != null) ? title : "";
        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        LocalDate today = LocalDate.now();
        startDate = (startDate != null) ? startDate : java.sql.Date.valueOf(today);
        endDate = (endDate != null) ? endDate : java.sql.Date.valueOf(LocalDate.of(9999, 12, 31));


        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = this.repository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity() : "",
                        event.getAddress() != null ? event.getAddress().getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .stream().toList();
    }

    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Optional<Address> address = addressService.findByEventId(eventId);

        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                address.isPresent() ? address.get().getCity() : "",
                address.isPresent() ? address.get().getUf() : "",
                event.getImgUrl(),
                event.getEventUrl(),
                couponDTOs);
    }



    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            System.out.println("Arquivo subido com sucesso");
            return s3Client.getUrl(bucketName, filename).toString()
            ;
        } catch (Exception e) {
            System.out.println("Erro ao subir arquivo");
            return " ";
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
