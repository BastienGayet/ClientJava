package Tcp;

import java.io.*;
import java.net.*;
public class TcpConnection {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    //connexion + creation flux
    public TcpConnection(String host, int port) throws IOException {
        socket= new Socket(host, port);
        dos = new DataOutputStream(new DataOutputStream(socket.getOutputStream()));
        dis = new DataInputStream(new DataInputStream(socket.getInputStream()));
    }

    //Recois reponse du serveur
    //retourne chaine de caractere
    public String Receive() throws IOException {
        StringBuffer buffer = new StringBuffer();
        boolean EOT = false;

        while (!EOT) // boucle lecture byte par byte
        {
            byte b1 = dis.readByte();
            System.out.println("b1 : --" + (char)b1 + "--");

            if(b1 == (byte) '#')
            {
                byte b2 = dis.readByte();
                System.out.println("b2 : --" + (char)b2 + "--");
                if(b2 ==(byte)')')
                {
                    EOT = true;
                }
                else
                {
                    buffer.append((char)b1);
                    buffer.append((char)b2);
                }
            }
            else
            {
                buffer.append((char)b1);
            }
        }

        return buffer.toString();


    }
    public void Send(String data) throws IOException {
        data += "#)";
        System.out.println("Client envoi : " + data);
        dos.write(data.getBytes());
        dos.flush();
        System.out.println("Réponse envoyée.");
    }
    //ferme connexion
    public void Close() throws IOException {
        socket.close();
    }
}
