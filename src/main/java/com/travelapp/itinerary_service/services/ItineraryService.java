package com.travelapp.itinerary_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.travelapp.itinerary_service.dtos.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelapp.itinerary_service.entities.Itinerary;
import com.travelapp.itinerary_service.entities.StayInfo;
import com.travelapp.itinerary_service.entities.Transfer;
import com.travelapp.itinerary_service.entities.VehicleInfo;
import com.travelapp.itinerary_service.enums.CustomItineraryStatusEnum;
import com.travelapp.itinerary_service.repositories.ItineraryRepository;
import com.travelapp.itinerary_service.util.ItineraryUtils;

@Service
public class ItineraryService {
	@Autowired
	ItineraryRepository itineraryRepository;

	public Itinerary createItinerary(Itinerary itinerary) {
		return itineraryRepository.save(itinerary);
	}

	public ItineraryDto getItineraryById(Long id) throws BadRequestException {
		Itinerary itinerary = itineraryRepository.findById(id).orElseThrow(()->{
           return new BadRequestException("Itinerary not found with given ID :: " + id);
        });
        return convertItineraryObjToDto(itinerary);
	}

	public void deactivateItineraryById(Long id) {
		Itinerary itinerary = itineraryRepository.findById(id).orElseThrow();
		itinerary.setActive(false);
        itineraryRepository.save(itinerary);
    }

	public List<Itinerary> fetchItinerariesBySearchCriteria(String location, boolean isVehicleIncluded,
			boolean isStayIncluded) {
		return itineraryRepository.findItinerariesByLocation(location.toUpperCase(),
				isVehicleIncluded, isStayIncluded);
	}

	public List<Itinerary> fetchItinerariesByDestinationType(String destinationType) {
        return itineraryRepository.fetchItinerariesByDestinationType(destinationType);
	}

	public List<Itinerary> fetchItinerariesByVehicle(String vehicleId) {
        return itineraryRepository.findItinerariesByVehicleId(vehicleId.toUpperCase());
	}

	public List<Itinerary> findItinerariesByStayIdAndRoomId(int roomId, Long stayId) {
        return itineraryRepository.fetchItinerariesByStayAndRoom(stayId, roomId);
	}

	public void updateVehiclePriceForItineraries(VehiclePriceUpdateDto vehiclePriceUpdateDto) {
		List<Itinerary> itineraries = itineraryRepository
				.findItinerariesByVehicleId(vehiclePriceUpdateDto.getVehicleId().toUpperCase());
		List<Itinerary> updatedItineraries = itineraries.stream().map(itinerary -> {
			itinerary.getVehicleinfo().setPrice(vehiclePriceUpdateDto.getUpdatedPrice());
			return itinerary;
		}).collect(Collectors.toList());
		itineraryRepository.saveAll(updatedItineraries);
    }

	public void updateStayPricesForItineraries(StayRoomPriceUpdateDto stayRoomPriceUpdateDto) {
        Set<RoomPriceDto> roomPriceDtoList = stayRoomPriceUpdateDto.getRoomPriceDtoList();
        Long stayId = stayRoomPriceUpdateDto.getStayId();
        for(RoomPriceDto roomPriceDto : roomPriceDtoList) {
            List<Itinerary> itineraries = itineraryRepository.fetchItinerariesByStayAndRoom(stayId,roomPriceDto.getRoomId());
            List<Itinerary> updatedItineraries = itineraries.stream().map(itinerary -> {
                itinerary.getStayInfo().setPrice(roomPriceDto.getPrice());
                return itinerary;
            }).toList();
            itineraryRepository.saveAll(updatedItineraries);
        }
    }

	public Itinerary updateItinerary(String id, Map<String, Object> updatedFields) {
		return itineraryRepository.updateItinerary(updatedFields, id);
	}

	public Itinerary customizeItineraryWithUpdatedStay(StayInfo stayInfo, long mobileNo, Long id) throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(id).orElseThrow();
		if (originalItinerary.isPredefined()) {
			Itinerary updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			updatedItinerary.setStayInfo(stayInfo);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary.setCustomerId(mobileNo);
			updatedItinerary.setPredefined(false);
			updatedItinerary.setBaseItineraryId(id);
			itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else if (originalItinerary.getStatus()
                .equalsIgnoreCase(CustomItineraryStatusEnum.DRAFT.name()) || originalItinerary.getStatus().equalsIgnoreCase(CustomItineraryStatusEnum.BOOKING_PENDING.name())) {
			originalItinerary.setStayInfo(stayInfo);
			originalItinerary = itineraryRepository.save(originalItinerary);
			return originalItinerary;
		} else {
			throw new Exception("Invalid Itinerary");
		}
	}

	public Itinerary customizeItineraryWithUpdatedVehicleInfo(VehicleInfo vehicleInfo, Long id, long mobileNo)
			throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(id).orElseThrow();
		if (originalItinerary.isPredefined()) {
			Itinerary updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			updatedItinerary.setCustomerId(mobileNo);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setPredefined(false);
			updatedItinerary.setBaseItineraryId(id);
			updatedItinerary.setVehicleinfo(vehicleInfo);
			updatedItinerary.setVehicleIncluded(true);
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary = itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else if (originalItinerary.getStatus()
				.equalsIgnoreCase(CustomItineraryStatusEnum.DRAFT.name())
				|| originalItinerary.getStatus().equalsIgnoreCase(CustomItineraryStatusEnum.BOOKING_PENDING.name())) {
			originalItinerary.setVehicleinfo(vehicleInfo);
			originalItinerary.setUpdatedDate(LocalDate.now());
			if (!originalItinerary.isVehicleIncluded()) {
				originalItinerary.setVehicleIncluded(true);
			}
			itineraryRepository.save(originalItinerary);
			return originalItinerary;
		} else {
			throw new Exception("Invalid Itinerary");
		}
	}

	public List<Itinerary> fetchItinerariesOfCustomer(long customerId) {
		return itineraryRepository.findItinerariesByCustomerid(customerId);
	}

	public Itinerary addTransfersToItinerary(Long itineraryId, long customerId, Set<Transfer> transfers)
			throws Exception {
		Itinerary originalItinerary = itineraryRepository.findById(itineraryId).orElseThrow();
		Itinerary updatedItinerary = null;
		if (originalItinerary.isVehicleIncluded()) {
			throw new Exception("Cannot add transfers, vehicle already included in the itinerary");
		} else if (originalItinerary.isPredefined()) {
			updatedItinerary = ItineraryUtils.deepCopy(originalItinerary);
			if (originalItinerary.getTransfers() == null) {
				updatedItinerary.setTransfers(transfers);
			} else {
				Set<Transfer> origTransfers = originalItinerary.getTransfers();
				origTransfers.addAll(transfers);
				updatedItinerary.setTransfers(origTransfers);
			}
			updatedItinerary.setBaseItineraryId(itineraryId);
			updatedItinerary.setCreatedDate(LocalDate.now());
			updatedItinerary.setUpdatedDate(LocalDate.now());
			updatedItinerary.setPredefined(false);
			updatedItinerary.setCustomerId(customerId);
			updatedItinerary.setStatus(CustomItineraryStatusEnum.DRAFT.name());
			updatedItinerary = itineraryRepository.insert(updatedItinerary);
			return updatedItinerary;
		} else {
			if (originalItinerary.getTransfers() == null) {
				originalItinerary.setTransfers(transfers);
			} else {
				Set<Transfer> origTransfers = originalItinerary.getTransfers();
				origTransfers.addAll(transfers);
				originalItinerary.setTransfers(origTransfers);
			}
			originalItinerary.setUpdatedDate(LocalDate.now());
			originalItinerary = itineraryRepository.save(originalItinerary);
			return originalItinerary;
		}

	}

	public void deleteCustomItinerariesUntouched() {
		itineraryRepository.deleteCustomItrUntouched();
	}

	public void archiveUnTouchedCustomItrFromPastSixMonths() {
		itineraryRepository.archiveUnTouchedCustItrFromPastSixMonths();
	}

	private ItineraryDto convertItineraryObjToDto(Itinerary itinerary) {
		double price = itinerary.getPrice();
		ItineraryDto itineraryDto = new ItineraryDto();
		itineraryDto.setActive(itinerary.isActive());
		itineraryDto.setId(itinerary.getId());
		itineraryDto.setDestinationTypes(itinerary.getDestinationTypes());
		itineraryDto.setDuration(itinerary.getDuration());
		itineraryDto.setGuideIncluded(itinerary.isGuideIncluded());
		itineraryDto.setImagesUri(itinerary.getImagesUri());
		itineraryDto.setItineraryDays(itinerary.getItineraryDays());
		itineraryDto.setPrice(itinerary.getPrice());
		itineraryDto.setItineraryName(itinerary.getItineraryName());
		itineraryDto.setLocation(itinerary.getLocation());
		itineraryDto.setPremiumType(itinerary.getPremiumType());
		itineraryDto.setVehicleIncluded(itinerary.isVehicleIncluded());
		itineraryDto.setStayIncluded(itinerary.isStayIncluded());
		if (itinerary.isStayIncluded()) {
			StayDetailDto stayDetailDto = new StayDetailDto();
			List<String> amenities = new ArrayList<>();
			amenities.add("bathroom");
			amenities.add("IronBoard");
			stayDetailDto.setAmenities(amenities);
			RoomDto roomDto = new RoomDto();
			roomDto.setBedType("single");
			roomDto.setRoomAmenities(amenities);
			roomDto.setNoOfBeds(2);
			roomDto.setRoomId(123);
			roomDto.setSize(250);
			stayDetailDto.setRoomDto(roomDto);
			AddressDto addressDto = new AddressDto();
			addressDto.setAddress_line1("line 1");
			addressDto.setAddress_line2("line 2");
			addressDto.setAddressId(1);
			addressDto.setCity("Hyderabad");
			addressDto.setState("Telangana");
			addressDto.setCountry("INDIA");
			addressDto.setZipCode(500072);
				stayDetailDto.setAddressDto(addressDto);
				stayDetailDto.setContactNo(1243423443);
				stayDetailDto.setHotelType("HOME STAY");
				stayDetailDto.setPrice(itinerary.getStayInfo().getPrice());
				stayDetailDto.setStayId("CHE_1234");
				itineraryDto.setStayDetailDto(stayDetailDto);
			price += stayDetailDto.getPrice();
			System.out.println(price);
		}
		if (itinerary.isVehicleIncluded()) {
			VehicleDetailDto vehicleDetailDto = new VehicleDetailDto();
			vehicleDetailDto.setNoOfSeats(4);
			vehicleDetailDto.setVehicleId(23);
			vehicleDetailDto.setVehicleType("SEDAN");
			vehicleDetailDto.setPrice(itinerary.getVehicleinfo().getPrice());
			itineraryDto.setVehicleDetailDto(vehicleDetailDto);
			price += vehicleDetailDto.getPrice();
			System.out.println(price);
		}
		System.out.println(price);
		itineraryDto.setPrice(price);
		return itineraryDto;
	}
}
