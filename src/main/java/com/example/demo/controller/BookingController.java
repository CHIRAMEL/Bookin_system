package com.example.demo.controller;

import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book/{userId}/{scheduleId}")
    public String bookClass(@PathVariable Long userId, @PathVariable Long scheduleId) {
        return bookingService.bookClass(userId, scheduleId);
    }

    @PostMapping("/cancel/{userId}/{bookingId}")
    public String cancelBooking(@PathVariable Long userId, @PathVariable Long bookingId) {
        return bookingService.cancelBooking(userId, bookingId);
    }

    @PostMapping("/complete/{scheduleId}")
    public void completeClass(@PathVariable Long scheduleId) {
        bookingService.processClassCompletion(scheduleId);
    }


}
