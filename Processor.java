import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Processor {
	Parser parser;
	DataMemory dataMem;
	InstructionMemory instMem;
	String[] instructionMemoryPrint = new String[1024];
	static byte[] registerFile;
	short pc;
	String SREG;
	int time;
	Queue<Short> fetchedQueue = new LinkedList<Short>();
	Queue<DecodedInstruction> decodedQueue = new LinkedList<DecodedInstruction>();
	short executePc;

	public Processor() {
		parser = new Parser();
		dataMem = new DataMemory();
		instMem = new InstructionMemory();
		registerFile = new byte[64];
		for (int i = 0; i < registerFile.length; i++) {
			registerFile[i] = 0;
		}
		pc = 0;
		executePc = 0;
		SREG = "00000000";
		time = 1;
	}

	public void fetch() {
		short x = this.instMem.instructions[this.pc];
		this.fetchedQueue.add(x);
		this.pc++;

	}

	public void decode() {
		short instruction = (short) fetchedQueue.remove();
		int temp;
		temp = (instruction >> 12);

		int opcode = temp & 0b0000000000001111;
		temp = (short) (instruction & 0b0000111111000000);
		int r1 = temp >> 6;
		temp = (short) (instruction & 0b0000000000111111);
		int r2 = temp;
		temp = (byte) (instruction & 0b0000000000111111);

		byte imm;

		int test = (instruction & 0b0000000000100000);
		if (test == 32) {

			imm = (byte) ((instruction & 0b1111111111111111) | 0b1111111111000000);
		} else

			imm = (byte) temp;

		this.decodedQueue.add(new DecodedInstruction(opcode, r1, r2, imm));

	}

	public void execute() {
		this.executePc++;
		DecodedInstruction ins = (DecodedInstruction) decodedQueue.remove();
		byte temp1 = registerFile[ins.r1];
		byte temp2 = registerFile[ins.r2];
		printInstruction(ins);
		
		
		if (ins.opcode == 0 | ins.opcode == 1 | ins.opcode == 2 | ins.opcode == 6 | ins.opcode == 5 | ins.opcode == 8
				| ins.opcode == 9) {
			
			
			this.SREG ="00000000";
		}

		switch (ins.opcode) {
		case 0:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], registerFile[ins.r2]);

			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}
			// how to get the unsigned values?
			break;
		case 1:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], registerFile[ins.r2]);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 2:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], registerFile[ins.r2]);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;

		case 6:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], registerFile[ins.r2]);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 7: // ask for a solution
			int x = (short) registerFile[ins.r1];
			short y = registerFile[ins.r2];
			y = (short) (y & 0b0000000011111111);
			x = (x << 8) | y;
			// and r2 with a bitbmask
			this.pc = (short) x;//
			this.executePc = pc;// -1 BECAUSE THE PC GETS INCREMENTED AFTER EXECUTION AND WE DON'T WANT THAT
								// wala la??
			// CHECK THAT THE ADDRESS DOESN'T PASS THE LENGTH OF THE INSTRUCTION
			this.decodedQueue = new LinkedList<DecodedInstruction>();

			this.fetchedQueue = new LinkedList<Short>();
			break;

		case 3:
			registerFile[ins.r1] = ins.imm;
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 4:

			if (registerFile[ins.r1] == 0) { // CHECK THAT THE ADDRESS DOESN'T PASS THE LENGTH OF THE INSTRUCTIONs
				System.out.println("Branched");
				this.pc = (short) (this.executePc + ins.imm);
				this.executePc = pc; // test tanyyyyyyyyyyy

				this.decodedQueue = new LinkedList<DecodedInstruction>();

				this.fetchedQueue = new LinkedList<Short>();

			}
			break;
		case 5:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], ins.imm);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 8:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], ins.imm);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 9:
			registerFile[ins.r1] = ALU(ins.opcode, registerFile[ins.r1], ins.imm);
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 10:
			registerFile[ins.r1] = this.dataMem.data[ins.imm];
			if (registerFile[ins.r1] != temp1) {

				System.out.println(
						"The Value of R" + ins.r1 + " has been changed from " + temp1 + " to " + registerFile[ins.r1]);
			}

			break;
		case 11:
			this.dataMem.data[ins.imm] = registerFile[ins.r1];

			break;
		}
		// handle sreg
		char[] SREGChars = this.SREG.toCharArray();
		if (ins.opcode == 0) { // test with hardwired vlaues in memory

			if ((temp1 > 0 && temp2 > 0 && registerFile[ins.r1] < 0)
					|| (temp1 < 0 && temp2 < 0 && registerFile[ins.r1] > 0)) {// overflow

				SREGChars[4] = '1';

			} else {

				SREGChars[4] = '0';

			}

			// carry flag
			int a = temp1 & 0x000000FF;
			int b = temp2 & 0x000000FF;// get their unsigned value
			// check ninth bit

			if (((a + b) & 0x0000000100) == 0x0000000100) {

				SREGChars[3] = '1';

			} else {

				SREGChars[3] = '0';

			}
		}
		if (ins.opcode == 1) {
			if (((temp1 > 0 & temp2 < 0) | (temp1 < 0 & temp2 > 0)) && ((temp1 - temp2) * temp2 > 0)) {

				SREGChars[4] = '1';

			} else {

				SREGChars[4] = '0';

			}
		}
		if (ins.opcode == 0 | ins.opcode == 1 | ins.opcode == 2 | ins.opcode == 6 | ins.opcode == 5 | ins.opcode == 8
				| ins.opcode == 9) {

			if (registerFile[ins.r1] < 0) {// negative flag

				SREGChars[5] = '1';

			} else {

				SREGChars[5] = '0';

			}

			// zero flag
			if (registerFile[ins.r1] == 0) {

				SREGChars[7] = '1';

			} else {

				SREGChars[7] = '0';

			}

		}
		if (ins.opcode == 0 || ins.opcode == 1) {
			// sign flag
			if (SREGChars[4] == SREGChars[5]) {
				SREGChars[6] = '0';

			} else {
				SREGChars[6] = '1';

			}

		}

		this.SREG = String.valueOf(SREGChars);
		
		if (ins.opcode == 0 | ins.opcode == 1 | ins.opcode == 2 | ins.opcode == 6 | ins.opcode == 5 | ins.opcode == 8
				| ins.opcode == 9) {
			
			
			printSREG(this.SREG);
		}
		

	}

	public byte ALU(int opcode, byte x, byte y) {
		int output = 0;
		switch (opcode) {
		case 0:
			output = x + y;
			break;
		case 1:
			output = x - y;
			break;
		case 2:
			output = x * y;

			break;
		case 5:
			output = x & y;
			break;
		case 6:
			output = x ^ y;
			break;
		case 8:
			output = x << y;
			break;
		case 9:
			output = x >> y;

			break;
		}
		return (byte) output;
	}

	public static void printInstruction(DecodedInstruction ins) {

		System.out.println("OPCODE = " + ins.opcode);
		System.out.println("R" + ins.r1 + " = " + registerFile[ins.r1]);

		if (ins.opcode == 0 || ins.opcode == 1 || ins.opcode == 2 || ins.opcode == 6 || ins.opcode == 7) {

			System.out.println("R" + ins.r2 + " = " + registerFile[ins.r2]);

		}

		else {
			System.out.println("IMM = " + ins.imm);
		}

	}

	public static void printSREG(String Sreg) {

		System.out.println("-------------SREG-------------");
		System.out.println("Carry Flag = " + Sreg.charAt(3));
		System.out.println("Overflow Flag = " + Sreg.charAt(4));
		System.out.println("Negative Flag = " + Sreg.charAt(5));
		System.out.println("Sign Flag = " + Sreg.charAt(6));
		System.out.println("Zero Flag = " + Sreg.charAt(7));
		System.out.println("------------------------------");
	}

	public static void main(String[] args) throws IOException {
		Processor p = new Processor();

		p.parser.readfile(p, "program.txt");

		while (p.instMem.instructions[p.pc] != 0 || !(p.fetchedQueue.isEmpty()) || !(p.decodedQueue.isEmpty())) {
			System.out.println("_________________________________________________________");
			System.out.println("    ");
			System.out.println("Current clock cycle : " + p.time);
			System.out.println("---------------------------------");

			/*
			 * if (!(p.decodedQueue.isEmpty())) {
			 * 
			 * p.execute(); System.out.println(p.SREG); }
			 * 
			 * if (!(p.fetchedQueue.isEmpty())) {
			 * 
			 * p.decode(); }
			 * 
			 * if (p.instMem.instructions[p.pc] != 0) {
			 * 
			 * p.fetch(); }
			 */

			if (p.fetchedQueue.isEmpty() && p.decodedQueue.isEmpty()) {

				if (p.instMem.instructions[p.pc] != 0) {
					System.out.println("Fetched Instruction : " + p.pc);
					p.fetch();

				}
			} else if (p.decodedQueue.isEmpty() && !(p.fetchedQueue.isEmpty())) {

				if (p.instMem.instructions[p.pc] != 0) {
					System.out.println("Fetched Instruction : " + p.pc);
					p.fetch();

				}
				if (!p.fetchedQueue.isEmpty()) {

					p.decode();
					System.out.println("Decoded Instruction : " + (p.pc - 2));

				}
			} else {

				if (p.instMem.instructions[p.pc] != 0) {
					System.out.println("Fetched Instruction : " + p.pc);
					p.fetch();

				}

				if (!p.fetchedQueue.isEmpty()) {

					p.decode();
					System.out.println("Decoded Instruction : " + (p.pc - 2));
				}

				if (!p.decodedQueue.isEmpty()) {
					System.out.println("Executed Instruction : " + p.executePc);
					p.execute();

				}

			}

			p.time++;

		}

		for (int j = 0; j < 1024; j++) {
			System.out.println("Instruction Memory " + (j) + ": " + p.instMem.instructions[j]);
		}
		for (int j = 0; j < registerFile.length; j++) {
			System.out.println("Register " + (j) + ": " + registerFile[j]);
		}

		for (int j = 0; j < 2048; j++) {
			System.out.println("Data Memory " + (j) + ": " + p.dataMem.data[j]);
		}

	}
}