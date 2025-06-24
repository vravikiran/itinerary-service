package com.travelapp.itinerary_service.util;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.entities.ItineraryDay;

public class ItineraryUtils {

    public static Itinerary deepCopy(Itinerary original) {
        Itinerary copy = new Itinerary();
        copy.setId(null);
        copy.setActive(original.isActive());
        copy.setCreatedDate(LocalDate.now());
        copy.setDestinationTypes(original.getDestinationTypes());
        copy.setGuideIncluded(original.isGuideIncluded());
        copy.setDuration(original.getDuration());
        copy.setBaseItineraryId(original.getId());
        copy.setImagesUri(original.getImagesUri());
        copy.setItineraryName(original.getItineraryName());
        copy.setLocation(original.getLocation());
        copy.setPremiumType(original.getPremiumType());
        copy.setPrice(original.getPrice());
        copy.setStayIncluded(original.isStayIncluded());
        copy.setVehicleIncluded(original.isVehicleIncluded());
        copy.setVehicleinfo(original.getVehicleinfo());
        copy.setStayInfo(original.getStayInfo());
        Set<ItineraryDay> copiedDays = original.getItineraryDays().stream().map(day -> {
            ItineraryDay newDay = new ItineraryDay();
            newDay.setActivities(day.getActivities());
            newDay.setDayNumber(day.getDayNumber());
            newDay.setDescription(day.getDescription());
            newDay.setTitle(day.getTitle());
            return newDay;
        }).collect(Collectors.toSet());
        copy.setItineraryDays(copiedDays);
        return copy;
    }
}
