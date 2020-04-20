package sample;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String logTime;
    private String message;

    private String command;
    private String param1;
    private String param2;

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

        String[] messageArray = message.split(" ");
        command = messageArray[0];

        if (messageArray.length > 1) {
            param1 = messageArray[1];
            if (messageArray.length > 2) {
                param2 = messageArray[2];
            }
        }
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

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "LogTime:: " + logTime + " Message:: " + message;
    }


}
