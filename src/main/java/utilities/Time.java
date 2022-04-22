package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public String getTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
