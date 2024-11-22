package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private WaitlistRepository waitlistRepository;

    @Autowired
    private UserService userService;

    public String bookClass(Long userId, Long scheduleId) {
        User user = userService.getUserById(userId);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Class not found."));

        if (user.getCredits() < schedule.getRequiredCredits()) {
            throw new RuntimeException("Insufficient credits.");
        }

        if (schedule.getAvailableSlots() > 0) {
            schedule.setAvailableSlots(schedule.getAvailableSlots() - 1);
            scheduleRepository.save(schedule);

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setSchedule(schedule);
            booking.setStatus(BookingStatus.BOOKED);
            bookingRepository.save(booking);

            user.setCredits(user.getCredits() - schedule.getRequiredCredits());
            userService.updateUser(user);

            return "Class booked successfully.";
        } else {
            Waitlist waitlist = new Waitlist();
            waitlist.setUser(user);
            waitlist.setSchedule(schedule);
            waitlistRepository.save(waitlist);

            return "Class is full. You have been added to the waitlist.";
        }
    }

    public String cancelBooking(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found."));

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized action.");
        }

        Schedule schedule = booking.getSchedule();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(schedule.getStartTime().minusHours(4))) {
            booking.setStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);

            User user = booking.getUser();
            user.setCredits(user.getCredits() + schedule.getRequiredCredits());
            userService.updateUser(user);

            schedule.setAvailableSlots(schedule.getAvailableSlots() + 1);
            scheduleRepository.save(schedule);

            List<Waitlist> waitlist = waitlistRepository.findByScheduleOrderByCreatedAt(schedule);
            if (!waitlist.isEmpty()) {
                Waitlist nextInLine = waitlist.get(0);
                Booking newBooking = new Booking();
                newBooking.setUser(nextInLine.getUser());
                newBooking.setSchedule(schedule);
                newBooking.setStatus(BookingStatus.BOOKED);
                bookingRepository.save(newBooking);

                waitlistRepository.delete(nextInLine);
            }

            return "Booking canceled, credits refunded.";
        } else {
            return "Cannot cancel within 4 hours of the class start.";
        }
    }

    public void processClassCompletion(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Class not found."));

        List<Waitlist> waitlist = waitlistRepository.findByScheduleOrderByCreatedAt(schedule);

        for (Waitlist entry : waitlist) {
            User user = entry.getUser();
            user.setCredits(user.getCredits() + schedule.getRequiredCredits());
            userService.updateUser(user);

            waitlistRepository.delete(entry);
        }
    }
}
