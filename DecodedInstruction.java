
public class DecodedInstruction {
	int opcode;
	int r1;
	int r2;
	byte imm;
	
	
	
	public DecodedInstruction(int opcode , int r1 , int r2 , byte imm) {
		
		
		this.opcode = opcode;
		this.r1 = r1;
		this.r2 = r2;
		this.imm = imm;
		
	}

}
