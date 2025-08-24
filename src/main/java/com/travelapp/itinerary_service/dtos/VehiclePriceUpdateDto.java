package com.travelapp.itinerary_service.dtos;

import lombok.Data;

@Data
public class VehiclePriceUpdateDto {
	private String vehicleId;
	private double updatedPrice;
}
