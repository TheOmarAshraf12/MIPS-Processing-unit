import java.util.ArrayList;

public class DataMemory {

	byte[] data = new byte[2048];

	public DataMemory() {

		//byte x = 0;

		
		
		for (int i = 0; i < data.length; i++) {
			data[i] = 0;
			//x++;
		}
		data[0] = 64 ;
		data[1] = -64;

	}
}
