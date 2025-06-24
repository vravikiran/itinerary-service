package com.travelapp.itinerary_service.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.travelapp.itinerary_service.services.ItineraryService;

@Component
public class DailyScheduler {
	@Autowired
	ItineraryService itineraryService;

	@Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
	public void deleteCustomItrUntouched() {
		itineraryService.deleteCustomItineariesUntouched();
	}

	@Scheduled(cron = "0 0 5 * * *", zone = "Asia/Kolkata")
	public void archiveUnTouchedCustItrFromPastSixMonths() {
		itineraryService.archiveUnTouchedCustItrFromPastSixMonths();
	}
}
