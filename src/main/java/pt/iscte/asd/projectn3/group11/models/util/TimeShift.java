package pt.iscte.asd.projectn3.group11.models.util;

public enum TimeShift {
    NOTHING(0),
    HOUR_07H00(1),
    HOUR_07H30(2),
    HOUR_08H00(3),
    HOUR_08H30(4),
    HOUR_09H00(5),
    HOUR_09H30(6),
    HOUR_10H00(7),
    HOUR_10H30(8),
    HOUR_11H00(9),
    HOUR_11H30(10),
    HOUR_12H00(11),
    HOUR_12H30(12),
    HOUR_13H00(13),
    HOUR_13H30(14),
    HOUR_14H00(15),
    HOUR_14H30(16),
    HOUR_15H00(17),
    HOUR_15H30(18),
    HOUR_16H00(19),
    HOUR_16H30(20),
    HOUR_17H00(21),
    HOUR_17H30(22),
    HOUR_18H00(23),
    HOUR_18H30(24),
    HOUR_19H00(25),
    HOUR_19H30(26),
    HOUR_20H00(27),
    HOUR_20H30(28),
    HOUR_21H00(29),
    HOUR_21H30(30),
    HOUR_22H00(31),
    HOUR_22H30(32),
    HOUR_23H00(33),
    HOUR_23H30(34),
    HOUR_24H00(35);


    private final int id;

    TimeShift(int id) {
        this.id = id;
    }
    public static TimeShift getById(int id) {
        final TimeShift[] values = TimeShift.values();
        return values[id];
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString()
    {
        int dec = this.id;
        if(dec == -1) {
            return "Nothing";
        }else{
            String minutes = "00";
            if (this.id % 2 == 0) {
                minutes = "30";
                dec--;
            }
            String hours = String.valueOf(dec / 2 + 7);
            if (hours.length() == 1) {
                hours = "0" + hours;
            }
            return hours + ":" + minutes + ":" + "00";
        }
    }

    public static TimeShift stringToClassTime(String timeString)
    {
        if(timeString.isEmpty()) return NOTHING;

        int id;
        try
        {
            final int hour = Integer.parseInt(timeString.split(":")[0]);
            final String minutesString = timeString.split(":")[1];
            id = (hour - 7 + 1) * 2 + (minutesString.equals("30") ? 0 : -1);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("TIME_IN_WRONG_FORMAT");
        }


        for(TimeShift timeShift : TimeShift.values())
        {
            if(timeShift.id == id)
            {
                return timeShift;
            }
        }
        throw new IllegalArgumentException("CLASS_TIME_DOESNT_EXIST");
    }

}
