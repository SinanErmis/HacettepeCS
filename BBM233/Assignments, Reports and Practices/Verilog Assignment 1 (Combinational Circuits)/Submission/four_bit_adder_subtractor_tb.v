`timescale 1ns/1ps
module four_bit_adder_subtractor_tb;
    reg[3:0] A, B;
    reg subtract;
    wire[3:0] Result;
    wire Cout;
    four_bit_adder_subtractor uut(.A(A), .B(B), .subtract(subtract), .Result(Result), .Cout(Cout));
    initial begin
        $monitor("A = %b , B = %b , subtract = %b , Result = %b, Cout = %b", A, B, subtract, Result, Cout);
                                
        $dumpfile("result.vcd");
        $dumpvars;

        // Test case with Select = 0
        subtract = 0;
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
        subtract = 1;
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
