import java.io.IOException;
import java.net.*;
import java.io.*;

public class Server {
    ServerSocket server;
    Socket socket;

    //Data Read for using br and Write using out
    BufferedReader br;
    PrintWriter out;

    public Server() //Constructor Created
    {
        try {
            server=new ServerSocket(7777);
            System.out.println("Server is Ready to accept Connection..");
            System.out.println("Waiting..");
            socket=server.accept(); //accept connection from client (Socket)

            //read data
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //write data
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        //thread - read karke deta rahega
        //lambda expression ()->{}
        Runnable r1 = () -> {
            System.out.println("Reader Started...");
            try{
            while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the Chat...");

                        socket.close(); //close terminal

                        break;
                    }
                    System.out.println("Client : " + msg);
                }
            }catch (Exception e){
                //e.printStackTrace();
                System.out.println("Connection is Closed..");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        //thread - data user lega and the send karega client tak
        Runnable r2=()->{
            System.out.println("Writer Started...");

            try {
                while (!socket.isClosed()) {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();

                        out.println(content);
                        out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                    }
                }catch(Exception e){
                    //e.printStackTrace();
                System.out.println("Connection is Closed..");
                }
            };
        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("This is Server...going to start server");
        new Server(); //call server
    }
}
