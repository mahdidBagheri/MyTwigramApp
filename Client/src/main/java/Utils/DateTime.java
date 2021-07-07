package Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTime {
    LocalDateTime dateTime = LocalDateTime.now();

    public String Now(){
        LocalDate Date = LocalDate.now();
        LocalTime Time = LocalTime.now();
        return Date.toString() + " " + Time.getHour() + ":" + Time.getMinute() + ":" + Time.getSecond();
    }

    public String Yesterday(){
        LocalDate Date = LocalDate.now().minusDays(1);
        LocalTime Time = LocalTime.now();
        return Date.toString() + " " + Time.getHour() + ":" + Time.getMinute() + ":" + Time.getSecond();
    }

}
