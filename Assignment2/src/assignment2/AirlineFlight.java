package assignment2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Kamil Zambrowski
 * Date created: 10/7/19
 * I Pledge My Honor That I Have Abided By The Stevens Honor System
 */
public class AirlineFlight {

    public enum WeekDay {
        MONDAY("M"), TUESDAY("T"), WEDNESDAY("W"), THURSDAY("R"),
          FRIDAY("F"), SATURDAY("Sa"), SUNDAY("Su");

        private final String abbr;

        WeekDay(String abbr) {
            this.abbr = abbr;
        }

        public static WeekDay toWeekDay(String abbr) throws IllegalArgumentException {
            for (WeekDay day : WeekDay.values()) {
                if (day.abbr.equals(abbr)) {
                    return day;
                }
            }
            throw new IllegalArgumentException("Invalid abbreviation");
        }

        public String toString() {
            return abbr;
        }
    }

    private String code;
    private int number;
    private WeekDay [] flightDays;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public WeekDay[] getFlightDays() {
        return flightDays;
    }

    public String getFlightDaysAsString() {
        StringBuilder builder = new StringBuilder();
        for (WeekDay day : flightDays) {
            builder.append(day.toString());
        }
        return builder.toString();
    }

    public void setFlightDays(String flightDaysStr) throws IllegalArgumentException {
        String [] tokens = flightDaysStr.split(",");
        List<WeekDay> days = new ArrayList<>();
        for (String tok : tokens) {
            days.add(WeekDay.toWeekDay(tok.trim()));
        }
        this.flightDays = days.toArray(new WeekDay[0]);
    }

    public String toString() {
        return String.format("%s%d flies on %s",this.code, this.number, Arrays.toString(this.flightDays));
    }



}
