package com.travelapp.itinerary_service.dtos;

public class VehiclePriceUpdateDto {
	private String vehicleId;
	private double updatedPrice;
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public double getUpdatedPrice() {
		return updatedPrice;
	}
	public void setUpdatedPrice(double updatedPrice) {
		this.updatedPrice = updatedPrice;
	}
}
