import java.io.IOException;

public class Peer_Start4 {
	public static void main(String []args){
	Thread t1 = new Thread(new Runnable() {

		@Override
		public void run() {
		  try {
			Client4.main(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		            }
		});
		t1.start();
		Thread t2 = new Thread(new Runnable() {

		@Override
		public void run() {
		  try {
			ClientServer4.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		        }
		});
		t2.start();

}
}
