package problem16;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    // Read in all inputs

    public static void main(String[] args) {
        problem1();
        problem2();
    }

    private static void problem2() {
        Map<Integer, Instruction> commandMap = createCommandMap(readInputs());

        List<Command> commands = readCommands();

        int[] registers = new int[4];

        for(Command command: commands) {
            registers = commandMap.get(command.code).update(registers, command.A, command.B, command.C);
        }

        System.out.println(registers[0]);
    }

    private static Map<Integer, Instruction> createCommandMap(List<Input> inputs) {
        Map<Integer, List<Input>> grouped = inputs.stream().collect(Collectors.groupingBy(Input::getCode));
        Map<Integer, Set<Instruction>> satisfying = new HashMap<>();

        Map<Integer, Instruction> result = new HashMap<>();



        for(Map.Entry<Integer, List<Input>> e : grouped.entrySet()) {
            satisfying.put(e.getKey(), e.getValue().stream().map(Input::getAllSatisfying).reduce((l, r) -> {l.retainAll(r); return l;}).get());
        }

        while(!satisfying.isEmpty()) {
            Instruction instruction = null;
            int code = -1;

            for(Map.Entry<Integer, Set<Instruction>> e : satisfying.entrySet()) {
                if (e.getValue().size() == 1) {
                    instruction = e.getValue().toArray(new Instruction[0])[0];
                    code = e.getKey();
                    break;
                }
            }

            for(Map.Entry<Integer, Set<Instruction>> e : satisfying.entrySet()) {
                e.getValue().remove(instruction);
            }
            satisfying.remove(code);

            if((instruction == null) || (code == -1)) {
                throw new RuntimeException();
            }
            result.put(code, instruction);
        }

        return result;
    }

    private static void problem1() {
        int count = 0;

        List<Input> inputs = readInputs();
        for(Input input: inputs) {
            if(input.getAllSatisfying().size() >= 3) {
                count++;
            }
        }

        System.out.println(count);
    }



    private static List<Input> readInputs() {
        Path path = Paths.get("./problem-input/16.txt");
        List<String> lines;
        try {
           lines  = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Input> result = new ArrayList<>();

        for(int i=0; lines.get(i).startsWith("Before:"); i+=4) {
            String beforeLine = lines.get(i);
            String instructionLine = lines.get(i + 1);
            String afterLine = lines.get(i + 2);
            int[] before = Arrays.stream(beforeLine.replaceAll("[\\D&&\\S]", "").trim().split(" ")).mapToInt(Integer::parseInt).toArray();
            int[] after =  Arrays.stream(afterLine.replaceAll("[\\D&&\\S]", "").trim().split(" ")).mapToInt(Integer::parseInt).toArray();

            result.add(new Input(before, after, readCommand(instructionLine)));
        }
        return result;
    }

    private static Command readCommand(String instructionLine) {
        int code = Integer.parseInt(instructionLine.split(" ")[0]);
        int A = Integer.parseInt(instructionLine.split(" ")[1]);
        int B = Integer.parseInt(instructionLine.split(" ")[2]);
        int C = Integer.parseInt(instructionLine.split(" ")[3]);
        return new Command(code, A, B, C);
    }

    private static List<Command> readCommands() {
        Path path = Paths.get("./problem-input/16.txt");
        List<String> lines;
        try {
            lines  = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Command> result = new ArrayList<>();
        int i=0;
        for(; lines.get(i).startsWith("Before:"); i+=4) {
        }
        while(lines.get(i).isEmpty()) {
            i++;
        }

        while(i < lines.size()) {
            result.add(readCommand(lines.get(i)));
            i++;
        }

        return result;
    }

}

