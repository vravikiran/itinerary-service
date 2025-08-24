package com.travelapp.itinerary_service.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.itinerary_service.enums.DestinationTypeEnum;
import com.travelapp.itinerary_service.util.MultiValueEnumValidator;

@Document(collection = "Itinerary")
@Data
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

}
