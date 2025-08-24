package com.travelapp.itinerary_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class StayRoomPriceUpdateDto {
    private Long stayId;
    private Set<RoomPriceDto> roomPriceDtoList;
}
