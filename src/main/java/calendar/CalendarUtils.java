package calendar;

import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils {

    /**
     * <!-- Based on the day of the month on a given date --> Returns {@link Boolean#TRUE} if and only if the date is even
     *
     * @param dateAndTime {@link java.nio.file.attribute.FileTime} Date and time
     * @return Return <strong>true</strong> if date is even
     */
    public static boolean isDateIsEven(FileTime dateAndTime) {
        return dateAndTime.toMillis() % 2 == 0;
    }

    /**
     * <!-- Based on the day of the month on a given date --> Returns {@link Boolean#TRUE} if and only if the date is even
     *
     * @param dateAndTime {@link java.util.Date} Date and time
     * @return Return <strong>true</strong> if date is even
     */
    public static boolean isDateIsEven(Date dateAndTime) { // todo Create documentation for this method
        return dateAndTime.getTime() % 2 == 0;
    }

    /**
     * Based on the day of the month on a given date returns {@link Boolean#TRUE} if and only if the date is odd
     *
     * @param dateAndTime {@link java.util.Calendar} Date and time
     * @return Return <strong>true</strong> if date is odd
     */
    public static boolean isDateIsOdd(Calendar dateAndTime) {
        int dayOfTheMonth = dateAndTime.get(Calendar.DATE);
        return dayOfTheMonth % 2 != 0;
    }

    /**
     * Return date in CEST format
     *
     * @param dateAndTime {@link java.nio.file.attribute.FileTime} Date in ZULU format
     * @return Date in CEST format
     */
    public static Date convertDateToUTC(FileTime dateAndTime) {
        return new Date(dateAndTime.toMillis());
    }

}
