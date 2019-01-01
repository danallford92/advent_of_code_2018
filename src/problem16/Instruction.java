package problem16;

public enum Instruction {
    ADDR {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] + registers[B];
        }
    },
    ADDI {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] + B;
        }
    },
    MULR {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] * registers[B];
        }
    },
    MULI {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] * B;
        }
    },

    BANR {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] & registers[B];
        }
    },

    BANI {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] & B;
        }
    },

    BORR {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] | registers[B];
        }
    },


    BORI {
        public int newValue(int[] registers, int A, int B) {
            return registers[A] | B;
        }
    },

    SETR {
        public int newValue(int[] registers, int A, int B) {
            return registers[A];
        }
    },


    SETI {
        public int newValue(int[] registers, int A, int B) {
            return A;
        }
    },

    GTIR {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return A > registers[B] ? 1 : 0;
        }
    },

    GTRI {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return registers[A] > B ? 1 : 0;
        }
    },
    GTRR {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return registers[A] > registers[B] ? 1 : 0;
        }
    },

    EQIR {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return A == registers[B] ? 1 : 0;
        }
    },
    EQRI {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return registers[A] == B ? 1 : 0;
        }
    },
    EQRR {
        @Override
        public int newValue(int[] registers, int A, int B) {
            return registers[A] == registers[B] ? 1 : 0;
        }
    };

    public abstract int newValue(int[] registers, int A, int B);

    public int[] update(int[] registers, int A, int B, int C) {
        int[] result = new int[4];
        for(int i = 0; i< 4; i++) {
            result[i] = registers[i];
        }
        result[C] = newValue(registers, A, B);
        return result;
    }
}