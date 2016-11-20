package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nicob on 09.11.2016.
 * converts a string to date
 */

public class DateConverter {
    private static DateConverter instance = new DateConverter();

    public static DateConverter getInstance() {
        return instance;
    }

    private DateConverter() {
    }

    public Date getDateFromString(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX"); //format of the date to parse
        Date date = null;

        try {
            date = dateFormat.parse(dateString.replaceAll("Z$", "+0000"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}