module llm(green, red, yellow, clock, a1, a2, a3, deception_out, current_state, timer);

	// Inputs
	input green, red, yellow;
	input clock;
	
	// Outputs
	output reg[3:0] current_state;
	output reg a1, a2, a3, deception_out; //a3 is expansion output
	output reg[5:0] timer;

	
	// Your code goes here.

endmodule