package com.travelapp.itinerary_service.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StayDetailDto {
	private String stayId;
	private List<String> amenities;
	private AddressDto addressDto;
	private long contactNo;
	private String hotelType;
	private RoomDto roomDto;
	private double price;
}
