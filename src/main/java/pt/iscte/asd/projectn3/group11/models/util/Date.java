package pt.iscte.asd.projectn3.group11.models.util;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>Date</h1>
 * <p>The Date class hold the day of the month, the month and the year all in {@link Integer} varaibles</p>
 * <p>
 * @implNote Comparable
 * </p>
 */
public final class Date implements Comparable<Date> {

    private final int NOTHING = 0;
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date plus(int numberDays){
        int newDay = this.day + numberDays;
        int newMonth = this.month;
        int newYear = this.year;
        if(newDay > 30){
            newDay = newDay - 30;
            newMonth ++;
        }
        if(newMonth > 12){
            newMonth = newMonth - 12;
            newYear ++;
        }
        return new Date(newDay,newMonth,newYear);
    }

    /**
     * <p>Constructor from {@link String}</p>
     * @param date {@link String}
     */
    public Date(String date)
    {
        int year1;
        int month1;
        int day1;
        if(date.isEmpty())
        {
            day1 = NOTHING;
            month1 = NOTHING;
            year1 = NOTHING;

        }else {

            try {
                final String[] split = date.split("-");
                day1 = Integer.parseInt(split[0]);
                month1 = Integer.parseInt(split[1]);
                year1 = Integer.parseInt(split[2]);
            } catch (Exception e) {
                try {
                    day1 = Integer.parseInt(date.split("/")[0]);
                    month1 = Integer.parseInt(date.split("/")[1]);
                    year1 = Integer.parseInt(date.split("/")[2]);
                } catch (Exception e2) {
                    throw new IllegalArgumentException("DATE_IN_WRONG_FORMAT");

                }
            }
        }
        this.day = day1;
        this.month = month1;
        this.year = year1;
    }

    /**
     * Gets day.
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets month.
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets year.
     * @return year
     */
    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return day + "-" + month + "-" + year;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + year;
        result = result * prime + month;
        result = result * prime + day;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        final Date other = (Date) obj;
        return other.day == this.day && other.month == this.month && other.year == this.year;
    }

    @Override
    public int compareTo(@NotNull Date o) {
        return (
                (o.year - this.year)*364 +
                (o.month - this.month)*30 +
                (o.day - this.day)
        );
    }
}
