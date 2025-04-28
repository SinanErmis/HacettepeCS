//`include "full_adder.v"
`timescale 1 ns/10 ps
module full_adder_tb;
    reg A, B, Cin;
    wire S, Cout;
    
    full_adder UUT(.A(A), .B(B), .Cin(Cin), .S(S), .Cout(Cout));
    
    initial begin
    $monitor("A = %b , B = %b , Cin = %b , S = %b , Cout = %b", A, B, Cin, S, Cout);
                    
    $dumpfile("result.vcd");
    $dumpvars;
    A = 0; B = 0; Cin = 0;
    #10 A = 0; B = 0; Cin = 1;
    #10 A = 0; B = 1; Cin = 0;
    #10 A = 0; B = 1; Cin = 1;
    #10 A = 1; B = 0; Cin = 0;
    #10 A = 1; B = 0; Cin = 1;
    #10 A = 1; B = 1; Cin = 0;
    #10 A = 1; B = 1; Cin = 1;
    #10 $finish;
    end

endmodule