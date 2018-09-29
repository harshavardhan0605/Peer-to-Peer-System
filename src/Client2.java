import java.net.*;
import java.io.*;
import java.util.*;

public class Client2 {
	private Socket socket            = null;
	private Socket socket1	= null;
    public Client2(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Peer2 Connected");
            //System.out.println(socket.getLocalSocketAddress());
            String Index = "/Users/appleapple/Desktop/harsha2/";
            // takes input from terminal
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in =new DataInputStream(inFromServer);
             OutputStream outToServer = socket.getOutputStream();
             DataOutputStream out =new DataOutputStream(outToServer);
            Scanner input = new Scanner(System.in);
            Scanner input2 = new Scanner(System.in);
            System.out.println("Registering on Server");
	            FileReader fileReader = new FileReader(Index+"peer2");
	            BufferedWriter bw = null;
	    			FileWriter fw = null;

	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            String[] parse = new String[100];
	            int cnt =0;
	            while((parse[cnt] = bufferedReader.readLine()) != null) {
	                cnt++;
	            }
	            int j;
	            bufferedReader.close();  
	            
        	   for(j=2;j<cnt-2;j++)
            {
        		   //System.out.println(parse[j]);
            String msg1="";
            msg1 = "ADD " + "RFC "+ parse[j] + " " + "P2P-CI/1.0\n" +"HOST: " + parse[0]+"\n" + "Port: "+ parse[1] + "\n" + "Title: "+ parse[j+1]+"\n\n";
            j = j+1;
            //System.out.println(msg1);
            out.writeUTF(msg1);
            }
                	   String msg1="";
                	   out.writeUTF(msg1);
                   
                
                String resp ="";
                resp = in.readUTF();
                //System.out.println("read");
                System.out.println(resp);

                String num,title;
            System.out.println("Select an option \n 1.LOOKUP & GET  2.LIST  3.LOGOUT \n");
            int z=0;
            while(z==0)
            {
            int i= input.nextInt();
           
            String msg ="";
            switch(i)
            {
            	            
            	case 1: 		System.out.println("Enter RFC Number");
            				 num = input.next();
            				System.out.println("Enter RFC Title");
            				 title = input2.nextLine();
            				msg = "LOOKUP " + "RFC " +num + " P2P-CI/1.0\n" +"HOST: " + parse[0]+"\n" + "Port: "+parse[1]+"\n" + "Title: " + title + "\n\n";
            	            out.writeUTF(msg);
            	            	String lreply;
            	            System.out.println(lreply =in.readUTF());
            	            if(lreply.equals("P2P-CI/1.0 404 NOT FOUND\n\n"))
            	            break;
            	             String parse7[] = lreply.split("\\s");
            	             int x,q=0;
                	         for(x=12;x<parse7.length;x++)
                	            {
    		        	        	 if(parse7[x].equals(parse[1]))
    		        	            {
    		        	            String reply6 =	"P2P-CI/1.0 400 BAD REQUEST\n\n";
    		        	            System.out.println(reply6);
    		        	            q++;
    		        	            break;
    		        	            }
    		        	        	 x = x+8;
                				}
                	         if(q>0) break;
            	            socket1 = new Socket("127.0.0.1", Integer.parseInt(parse7[12]));
            				msg = "GET " + "RFC "+ num  + " P2P-CI/1.0\n" + "Host: "+ parse[0] +"\n"+"OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n";
            				InputStream replyServer = socket1.getInputStream();
            				DataInputStream reply =new DataInputStream(replyServer);
            	             OutputStream sendToServer = socket1.getOutputStream();
            	             DataOutputStream send =new DataOutputStream(sendToServer);
            				send.writeUTF(msg);
            				String response =reply.readUTF();
            				System.out.println(response);
            				int filesize = 2022386, bytesRead, currentTot = 0;
            				 byte [] bytearray  = new byte [filesize];
            			        InputStream is = socket1.getInputStream();
            			        FileOutputStream fos = new FileOutputStream(Index+num+".pdf");
            			        BufferedOutputStream bos = new BufferedOutputStream(fos);
            			        bytesRead = is.read(bytearray,0,bytearray.length);
            			        currentTot = bytesRead;
            			 
            			        do {
            			           bytesRead =
            			              is.read(bytearray, currentTot, (bytearray.length-currentTot));
            			           if(bytesRead >= 0) currentTot += bytesRead;
            			        } while(bytesRead > -1 && is.available()>0);{
            			        bos.write(bytearray, 0 , currentTot);
            			        bos.flush();}
            			        bos.close();
            			         String store = "\n"+ num + "\n" + title ; 
            				socket1.close();
            				fw = new FileWriter(Index+"peer2",true);
            				bw = new BufferedWriter(fw);
            				bw.write(store);
            				bw.close();
            				fw.close();
            				 msg1 = "ADD " + "RFC "+ num + " " + "P2P-CI/1.0\n" +"HOST: " + parse[0]+"\n" + "Port: "+ parse[1] + "\n" + "Title: "+ title+"\n\n";
            				 out.writeUTF(msg1);
            	                String resp4 ="";
            	                resp4 = in.readUTF();
            	                System.out.println("read");
            	                System.out.println(resp4);
            				break;
            	case 2: msg = "LIST ALL "+ "P2P-CI/1.0\n" +"HOST: peer2.ncsu.edu\n" + "Port: 15216\n\n" ;
	            			out.writeUTF(msg);
	            			System.out.println(in.readUTF());
	            			break;
      
            				
            	case 3:  System.out.println("You are now disconnected");
            			msg = "Exit "+ parse[0];
            			out.writeUTF(msg);;
            			 socket.close();
            			 z++;
            			break;
            	default: System.out.println("Please enter a valid option\n");
            }
            if(z==0)
            System.out.println("Select an option \n 1.LOOKUP & GET  2.LIST  3.LOGOUT \n");
            }
        }
        catch(IOException e)
        {
           e.printStackTrace();
        }
    }
       
       
    
    public static void main(String args[])
    {
        Client2 client = new Client2("127.0.0.1", 7734);
    }

}






