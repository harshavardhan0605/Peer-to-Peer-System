import java.net.*;
import java.io.*;
public class CentralServer  
{
	static final int PORT = 7734;
	public static void main(String[] args) throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Central Server Started");
		try
		{
			while(true)
			{
				Socket socket = s.accept();
				try
				{
					new Server(socket);
				} catch (IOException e) 
				{
					socket.close();
				}
			}
		}
		finally { s.close();}
	}
}
 class Server extends Thread {
	private Socket socket;
	private DataInputStream  in   = null;
    private DataOutputStream out     = null;
	public static peerlist head = null;
	public static rfclist head1 = null;
	public Server(Socket s) throws IOException
	{
		socket =s;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	start();
	}
	public void run()
	{
	try {	
		while(true)
		{
        	   String msg="";
        	 if (in.available()>0) 
        	 {
        		   //System.out.println("Has req");
            	msg = in.readUTF();
            String peer1[] = msg.split("\\s",10);
            if(peer1[0].equals("ADD"))
            {
        		String resp= "P2P-CI/1.0 200 OK\n\n";
        			while(msg.length()!=0)
            		{
        				System.out.println(msg);
        				String peer2[] = msg.split("\\s",10);
            			
            			peerlist x = new peerlist(peer2[5],Integer.parseInt(peer2[7]));
            			add_peer(x);
            			String peer3[] = peer2[9].split("\\n");
            			String peer4[] = peer2[2].split("\\.");
            			//System.out.println(peer4[0]);
            			rfclist y = new rfclist(Integer.parseInt(peer4[0]),peer3[0],peer2[5]);
            			add_rfc(y);
            			resp = resp + "RFC " + peer2[2] + " " + "RFC " + peer3[0] + " " + peer2[5] + " " + peer2[7] + "\n"; 
            			msg= in.readUTF();
            		}
            		resp += "\n";
            		//System.out.println(resp);
            		out.writeUTF(resp);	
            }
            
            if(peer1[0].equals("LIST"))
            {
            	System.out.println(msg);
            	String resp= show_rfc();
            	out.writeUTF(resp);
            }
            
            if(peer1[0].equals("LOOKUP"))
            {            	
            		System.out.println(msg);
            		String peer5 [] =  peer1[9].split("\\n");
            		//System.out.println(peer5[0]);
            		String resp = search(Integer.parseInt(peer1[2]),peer5[0]);
            		//System.out.println(resp);
            		out.writeUTF(resp);
            }
            if(peer1[0].equals("Exit"))
            		{
            			delete_peer(peer1[1]);
            		}
			
           }
	}
}
		 catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	}
void add_peer(peerlist x)
{
	 if(head==null)
     {
         head=x;
     }
     else
     {
         peerlist temp=head;
         while(temp.next!=null)
         {
             temp=temp.next;
         }
         temp.next=x;
     }
}
void delete_peer(String host)
{
	rfclist temp = head1;
	rfclist prev = temp;
	while(temp.next!= null)
	{
		if(temp.hostname.equals(host))
		{
			if(temp==head1)
			{
				head1 = temp.next;
			}
			else
			{
				prev.next = temp.next;
			}
			
		}
		prev = temp;
		temp = temp.next;
		
	}
	peerlist temp2 = head;
	peerlist prev2 = temp2;
	while(temp.next!= null)
	{
		if(temp2.hostname.equals(host))
		{
			if(temp2==head)
			{
				head = temp2.next;
			}
			else
			{
				prev2.next = temp2.next;
			}
			
		}
		prev2 = temp2;
		temp2 = temp2.next;
		
	}
}
void add_rfc(rfclist x)
{
    if(head1==null)
    {
        head1=x;
    }
    else
    {
        rfclist temp=head1;
        while(temp.next!=null)
        {
            temp=temp.next;
        }
        temp.next=x;
    }
}
void show_peers()
{
    peerlist temp=head;
    
    while(temp!=null)
    {
        System.out.println("Hostname: "+temp.hostname+"\t Port No: "+temp.port);
        temp=temp.next;
    }
}
String show_rfc()
{
    rfclist temp=head1;
    String resp ="P2P-CI/1.0 200 OK\n\n";
    while(temp!=null)
    {
    		resp += "RFC "+temp.rfc_num +" "+temp.rfc_title+" "+temp.hostname ;
    		int port = peer_search(temp.hostname);
		resp += " "+ port + "\n";
    		
       // System.out.println("RFC number"+temp.num+"\ttitle"+temp.title+"\tHostname"+temp.hostname);
        temp=temp.next;
    }
    return resp;
}
String search (int num, String title)
{
	rfclist temp = head1;
	String resp ="";
	//System.out.println("searching");
	while(temp!=null)
	{
		if(num == temp.rfc_num && title.equals(temp.rfc_title))
		{
			//System.out.println("coming into loop here");
			resp += "RFC "+temp.rfc_num +" RFC "+temp.rfc_title+" Hostname "+temp.hostname ;
			int port = peer_search(temp.hostname);
			resp += " Port " + port + "\n";
			
			
		}
		temp = temp.next;
	}
	if(resp== "")
	{
		resp = "P2P-CI/1.0 404 NOT FOUND\n\n" ;
	}
	else
		resp = "P2P-CI/1.0 200 OK\n\n" + resp + "\n";

	return resp;
}
int peer_search(String name)
{
	peerlist temp = head;
	int port = 0;
	while(temp!=null)
	{
		if(temp.hostname == name)
		{
			port = temp.port;
			break;
			//return port;
		}
		temp = temp.next;
		
	}
	return port;
	
}

}

class peerlist
{
	peerlist next= null;
	String hostname;
	int port;
	public peerlist(String x,int y)
	{
		this.hostname=x;
		this.port = y;
	}
}

class rfclist
{
	rfclist next = null;
	int rfc_num;
	String rfc_title;
	String hostname;
	public rfclist(int x, String y, String z)
	{
		this.rfc_num = x;
		this.rfc_title =y;
		this.hostname =z;
	}
}
