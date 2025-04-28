//`include "four_bit_rca.v"
`timescale 1 ns/10 ps
module four_bit_rca_tb;
    reg[3:0] A, B;
    reg Cin;
    wire[3:0] S;
    wire Cout;
    four_bit_rca uut (.A(A), .B(B), .Cin(Cin), .S(S), .Cout(Cout));
    
    initial begin
        $monitor("A = %b , B = %b , Cin = %b , S = %b, Cout = %b", A, B, Cin, S, Cout);
                        
        $dumpfile("result.vcd");
        $dumpvars;

        // Test case with Select = 0
        Cin = 0;
        A = 4'b0000;
        B = 4'b0000;
        repeat (16) begin
            repeat (16) begin
                #10;
                B = B + 1;
            end
            B = 4'b0000;
            A = A + 1;
        end
        
        // Test case with Select = 1
        Cin = 1;
        A = 4'b0000;
        B = 4'b0000;
        repeat (16) begin
            repeat (16) begin
                #10;
                B = B + 1;
            end
            B = 4'b0000;   
            A = A + 1;
        end
        
        $finish;
    end

endmodule