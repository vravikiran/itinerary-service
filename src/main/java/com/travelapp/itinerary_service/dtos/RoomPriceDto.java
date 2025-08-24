package com.travelapp.itinerary_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomPriceDto {
    private int roomId;
    private double price;
}
