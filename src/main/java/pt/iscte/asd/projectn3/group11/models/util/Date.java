package pt.iscte.asd.projectn3.group11.models.util;

/**
 * <h1>Date</h1>
 * <p>The Date class hold the day of the month, the month and the year all in {@link Integer} varaibles</p>
 * <p>
 * @implNote Comparable
 * </p>
 */
public final class Date implements Comparable {

    private final int NOTHING = 0;
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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
        if(other.day == this.day && other.month == this.month && other.year == this.year)
            return true;

        return false;
    }

    @Override
    public int compareTo(Object o) {
        return this.hashCode() - o.hashCode();
    }
}
