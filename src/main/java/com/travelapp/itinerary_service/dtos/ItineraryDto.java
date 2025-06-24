package com.travelapp.itinerary_service.dtos;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.itinerary_service.entities.ItineraryDay;

public class ItineraryDto {
	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isGuideIncluded() {
		return isGuideIncluded;
	}

	public void setGuideIncluded(boolean isGuideIncluded) {
		this.isGuideIncluded = isGuideIncluded;
	}

	public boolean isVehicleIncluded() {
		return isVehicleIncluded;
	}

	public void setVehicleIncluded(boolean isVehicleIncluded) {
		this.isVehicleIncluded = isVehicleIncluded;
	}

	public boolean isStayIncluded() {
		return isStayIncluded;
	}

	public void setStayIncluded(boolean isStayIncluded) {
		this.isStayIncluded = isStayIncluded;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<String> getDestinationTypes() {
		return destinationTypes;
	}

	public void setDestinationTypes(List<String> destinationTypes) {
		this.destinationTypes = destinationTypes;
	}

	public String getPremiumType() {
		return premiumType;
	}

	public void setPremiumType(String premiumType) {
		this.premiumType = premiumType;
	}

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<ItineraryDay> getItineraryDays() {
		return itineraryDays;
	}

	public void setItineraryDays(Set<ItineraryDay> itineraryDays) {
		this.itineraryDays = itineraryDays;
	}

	public String getImagesUri() {
		return imagesUri;
	}

	public void setImagesUri(String imagesUri) {
		this.imagesUri = imagesUri;
	}

	public StayDetailDto getStayDetailDto() {
		return stayDetailDto;
	}

	public void setStayDetailDto(StayDetailDto stayDetailDto) {
		this.stayDetailDto = stayDetailDto;
	}

	public VehicleDetailDto getVehicleDetailDto() {
		return vehicleDetailDto;
	}

	public void setVehicleDetailDto(VehicleDetailDto vehicleDetailDto) {
		this.vehicleDetailDto = vehicleDetailDto;
	}

	public ItineraryDto() {
		super();
	}
}
