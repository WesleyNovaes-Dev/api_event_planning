package com.eventPlanning.api_event_planning.repositories;

import com.eventPlanning.api_event_planning.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>{

    //Quero externa
    //@Query("SELECT * FROM event e WHERE e.date >= :CurrentDate")
    //minha query
    //@Query("SELECT e FROM Event WHERE e.data >= CURRENT_DATE")
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address A WHERE e.date >= :currentDate ORDER BY e.date ASC")
    Page<Event> findUpcomingEvents(@Param("currentDate") Date currentDate, Pageable pageable);


    //filtrar por data, local e titulo

    @Query("SELECT e FROM Event e LEFT JOIN e.address a "  +
            "WHERE (:title = '' OR e.title LIKE %:title%)" +
            "AND (:city ='' OR a.city LIKE %:city%)" +
            "AND (:uf ='' OR a.uf LIKE %:uf%)" +
            "AND (e.date >= :startDate AND e.date <= :endDate)")
    Page<Event> findFilteredEvents(@Param("title") String title,
                                                    @Param("city") String city,
                                                    @Param("uf") String uf,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate,
                                                    Pageable pageable);


}
