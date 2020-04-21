package sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiver implements Runnable {
    private int inPort = 7000;
    private DatagramSocket socket = null;
    private boolean receiveMessages = true;
    private Controller messageHandler;

   public UdpReceiver (Controller controller){
       this.messageHandler = controller;
   }
    private void setupSocket(){

        try {
            socket = new DatagramSocket(inPort);

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private Message receivePacket(){
        byte[] inBuf = new byte[256];
        DatagramPacket packet = new DatagramPacket(inBuf, inBuf.length);
        Message message = null;
        try {

            socket.receive(packet);
            message = new Message(packet.getData(), packet.getLength());
            System.out.println("received: "+ message);
            messageHandler.handleMessage(message);
            messageHandler.setEspIP(packet.getAddress().getHostAddress());
            messageHandler.setEspPort(String.valueOf(packet.getPort()));


            packet.getSocketAddress();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void run() {

        System.out.println("Stared UdpReceiver Thread");
        if (socket == null){
            setupSocket();
        }
        while (receiveMessages){
            receivePacket();
        }
        //socket.close();
    }

    public boolean isReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(boolean receiveMessages) {
        this.receiveMessages = receiveMessages;
        //socket.close();
    }
}


