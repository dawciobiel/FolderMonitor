package calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CalendarUtilsTest {

    @Test
    void isDateIsEven() {
        // given
        Date dateOne = new Date();
        long milliseconds = dateOne.getTime();
        milliseconds += 1L;
        Date dateTwo = new Date(milliseconds);

        // when
        boolean resultOne = CalendarUtils.isDateIsEven(dateOne);
        boolean resultTwo = CalendarUtils.isDateIsEven(dateTwo);

        // then
        Assertions.assertNotSame(resultOne, resultTwo);
    }

    @Test
    void isDateIsParity() {
        // given
        Date date = new GregorianCalendar(2014, Calendar.JUNE, 2).getTime(); // Even date

        // when
        boolean result = CalendarUtils.isDateIsEven(date);

        // then
        Assertions.assertTrue(result);
    }

    @Test
    void isDateIsOdd() {
        // given
        Date date = new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime(); // Odd date

        // when
        boolean result = CalendarUtils.isDateIsEven(date);

        // then
        Assertions.assertTrue(result);
    }

}














