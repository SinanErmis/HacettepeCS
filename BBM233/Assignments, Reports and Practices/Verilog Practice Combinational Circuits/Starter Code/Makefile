CC = iverilog 
FLAGS = -Wall 

# Define the testbench targets and their dependencies
TB_TARGETS = full_adder half_adder multiplier four_bit_rca

# You can execute each separately by:
# make full_adder
# make half_adder
# make multiplier
# make four_bit_rca

all: $(TB_TARGETS)

full_adder: .FORCE
	$(CC) $(FLAGS) -o full_adder half_adder.v full_adder.v full_adder_tb.v
	vvp full_adder
	gtkwave full_adder.vcd

half_adder: .FORCE
	$(CC) $(FLAGS) -o half_adder half_adder.v half_adder_tb.v
	vvp half_adder
	gtkwave half_adder.vcd

multiplier: .FORCE
	$(CC) $(FLAGS) -o multiplier multiplier.v half_adder.v full_adder.v  multiplier_tb.v
	vvp multiplier
	gtkwave multiplier.vcd

four_bit_rca: .FORCE
	$(CC) $(FLAGS) -o four_bit_rca four_bit_rca.v full_adder.v
	vvp four_bit_rca
	gtkwave four_bit_rca.vcd

clean: 
	del $(TB_TARGETS)
	del *.vcd

.FORCE:
.PHONY: .FORCE