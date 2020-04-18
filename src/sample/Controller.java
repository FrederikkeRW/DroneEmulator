package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

public class Controller {

    /*
    our FXML elements we need to grab.
     */
   @FXML
    TableView<Message> inputLogTable;
   @FXML
    ToggleButton toggleBtnDrone;

   private UdpReceiver udpReceiver;

   public Controller (){
       udpReceiver = new UdpReceiver(this);
       new Thread(udpReceiver).start();
   }

    public void toggleBtnDrone(ActionEvent actionEvent) {
        System.out.println("Toggle Drone button");
        if (udpReceiver.isReceiveMessages()){
            /*
            Kill udpReceiver thread
             */
            udpReceiver.setReceiveMessages(false);
            toggleBtnDrone.setText("OFF");
        } else {
            /*
            Start udpReceiver thread
             */
            udpReceiver.setReceiveMessages(true);
            new Thread(udpReceiver).start();
            toggleBtnDrone.setText("ON");
        }
    }

    public void toggleBtnBroadcast(ActionEvent actionEvent) {
        System.out.println("Toggle Broadcast button");
    }

    public void handleMessage(Message message){
       if (inputLogTable != null){
           inputLogTable.getItems().add(0, message);
       }
    }
}
