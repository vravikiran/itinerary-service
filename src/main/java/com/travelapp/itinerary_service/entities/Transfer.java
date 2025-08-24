package com.travelapp.itinerary_service.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Transfer {
    private String source;
    private String destination;
    private LocalDate date;
    private LocalTime time;
}
