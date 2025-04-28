module machine_d(
    input wire x,
    input wire CLK,
    input wire RESET,
    output wire F,
    output wire [2:0] S
);
    dff DA(
        .D((~S[2] & S[1]) | (S[2] & ~S[1]) | (~S[2] & ~S[0] & x) | (S[1] & ~S[0] & ~x) | (S[1] & S[0] & x)),
        .CLK(CLK),
        .RESET(RESET),
        .Q(S[2])
    );
    dff DB(
        .D((~S[2] & ~S[1] & S[0]) | (S[1] & ~S[0] & ~x) | (S[2] & ~S[0]) | (S[2] & x)),
        .CLK(CLK),
        .RESET(RESET),
        .Q(S[1])
    );
    dff DC(
        .D((~S[0] & ~x) | (S[1] & S[0] & x) | (~S[2] & ~S[1] & x)),
        .CLK(CLK),
        .RESET(RESET),
        .Q(S[0])
    );
    
    assign F = S[0] & S[1] & S[2];
   
endmodule