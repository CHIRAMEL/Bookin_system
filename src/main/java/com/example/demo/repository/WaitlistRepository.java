package com.example.demo.repository;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    List<Waitlist> findByScheduleId(Long scheduleId);

    Waitlist findByUserIdAndScheduleId(Long userId, Long scheduleId);

    List<Waitlist> findByScheduleOrderByCreatedAt(Schedule schedule);

}
