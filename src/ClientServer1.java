import java.net.*;
import java.nio.charset.Charset;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientServer1 {
	static final int PORT = 15214;
	public static void main(String[] args) throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Peer1 Server Started");
		try
		{
			while(true)
			{
				Socket socket = s.accept();

				
				try
				{
					new upload_server(socket);
				} catch (IOException e) 
				{
					socket.close();
				}
			}
		}
		finally { s.close();}

}
}

class upload_server extends Thread
{
	private Socket socket;
	private DataInputStream  in   = null;
    private DataOutputStream out     = null;
    public upload_server(Socket s) throws IOException
	{
		socket =s;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		start();
	}
    public void run()
	{
	try {
		System.out.println(socket.getLocalSocketAddress());
		String Index = "/Users/appleapple/Desktop/harsha2/";
        	   String msg="";
        	 if (in.available()>0) 
        	 {
        		//System.out.println("Has req");
            	msg = in.readUTF();
            	System.out.println(msg);
            	String peer[] = msg.split("\\s");
            	//System.out.println(peer[2]);
            	
            	File tosend = new File(Index +peer[2]+".pdf");
            	 DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
                 Calendar cal = Calendar.getInstance();
                 msg = "P2P-CI/1.0 200 OK\n\n" +"Date: " +dateFormat.format(cal.getTime()) +"\n";
                 msg += "OS: "+ System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n" ;
                 msg+=  "Last Modified: " + dateFormat.format(tosend.lastModified()) + "\n";
                 msg+=  "Content-Length: " + tosend.length() + "\n";
                 msg+= "Content-Type: PDF\n";
                 out.writeUTF(msg);
                    	   
                        byte [] bytearray  = new byte [(int)tosend.length()];
                        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(tosend));
                        bin.read(bytearray,0,bytearray.length);
                        OutputStream output = socket.getOutputStream();
                        //System.out.println("Sending Files...");
                        output.write(bytearray,0,bytearray.length);
                        output.flush();
                        socket.close();
                        //System.out.println("File transfer complete");
                      
                       
                    }
           
			
           
}
		 catch(IOException e)
	      {
	         e.printStackTrace();
	      }
}
}