module dff (
    input D,      // Data input
    input CLK,    // Clock input
    input RESET,  // Asynchronous reset, active high
    output reg Q  // Output
);
    always @ (posedge CLK or posedge RESET)
    begin
        if (RESET) 
            Q <= 1'b0;       // Reset the output to 0 on rising edge of rst
        else 
            Q <= D;       // Capture input `d` on rising edge of clk
    end
endmodule