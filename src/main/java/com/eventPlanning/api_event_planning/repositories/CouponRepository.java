package com.eventPlanning.api_event_planning.repositories;

import com.eventPlanning.api_event_planning.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date validAfter);
}
