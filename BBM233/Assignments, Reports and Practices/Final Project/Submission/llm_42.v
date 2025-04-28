module llm(green, red, yellow, clock, a1, a2, a3, deception_out, current_state, timer);

	// Inputs
	input green, red, yellow;
	input clock;
	
	// Outputs
	output reg[3:0] current_state;
	output reg a1, a2, a3, deception_out; //a3 is expansion output
	output reg[5:0] timer;

    // States
    parameter LAY_LOW         = 4'b0000,
              DECEPTION       = 4'b0001,
              ATTACK_SECURITY = 4'b0010,
              ATTACK_DATABASE = 4'b0011,
              FAIL_STATE      = 4'b0100,
              EXPANSION       = 4'b0101;

    always @(posedge clock) begin
        case (current_state)

        // LAY_LOW (0000)
        LAY_LOW: begin
            // Red => Deception immediately
            if (red == 1) begin
                current_state <= DECEPTION;
                deception_out <= 1;
                timer <= 1;
            end
            // After 20s AND only green => Attack Security
            else if (timer >= 20 && green == 1 && red == 0 && yellow == 0) begin
                current_state <= ATTACK_SECURITY;
                a1 <= 1;
                timer <= 1;
            end
            else begin
                timer <= timer + 1;
            end
        end

        // ATTACK_SECURITY (0010)
        ATTACK_SECURITY: begin
            // Red => Deception immediately
            if (red == 1) begin
                current_state <= DECEPTION;
                deception_out <= 1;
                timer <= 1;
            end
            // Yellow => return to Lay Low immediately
            else if (yellow == 1) begin
                current_state <= LAY_LOW;
                a1 <= 0;
                timer <= 1;
            end
            // After 20s => Attack Database
            else if (timer >= 20 && green == 1 && red == 0 && yellow == 0) begin
                current_state <= ATTACK_DATABASE;
                a2 <= 1;
                timer <= 1;
            end
            else begin
                timer <= timer + 1;
            end
        end

        // ATTACK_DATABASE (0011)
        ATTACK_DATABASE: begin
            // Red => Deception
            if (red == 1) begin
                current_state <= DECEPTION;
                deception_out <= 1;
                timer <= 1;
            end
            // Yellow => Attack Security
            else if (yellow == 1) begin
                current_state <= ATTACK_SECURITY;
                a2 <= 0;
                timer <= 1;
            end
            // After 10s => Expansion
            else if (timer >= 10 && green == 1 && red == 0 && yellow == 0) begin
                current_state <= EXPANSION;
                a3 <= 1;
                timer <= 1;
            end
            else begin
                timer <= timer + 1;
            end
        end

        // DECEPTION (0001)
        DECEPTION: begin
            // Stay for 15s total
            if (timer >= 15) begin
                // After 15s, if red=1 => Fail, else => Lay Low
                if (red == 1) begin
                    current_state <= FAIL_STATE;
                    timer <= 1;
                end
                else begin
                    current_state <= LAY_LOW;
                    a1 <= 0;
                    a2 <= 0;
                    deception_out <= 0;
                    timer <= 1;
                end
            end
            else begin
                timer <= timer + 1;
            end
        end

        // FAIL_STATE (0100) - terminal
        FAIL_STATE: begin
            timer <= timer + 1;
        end

        // EXPANSION (0101) - terminal
        EXPANSION: begin
            timer <= timer + 1;
        end

        // Default
        default: begin
            current_state <= LAY_LOW;
            timer <= 1;
        end

        endcase
    end

    // Initial Conditions
    initial begin
        current_state = LAY_LOW;
        timer = 1;
        a1 = 0;
        a2 = 0;
        a3 = 0;
        deception_out = 0;
    end

endmodule