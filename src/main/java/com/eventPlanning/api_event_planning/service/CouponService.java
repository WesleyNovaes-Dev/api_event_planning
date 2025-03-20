package com.eventPlanning.api_event_planning.service;

import com.eventPlanning.api_event_planning.domain.coupon.Coupon;
import com.eventPlanning.api_event_planning.domain.coupon.CouponRequestDTO;
import com.eventPlanning.api_event_planning.domain.event.Event;
import com.eventPlanning.api_event_planning.repositories.CouponRepository;
import com.eventPlanning.api_event_planning.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponRepository repository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CouponRepository couponRepository;

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponData)
    {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponData.code());
        coupon.setDiscount(couponData.discount());
        coupon.setEvent(event);
        coupon.setValid(new Date(couponData.valid()));

        return couponRepository.save(coupon);

    }

    public List<Coupon> consultCoupons(UUID eventId, Date currentDate) {
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
}
