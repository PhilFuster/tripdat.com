package dev.phasterinc.tripdat.util;

import java.time.LocalDate;

public final class DateFormatter {



    private DateFormatter() {

    }

    // == public methods ==
    public static String formatDate(LocalDate date) {
        StringBuilder builder = new StringBuilder();
        if(null != date) {
            builder.append(date.getDayOfWeek().toString().substring(0, 3)).append(",")
                    .append(date.getMonth().toString().substring(0,3)).append(" ").append(date.getDayOfMonth());
        }
        return builder.toString();

    }

}
