package pt.iscte.asd.projectn3.group11.models.util;

public class Date implements Comparable {

    private final int NOTHING = -1;
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date)
    {
        if(date.isEmpty())
        {
            this.day = NOTHING;
            this.month = NOTHING;
            this.year = NOTHING;
            return;
        }

        try
        {
            this.day = Integer.parseInt(date.split("-")[0]);
            this.month = Integer.parseInt(date.split("-")[1]);
            this.year = Integer.parseInt(date.split("-")[2]);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("DATE_IN_WRONG_FORMAT");
        }

    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

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
