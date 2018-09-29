Instruction to run this project(Peer-to-Peer System)

1)After you have Extracted the zip file, You can find 5 folders.
2)Peer1, Peer2, Peer3,Peer4 are the Source folders for the RFC's for the respective peers. 
3)Peer to Peer System Folder contains the code of the project.
Structure of the Code:
Each Peer has a Peer_Start File which upon running starts the respective Peer. 
Internally the Peer_Start File starts up the Peer Client and Peer Upload Server
We have 1 Central Server which acts the main Server

Before Running please specify the RFC's Source File Location to all the Peers
-> Get the location of the Peer1 Folder
-> Open the Client1.java File
->At the 16th line of the code specify the file location of the Peer1 to the String Index.
->Open the ClientServer1.java File
->At the line 51 of the code specify the file location of Peer1 to the String Index.

=> Repeat this For all the 4 Peers. 

After specifying the File Paths:
->First Run the Central Server.java file
->Then Run the Peer_Start1/2/3/4 in any order to start up all the Clients. 

Once you have all the Peers Active, You can perform all the necessary functions based on the options provided for you to select. 

Please Note: The RFC titles since needs to be inputed while Looking up, for the ease of testing the Titles have been set accordingly. By Listing all the RFC's you can know the Title which you can input while Looking up. 