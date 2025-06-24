package com.travelapp.itinerary_service.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.itinerary_service.enums.DestinationTypeEnum;
import com.travelapp.itinerary_service.util.MultiValueEnumValidator;

@Document(collection = "Itinerary")
public class Itinerary {
	@Id
	private String id;
	private boolean isGuideIncluded = true;
	private boolean isVehicleIncluded = false;
	private boolean isStayIncluded = false;
	private int duration;
	@MultiValueEnumValidator(enumClazz = DestinationTypeEnum.class, message = "One or more destination types are invalid")
	private List<String> destinationTypes;
	private String premiumType;
	private String itineraryName;
	private boolean active = true;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private StayInfo stayInfo;
	private String location;
	private double price;
	private Set<ItineraryDay> itineraryDays;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String imagesUri;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private VehicleInfo vehicleinfo;
	private boolean isPredefined = true;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private long customerId;
	private LocalDate createdDate;
	private LocalDate updatedDate;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String baseItineraryId;
	private String status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<Transfer> transfers;

	public Set<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(Set<Transfer> transfers) {
		this.transfers = transfers;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBaseItineraryId() {
		return baseItineraryId;
	}

	public void setBaseItineraryId(String baseItineraryId) {
		this.baseItineraryId = baseItineraryId;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isPredefined() {
		return isPredefined;
	}

	public void setPredefined(boolean isPredefined) {
		this.isPredefined = isPredefined;
	}


	public VehicleInfo getVehicleinfo() {
		return vehicleinfo;
	}

	public void setVehicleinfo(VehicleInfo vehicleinfo) {
		this.vehicleinfo = vehicleinfo;
	}

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

	public StayInfo getStayInfo() {
		return stayInfo;
	}

	public void setStayInfo(StayInfo stayInfo) {
		this.stayInfo = stayInfo;
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

	public Itinerary() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itinerary other = (Itinerary) obj;
		return Objects.equals(id, other.id);
	}
}
