package com.travelapp.itinerary_service.dtos;

import lombok.Data;

@Data
public class AddressDto {
	private long addressId;
	private String address_line1;
	private String address_line2;
	private String city;
	private String state;
	private String country;
	private int zipCode;
}
