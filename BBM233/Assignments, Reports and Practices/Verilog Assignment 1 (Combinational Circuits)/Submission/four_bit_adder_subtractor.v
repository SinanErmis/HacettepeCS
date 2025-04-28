module four_bit_adder_subtractor(A, B, subtract, Result, Cout);
    input [3:0] A;
    input [3:0] B;
    input subtract;
    output [3:0] Result;
    output Cout;

    wire [3:0] bComplement;
    wire [3:0] usedB;

    two_s_complement complementer(
        .In(B),
        .Out(bComplement)
    );

    four_bit_2x1_mux mux(
        .In_0(B),
        .In_1(bComplement),
        .Select(subtract),
        .Out(usedB)
    );

    four_bit_rca adder(
        .A(A),
        .B(usedB),
        .Cin(0),
        .S(Result),
        .Cout(Cout)
    );
endmodule
