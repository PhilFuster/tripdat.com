package dev.phasterinc.tripdat;

import java.time.LocalDate;
import java.time.LocalTime;

/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

/**
 * Name: IDateAndTimeSpan
 * Purpose: An interface for date and time capabilities
 */
public interface IDateAndTimeSpan {
    public LocalDate getStartDate();

    public LocalDate getEndDate();

    public LocalTime getStartTime();

    public LocalTime getEndTime();
}
