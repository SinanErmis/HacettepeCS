//`include "four_bit_2x1_mux.v"
`timescale 1ns/10ps

module four_bit_2x1_mux_tb;
	
	reg [3:0] In_1, In_0;
    reg Select;
    wire [3:0] Out;
    four_bit_2x1_mux uut (.In_1(In_1), .In_0(In_0), .Select(Select), .Out(Out));
        
    initial begin
        $monitor("In_1 = %b , In_0 = %b , Select = %b , Out = %b", In_1, In_0, Select, Out);
        
        $dumpfile("result.vcd");
        $dumpvars;

        // Test case with Select = 0
        Select = 0;
        In_1 = 4'b0000;
        In_0 = 4'b0000;
        repeat (16) begin
            repeat (16) begin
                #10;
                In_0 = In_0 + 1; 
            end
            In_0 = 4'b0000;     
            In_1 = In_1 + 1;   
        end
        
        // Test case with Select = 1
        Select = 1;
        In_1 = 4'b0000;
        In_0 = 4'b0000;
        repeat (16) begin
            repeat (16) begin
                #10;
                In_0 = In_0 + 1; 
            end
            In_0 = 4'b0000;    
            In_1 = In_1 + 1;   
        end
        
        $finish;
    end
endmodule
