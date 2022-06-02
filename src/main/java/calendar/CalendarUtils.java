package calendar;

import java.nio.file.attribute.FileTime;
import java.util.Calendar;

public class CalendarUtils {

    /**
     * <!-- Based on the day of the month on a given date --> Returns {@link Boolean#TRUE} if and only if the date is even
     *
     * @param fileTime File name
     * @return  Return <strong>true</strong> if date is even
     */
    public static boolean isDateIsEven(FileTime fileTime) {
        return fileTime.toMillis() % 2 == 0;
    }

    /**
     * Based on the day of the month on a given date returns {@link Boolean#TRUE} if and only if the date is odd
     *
     * @param calendar {@link java.util.Calendar}
     * @return Return <strong>true</strong> if date is odd
     */
    public static boolean isDateIsOdd(Calendar calendar) {
        int dayOfTheMonth = calendar.get(Calendar.DATE);

        return dayOfTheMonth % 2 != 0;
    }

}
