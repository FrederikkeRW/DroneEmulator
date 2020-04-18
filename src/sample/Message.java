package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String logTime;
    private String message;


    public Message(String message){
        /*
         Formatter: converts data into a String.
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        /*
        Gets current time
         */
        Date currentTime = new Date();
        /*
        Uses the formatter: to set current time to a string
         */
        logTime = formatter.format(currentTime);


        this.message = message;

    }
    /*
    Constructor to convert the byte[], to a String.
     */
    public Message (byte[] message, int length){
        this(new String(message, 0, length));
    }


    public String getMessage() {
        return message;
    }
    public String getLogTime() {
        return logTime;
    }

    @Override
    public String toString() {
        return "LogTime:: " + logTime + " Message:: " + message;
    }
}
