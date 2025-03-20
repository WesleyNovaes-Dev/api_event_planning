package com.eventPlanning.api_event_planning.domain.event;

import com.eventPlanning.api_event_planning.domain.coupon.Coupon;

import java.util.Date;
import java.util.UUID;
import java.util.List;

public record EventDetailsDTO(
    UUID id,
    String title,
    String description,
    Date date,
    String city,
    String state,
    String imgUrl,
    String eventUrl,
    List<CouponDTO> coupons){

        public record CouponDTO(
                String code,
                Integer discount,
                Date valid) {
        }
}
