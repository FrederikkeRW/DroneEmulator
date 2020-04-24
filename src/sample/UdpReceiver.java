package sample;

import java.io.IOException;
import java.net.*;

public class UdpReceiver implements Runnable {
    private int inPort = 7007; //port im listening on
    private int broadcastToPort = 4000; //port we are sending echo back to
    private int broardcastFromPort = 7001;
    private DatagramSocket reciveSocket = null;
    private DatagramSocket broadcastSocket = null;
    private boolean receiveMessages = true;
    private boolean sendEcho = false;
    private Controller messageHandler;

   public UdpReceiver (Controller controller){
       this.messageHandler = controller;
   }


    private void setupSocket(){

        try {
            reciveSocket = new DatagramSocket(inPort);
            broadcastSocket = new DatagramSocket(broardcastFromPort, InetAddress.getLocalHost());
            broadcastSocket.setBroadcast(true);

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }



    private Message receivePacket(){
        byte[] inBuf = new byte[256];
        DatagramPacket recivePacket = new DatagramPacket(inBuf, inBuf.length);
        Message message = null;
        try {

            reciveSocket.receive(recivePacket);
            message = new Message(recivePacket.getData(), recivePacket.getLength());
            System.out.println("received: "+ message);
            messageHandler.handleMessage(message);
            //System.out.println("host address: "+recivePacket.getAddress().getHostAddress()+" port: "+String.valueOf(recivePacket.getPort()));


            if(sendEcho) {
                DatagramPacket broadcastPacket = new DatagramPacket(inBuf, inBuf.length, InetAddress.getByName("255.255.255.255"), broadcastToPort);
                broadcastSocket.send(broadcastPacket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
    @Override
    public void run() {

        System.out.println("Stared UdpReceiver Thread");
        if (reciveSocket == null){
           setupSocket();
        }
        while (receiveMessages){
            receivePacket();
        }
        reciveSocket.close();
        reciveSocket = null;
        broadcastSocket.close();
    }

    public boolean isReceiveMessages() {
        return receiveMessages;
    }

    public void setReceiveMessages(boolean receiveMessages) {
        this.receiveMessages = receiveMessages;
        //socket.close();
    }

    public boolean isSendEcho() {
        return sendEcho;
    }

    public void setSendEcho(boolean sendEcho) {
        this.sendEcho = sendEcho;
    }
}


