package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import java.awt.*;

public class Controller {

    /*
    our FXML elements we need to grab.
     */
    @FXML
    TableView<Message> inputLogTable;

    @FXML
    ListView<String> listViewVertices;

    @FXML
    ToggleButton toggleBtnDrone;

    @FXML
    Canvas droneCanvas;

    @FXML
    Label espIP;

    @FXML
    Label espPort;

    @FXML
    ToggleButton toggleBtnEchoServer;


    private UdpReceiver udpReceiver;
    private GraphicsContext graphicsContext;
    private double currentX;
    private double currentY;
    private double speed = 8;

    public void initialize(){
        this.graphicsContext = droneCanvas.getGraphicsContext2D();

        if(udpReceiver.isSendEcho()) {
            toggleBtnEchoServer.setText("ON");
        } else{
            toggleBtnEchoServer.setText("OFF");
        }
        /*
        Now the canvas is initialized
        Start udpReceiver thread
         */
        new Thread(udpReceiver).start();
    }

    public Controller() {
        udpReceiver = new UdpReceiver(this);
    }

    public void toggleBtnDrone(ActionEvent actionEvent) {
        System.out.println("Toggle Drone button");
        if (udpReceiver.isReceiveMessages()) {
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

    public void toggleBtnEchoServer(ActionEvent actionEvent){
        if(udpReceiver.isSendEcho()){
            udpReceiver.setSendEcho(false);
            toggleBtnEchoServer.setText("OFF");
        } else {
            udpReceiver.setSendEcho(true);
            toggleBtnEchoServer.setText("ON");
        }
    }

    public void clearTable(ActionEvent actionEvent) {
        //System.out.println("Toggle Clear table button");
        inputLogTable.getItems().clear(); // clears table
    }

    public void clearList (ActionEvent actionEvent){
        listViewVertices.getItems().clear();
    }

    public void handleMessage(Message message) {
        if (inputLogTable != null) {
            inputLogTable.getItems().add(0, message);
        }

        if (espIP != null){
            Platform.runLater(() ->  espIP.setText("bla bla"));
        }



        String command = message.getCommand();
        switch (command){
            case "init":
                String x = message.getParam1();
                String y = message.getParam2();
                currentX = Double.parseDouble(x);
                currentY = Double.parseDouble(y);
                //drawCircle();
                Platform.runLater(() -> drawCircle());
                break;

            case "moveup":
                clearCircle();
                currentY -= speed;
                //drawCircle();
                Platform.runLater(() -> drawCircle());
                break;

            case "movedown":
                clearCircle();
                currentY += speed;
                //drawCircle();
                Platform.runLater(() -> drawCircle());
                break;

            case "moveleft":
                clearCircle();
                currentX -= speed;
                //drawCircle();
                Platform.runLater(() -> drawCircle());
                break;

            case "moveright":
                clearCircle();
                currentX += speed;
                //drawCircle();
                Platform.runLater(() -> drawCircle());
                break;
        }
    }

    @FXML
    void mouseClickedOnCanvas(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            currentX = event.getX();
            currentY = event.getY();
            drawCircle();
            // graphicsContext.strokeOval(x - 30, y - 30, 30, 30);
            //listViewVertices.getItems().add("Vertex {x=" + x + ", y= " + y + "}");
        }
    }

    private void drawCircle () {
        if (graphicsContext != null) {
            graphicsContext.strokeOval(currentX - 30, currentY - 30, 30, 30);
        }
        if (listViewVertices != null) {
            listViewVertices.getItems().add("Vertex {x=" + currentX + ", y= " + currentY + "}");
        }
    }

    private void clearCircle () {
        if (graphicsContext != null) {
            graphicsContext.clearRect(currentX - 30, currentY - 30, 40, 40);
        }
    }

}

