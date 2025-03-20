package com.eventPlanning.api_event_planning.controller;


import com.eventPlanning.api_event_planning.domain.coupon.Coupon;
import com.eventPlanning.api_event_planning.domain.coupon.CouponRequestDTO;
import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.domain.event.EventRequestDTO;
import com.eventPlanning.api_event_planning.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("event/{eventId}")
    public ResponseEntity<Coupon> addCouponToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data)
    {
        Coupon coupons = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupons);
    }


}
