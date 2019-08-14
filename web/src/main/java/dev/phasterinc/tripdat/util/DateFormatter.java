package dev.phasterinc.tripdat.util;

import java.time.LocalDate;

public final class DateFormatter {



    private DateFormatter() {

    }

    // == public methods ==
    public static String formatDate(LocalDate date) {
        StringBuilder builder = new StringBuilder();
        if(null != date) {
            if(date.getYear() != 9999 ) {
                builder.append(date.getDayOfWeek().toString().substring(0, 3)).append(",")
                        .append(date.getMonth().toString().substring(0,3)).append(" ").append(date.getDayOfMonth());
            } else {
                builder.append("Unfiled Items");
            }
        }
        return builder.toString();

    }

}
