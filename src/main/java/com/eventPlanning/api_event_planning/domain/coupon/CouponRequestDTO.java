package com.eventPlanning.api_event_planning.domain.coupon;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
