package com.example.demo.service;

import com.example.demo.entity.Waitlist;
import com.example.demo.repository.WaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitlistService {

    @Autowired
    private WaitlistRepository waitlistRepository;

    public List<Waitlist> getWaitlistForClass(Long classId) {
        return waitlistRepository.findByScheduleId(classId);
    }

    public Waitlist addToWaitlist(Long userId, Long classId) {

        return null;
    }
}
