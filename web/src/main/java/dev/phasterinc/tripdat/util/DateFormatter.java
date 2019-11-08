package dev.phasterinc.tripdat.util;
/************************************************************
 * Name:  Philip Fuster                                     *
 * Project : Tripdat Travel Itinerary Application           *
 * Class : CMPS 450 Senior Project                          *
 * Date : 3/1/2019                                          *
 ************************************************************/

import java.time.LocalDate;

/**
 * Name: DateFormatter
 * Purpose: To format dates for display in the view
 */
public final class DateFormatter {


    /**
     * Name: DateFormatter
     * Purpose: Default Constructor for {@code DateFormatter}
     * <p>
     */
    private DateFormatter() {

    }

    // == public methods ==

    /**
     * Name: formatDate
     * Purpose: To format dates for presentation to the user.
     * Synopsis: Uses StringBuilder to build a string & {@code LocalDate} methods for
     * formatting.
     * <p>
     * @param date LocalDate to format.
     */
    public static String formatDate(LocalDate date) {
        StringBuilder builder = new StringBuilder();
        if (null != date) {
            if (date.getYear() != 9999) {
                builder.append(date.getDayOfWeek().toString().substring(0, 3)).append(",")
                        .append(date.getMonth().toString().substring(0, 3)).append(" ").append(date.getDayOfMonth());
            } else {
                builder.append("Unfiled Items");
            }
        }
        return builder.toString();

    }

}
