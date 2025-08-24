package com.travelapp.itinerary_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoomDto {
	private int roomId;
	private String bedType;
	private int size;
	private List<String> roomAmenities;
	private int noOfBeds;
}
