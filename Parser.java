import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	
	
	

	public  void readfile(Processor p,String path) throws IOException{
	    String[] result = new String [1000];
	        BufferedReader  br=new BufferedReader(new FileReader(path));
	        String st;
	        int i=0;
	        while((st=br.readLine())!=null){
	          short temp= this.parseInstruction(st);
	          p.instMem.instructions[i]=temp;
	          p.instructionMemoryPrint[i] = st;
	            i++;
	        }
	}	
	
	
	private short parseInstruction(String st) {
		String[] instruction = st.split(" ");
		
		String s = "";
		
		
		switch (instruction[0]) {
		case "ADD": s+="0000";
		
			        break;
		case "SUB": s+="0001";
        break;
		case "MUL": s+="0010";
        break;
		case "MOVI": s+="0011";
        break;
		case "BEQZ": s+="0100";
        break;
		case "ANDI": s+="0101";
        break;
		case "EOR": s+="0110";
        break;
		case "BR": s+="0111";
        break;
		case "SAL": s+="1000";
        break;
		case "SAR": s+=("1001");
        break;
		case "LDR": s+=("1010");
        break;
		case "STR": s+=("1011");
        break;
			
	}
		
		s+=(getregister(instruction[1]));
		
		if(instruction[0] .equals("ADD") ||instruction[0].equals("SUB") || instruction[0].equals("MUL") || instruction[0].equals("EOR") || instruction[0].equals("BR")) {
			s=s+getregister(instruction[2]);
		
		}
		else {
			int tmp;
			
			String tmp2;
			
			
			tmp = (Integer.parseInt(instruction[2]));
			
			s=s+getImm(tmp);
			
		}
		
		
		short ins =  (short)(Integer.parseInt(s,2));
		return ins;
	}


	/*public short parseInstruction(String x) {
		ArrayList out = new ArrayList <String> ();
		for(int i=0 ; i<=x.length;i++) {
			String[] instruction = x[i].split(" ");
			String s = "";
			
			switch (instruction[0]) {
			case "ADD": s.concat("0000");
				        break;
			case "SUB": s.concat("0001");
	        break;
			case "MUL": s.concat("0010");
	        break;
			case "MOVI": s.concat("0011");
	        break;
			case "BEQZ": s.concat("0100");
	        break;
			case "ANDI": s.concat("0101");
	        break;
			case "EOR": s.concat("0110");
	        break;
			case "BR": s.concat("0111");
	        break;
			case "SAL": s.concat("1000");
	        break;
			case "SAR": s.concat("1001");
	        break;
			case "LDR": s.concat("1010");
	        break;
			case "STR": s.concat("1011");
	        break;
				
		}
			s.concat(getregister(instruction[1]));
			if(instruction[0] == "ADD" ||instruction[0] == "SUB" || instruction[0] == "MUL" || instruction[0] == "EOR" || instruction[0] == "BR") {
				s.concat(getregister(instruction[2]));
			}
			else {
				int tmp;
				String tmp2;
				
				tmp = Integer.parseInt(instruction[2]);
				tmp2 = Integer.toBinaryString(tmp);
				s.concat(tmp2);
			}
			
			out.add(s);
			
	
		
		
		
		
		
	}
		return out;
		
	
	
	
}*/
	
	public static String getImm(int r) {
		String out = "";
		
		/*int out1;
		
		int tmp;
		
		String tmp2;
		
		
		tmp = (Integer.parseInt(instruction[2]));
		System.out.println(tmp);
		tmp2 = Integer.toBinaryString(tmp);*/
		
		if(r>=0) {
			
			
			out = Integer.toBinaryString(r);
			
			
			while(out.length()<6) {
				
				out = "0" + out;
			}
		}
		
		else {
			
			out = Integer.toBinaryString(r);
			
			out = out.substring(26);
			
			
		}
		
			return out;
		
		
		
		
	}
public static String getregister(String r) {
	String out = "";
	out = out + r.charAt(1);
	int out1;
	if(r.length()>2) {
		out  = out + r.charAt(2);
	}
		out1 = Integer.parseInt(out);
		out = Integer.toBinaryString(out1);
		while(out.length()<6) {
			out = "0" + out;
		}
		return out;
	
	
	
	
}



public static void main(String[] args) {
	

	
}
}

