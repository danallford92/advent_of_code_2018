package problem16;

import java.util.HashSet;
import java.util.Set;

public class Input {
    int[] before;
    int[] after;
    public Command command;

    public Input(int[] before, int[] after, Command command) {
        this.before = before;
        this.after = after;
        this.command = command;
    }

    public boolean isSatisfiedBy(Instruction instruction) {
        int[] updated = instruction.update(before, command.A, command.B, command.C);

        for(int i=0; i<4; i++) {
            if(updated[i] != after[i])
                return false;
        }

        return true;
    }

    public Set<Instruction> getAllSatisfying(){
        Set<Instruction> result = new HashSet<>();
        for(Instruction instruction: Instruction.values()) {
            if(isSatisfiedBy(instruction)) {
                result.add(instruction);
            }
        }
        return result;
    }

    public int getCode() {
        return command.code;
    }
}
