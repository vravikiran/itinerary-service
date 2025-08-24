package com.travelapp.itinerary_service.dtos;

import lombok.Data;

@Data
public class VehicleDetailDto {
	private int vehicleId;
	private String vehicleType;
	private int noOfSeats;
	private double price;
}
