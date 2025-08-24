package com.travelapp.itinerary_service.dtos;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.itinerary_service.entities.ItineraryDay;
import lombok.Data;

@Data
public class ItineraryDto {
	private Long id;
	private boolean isGuideIncluded = true;
	private boolean isVehicleIncluded = false;
	private boolean isStayIncluded = false;
	private int duration;
	private List<String> destinationTypes;
	private String premiumType;
	private String itineraryName;
	private boolean active = true;
	private String location;
	private double price;
	private Set<ItineraryDay> itineraryDays;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imagesUri;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private StayDetailDto stayDetailDto;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VehicleDetailDto vehicleDetailDto;

}
