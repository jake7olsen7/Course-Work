import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeleteTester {
	public static void main(String[] args){
		try {
			System.out.println("Test");
			File fl = new File("tempTest.txt");
			fl.createNewFile();
			BTree tree = new BTree("tempTest.txt", 5, 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
