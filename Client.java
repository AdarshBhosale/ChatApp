import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;

public class Client {
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //constructor created
    public Client(){
        try {
            System.out.println("Sending Request to Server");
            socket = new Socket("127.0.0.1",7777);   //Server Computer IP Address  & port no
            System.out.println("Connection Done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e){

        }
    }

    public void startReading() {
        //thread - read karke deta rahega
        //lambda expression ()->{}
        Runnable r1 = () -> {
            System.out.println("Reader Started...");

            try {
            while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the Chat...");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
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
            try{
            while (!socket.isClosed()){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is Closed..");
            }catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Client...");
        new Client(); //constructor call
    }
}
