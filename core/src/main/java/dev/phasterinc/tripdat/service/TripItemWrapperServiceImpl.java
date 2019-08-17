package dev.phasterinc.tripdat.service;

import dev.phasterinc.tripdat.model.*;
import dev.phasterinc.tripdat.model.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
public class TripItemWrapperServiceImpl implements TripItemWrapperService {

    // == fields ==
    TripdatTripItemService tripItemService;

    // == constructors ==
    @Autowired
    public TripItemWrapperServiceImpl(TripdatTripItemService tripItemService) {
        this.tripItemService = tripItemService;
    }



    // == public methods ==
    /**
     * Name: getFormattedDate
     * Purpose: Format a date String in the format of startMonth day[,] [startYear] - [endMonth] endYear
     *          ex: Apr 25 - May 23, 2019
     *          ex: Dec 20, 2018 - Jan 5, 2019
     * @param startDate - start date of type LocalDate
     * @param endDate - end date of type LocalDate
     * @return formattedDate:String - the properly formatted date
     */
    @Override
    public String getFormattedDate(LocalDate startDate, LocalDate endDate) {
        StringBuilder formattedDate = new StringBuilder();
        String month = startDate.getMonth().toString();


        // value: ex: Feb 05
        formattedDate.append(formatMonthString(month)).append(" ").append(startDate.getDayOfMonth());

        if(startDate.getYear() != endDate.getYear()) {
            formattedDate.append(", ").append(startDate.getYear());
        }
        formattedDate.append(" - ");

        if(startDate.getMonth().equals(endDate.getMonth())) {
            formattedDate.append( endDate.getDayOfMonth());
        }
        else {
            formattedDate.append(formatMonthString(endDate.getMonth().toString())).append(" ").append(endDate.getDayOfMonth());
        }

        formattedDate.append(", ").append(endDate.getYear());

        return formattedDate.toString();
    }

    /**
     * Name: formatMonthString
     * Purpose: return the first three letters of the month with only the first letter capitalized
     * @param  - String to be formatted
     * @return String - formatted Month string
     */
    @Override
    public String formatMonthString(String month) {
        StringBuilder formattedStartMonth = new StringBuilder();
        formattedStartMonth.append(month.charAt(0));
        formattedStartMonth.append( Character.toLowerCase(month.charAt(1)));
        formattedStartMonth.append( Character.toLowerCase(month.charAt(2)));
        return formattedStartMonth.toString();
    }

    /**
     * Name: getDurationOfTrips
     * Purpose: To calculate the duration of each trip in a collection and format it into a string
     * @param trips - List of trips to calculate their durations.
     * @return durationOfTrips - A list of strings containing the duration of each trip.
     */
    @Override
    public List<String> getDurationOfTrips(List<TripdatTrip> trips) {
        // change the date format for each date to be displayed on the view
        List<String> durationOfTrips = new ArrayList<>();
        for (TripdatTrip trip : trips) {
            LocalDate startDate = trip.getTripStartDate();
            LocalDate endDate = trip.getTripEndDate();

            StringBuilder durationOfTrip = new StringBuilder();
            String daysOfTrip = Long.toString(startDate.until(endDate, ChronoUnit.DAYS));
            durationOfTrip.append("(").append(daysOfTrip).append(" day");
            if(Long.parseLong( daysOfTrip )!= 1) {
                durationOfTrip.append("s");
            }
            durationOfTrip.append(")");
            durationOfTrips.add(durationOfTrip.toString());

        }

        return durationOfTrips;
    }

    /**
     * Name: getFormattedDateStrings
     * Purpose: to populate a collection of Strings with the formatted date strings.
     *          Calls helper function getFormattedDate to format the dates.
     * @param trips - Collection of Trips to iterate over and get the format its date
     * @return formattedDateStrings - collection of Strings containing the formatted dates.
     */
    @Override
    public List<String> getFormattedDateStrings(List<TripdatTrip> trips) {

        List<String> formattedDateStrings = new ArrayList<>();

        for(TripdatTrip trip: trips) {
            LocalDate startDate = trip.getTripStartDate();
            LocalDate endDate = trip.getTripEndDate();
            String formattedDate = getFormattedDate(startDate, endDate);
            formattedDateStrings.add(formattedDate);
        }
        return formattedDateStrings;
    }

    /**
     * Name: getNextUpItemsInItemWrapper
     * Purpose: To generate a collection of TripItemWrapper type to order all trip Items by date and time.
     * @param nextTrip - TripdatTrip to iterate over and create the collection of TripItemWrapper.
     * @return items - Collection of TripItemWrapper
     *
     *
     *
     */
    @Override
    public List<TripItemWrapper> getNextUpItemsInItemWrapper(TripdatTrip nextTrip) {
        Set<TripdatTripItem> items = tripItemService.getMax4ItemsById(nextTrip.getTripId());

        return unwrapTripItemsIntoWrappers(items);
    }

    @Override
    public List<TripItemWrapper> getItemsInItemWrapper(TripdatTrip trip) {
        Set<TripdatTripItem> items = tripItemService.getItemsByTripId(trip.getTripId());
        List<TripItemWrapper> wrappers = unwrapTripItemsIntoWrappers(items);
        return wrappers;
    }

    /**
     * name: unwrapTripItemsIntoWrappers
     * Purpose: iterate over a collection of items, if an item has segments, unwrap the
     *          segments into a wrapper. Each item and segment type have different fields
     *          hence a switch is used to wrap the different items and segments into an item wrapper.
     * @param items - collection of TripItems to unwrap
     * @return
     *
     * Algorithm:
      *      Step 1: declare and initialize items, a List of type TripdatTripItems and itemWrappers, a List of TripItemWrapper
     *      Step 2: for each TripItem item in items
     *      Step 3:    if item.itemTypeCode is of CruiseInformation, Flight, Rail or GeneralTransportation
     *      Step 4:       if item has any segments.
     *      Step 6:                create a TripItemWrapper
     *      Step 5:            for each segment
     *      Step 7:                insert into items collection
     *      Step 8:        if no segments
     *      Step 9:            Create TripItemWrapper
     *      Step 10:           insert tripItemWrapper to the items collection
     */
    @Override
    public List<TripItemWrapper> unwrapTripItemsIntoWrappers(Set<TripdatTripItem> items) {
        List<TripItemWrapper> itemWrappers = new ArrayList<>();
        if(CollectionUtils.isEmpty(items)) {
            log.info("TripItemWrapperServiceImpl:unwrapTripItemsIntoWrappers - No items in the trip passed.");
            return itemWrappers;
        }
        for(TripdatTripItem item : items) {
            log.info("item: {}",item);

            switch (item.getType())
            {
                // flightInformation
                case "F":
                    // Do Stuff for flight
                    Flight flightInfo = (Flight) item;

                    // for each segment in flightInfo.flightSegments
                    for(FlightSegment segment : flightInfo.getFlightSegments()) {
                        TripItemWrapper wrapper = TripItemWrapper.builder()
                                .tripItemTypeCode(item.getType())
                                .tripItem(FlightSegmentDto.buildDto(segment))
                                .startDate(segment.getFlightDepartureDate())
                                .startTime(segment.getFlightDepartureTime())
                                .confirmationNumber(flightInfo.getFlightConfirmationNumber())
                                .supplier(SupplierDto.buildDto(flightInfo.getSupplier()))
                                .travelAgency(TravelAgencyDto.buildDto(flightInfo.getTravelAgency()))
                                .bookingDetail(BookingDetailDto.buildDto(flightInfo.getBookingDetail()))
                                .id(item.getTripItemId())
                                .notes(item.getTripItemNote())
                                .endDate(segment.getFlightArrivalDate())
                                .endTime(segment.getFlightArrivalTime())
                                .attendees(AttendeeDto.buildDtoList(flightInfo.getAttendees()))
                                .build();

                        itemWrappers.add(wrapper);
                    }

                    break;
                // CruiseInformation
                case "C" :
                    // Do Stuff for Cruise
                    break;
                // General Transportation
                case "G" :
                    // Do stuff for general trans
                    break;
                // RailInformation
                case "RA" :
                    // do stuff for Rail
                    break;
                // Restaurant
                case "R" :
                    // do stuff for restaurant
                    break;
                // Meeting
                case "M" :
                    // do stuff for meeting
                    break;
                // Parking
                case "P" :
                    // do stuff for parking
                    break;
                // Car Rental
                case "CR" :
                    // do stuff for carRental
                    CarRental rental = (CarRental) item;
                    TripItemWrapper wrapper = TripItemWrapper.builder()
                            .tripItemTypeCode(item.getType())
                            .tripItem(CarRentalDto.buildDto(rental,AttendeeDto.buildDtoList(rental.getAttendees())))
                            .startDate(rental.getCarRentalPickUpDate())
                            .startTime(rental.getCarRentalPickUpTime())
                            .confirmationNumber(rental.getCarRentalConfirmationNumber())
                            .supplier(SupplierDto.buildDto(rental.getSupplier()))
                            .travelAgency(TravelAgencyDto.buildDto(rental.getTravelAgency()))
                            .bookingDetail(BookingDetailDto.buildDto(rental.getBookingDetail()))
                            .id(item.getTripItemId())
                            .notes(item.getTripItemNote())
                            .endDate(rental.getCarRentalDropOffDate())
                            .endTime(rental.getCarRentalDropOffTime())
                            .attendees(AttendeeDto.buildDtoList(rental.getAttendees()))
                            .build();
                    itemWrappers.add(wrapper);
                    break;
                // Lodging
                case "L" :
                    break;
                // Activity
                case "Y" :
                    break;
                default:
                    break;
            }

        }

        return itemWrappers;
    }

    /**
     * Name: orderItemWrappersByAscDateAndTime
     * Purpose: To order the passed collection of TripItemWrappers by Ascending Date and Time
     * @param wrappers - List of TripItemWrapper, user's items in wrappers
     *
     */
    @Override
    public void orderItemWrappersByAscDateAndTime(List<TripItemWrapper> wrappers) {
        //wrappers.sort((TripItemWrapper item1, TripItemWrapper item2)->item1.getStartDate().compareTo(item2.getStartDate()));
        log.info("Wrappers passed to orderItemWrappersByAscnDateAndTime{}",wrappers.size());
        Collections.sort(wrappers, Comparator.nullsLast(Comparator.comparing(TripItemWrapper::getStartDate, Comparator.nullsLast(LocalDate::compareTo)).thenComparing(TripItemWrapper::getStartTime ,Comparator.nullsLast(LocalTime::compareTo))));
        wrappers.forEach((item)->log.debug("Start Date: " +
                item.getStartDate() + " Start Time: " + item.getStartTime() ));
    }

    /**
     * name: getWrappersInMapByDate
     * purpose: Creates a HashMap<LocalDate, ArrayList<TripItemWrapper>> itemMap. Key is a LocalDate, and the value
     *          is an ArrayList<TripItemWrapper>. This HashMap will be used in the view. A template will iterate over
     *          the HashMap and get each ArrayList for each day and print the items in that list. The items are already
     *          ordered by Date and time.
     * @param wrappers - List TripItemWrapper, contains User's items in wrappers
     * @param startDate - LocaDate, startDate of trip
     * @param endDate - LocalDate, endDate of trip
     * @return HashMap<LocalDate, List<TripItemWrapper>>, HashMap with key of a Day during the trip
     */
    @Override
    public HashMap<LocalDate, List<TripItemWrapper>> getWrappersInMapByDate(List<TripItemWrapper> wrappers, LocalDate startDate, LocalDate endDate) {
        long durationOfTrip = startDate.until(endDate, ChronoUnit.DAYS);
        LocalDate currDate = startDate;
        HashMap<LocalDate, List<TripItemWrapper>> itemsMap = new HashMap<>();
        // initialize map
        for(int i = 0; i < durationOfTrip; ++i) {
            log.debug("Adding date: " + currDate.toString());
            itemsMap.put( currDate, new ArrayList<TripItemWrapper>() );
            currDate = currDate.plus(1, ChronoUnit.DAYS);
            log.debug("Date Incremented to: " + currDate.toString());
        }

        // putting an arrayList in itemsMap for items with null start Dates
        itemsMap.put(LocalDate.of(9999,12,31), new ArrayList<TripItemWrapper>());


        /*for(TripItemWrapper item : wrappers) {
            log.info("startDate to search for in map: " + item.getStartDate());
            System.out.println( itemsMap.get(item.getStartDate()) );
            itemsMap.get(item.getStart())
        }*/
        // for each itemWrapper get the ArrayList from itemsMap with key item.startDate, add item to that ArrayList
        // if that date hasn't been added to the map yet create an arrayList for tripItems scheduled for that day
        wrappers.forEach((item)-> {
            List<TripItemWrapper> items;
            if(item.getStartDate() == null) {
               items = itemsMap.getOrDefault(LocalDate.of(9999,12,31), new ArrayList<>());
            }else {
                items = itemsMap.getOrDefault(item.getStartDate(), new ArrayList<>());
            }
            log.info("Items from getWrappersInMap {}",items.toString());
            items.add(item);

        } );





        return itemsMap;
    }

    @Override
    public TripItemWrapper getOneItemById(Long tripItemId) {
        return null;
    }
}
