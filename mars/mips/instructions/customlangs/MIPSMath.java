package mars.mips.instructions.customlangs;
import mars.simulator.*;
import mars.mips.hardware.*;
import mars.mips.instructions.syscalls.*;
import mars.*;
import mars.util.*;
import java.util.*;
import java.io.*;
import mars.mips.instructions.*;
import java.util.Random;


public class MIPSMath extends CustomAssembly{
    @Override
    public String getName(){
        return "MIPS Math";
    }

    @Override
    public String getDescription(){
        return "A version of MIPS with more math functionalities";
    }

    @Override
    protected void populate(){
        instructionList.add(
                new BasicInstruction("add $t1,$t2,$t3",
                        "Addition: set $t1 to ($t2 + $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = RegisterFile.getValue(operands[2]);
                                int sum = add1 + add2;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                                SystemIO.printString(add1 + " + " + add2 + " = " + sum + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("addi $t1,$t2,-100",
                        "Addition immediate: set $t1 to ($t2 + signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000001 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int add1 = RegisterFile.getValue(operands[1]);
                                int add2 = operands[2] << 16 >> 16;
                                int sum = add1 + add2;
                                // overflow on A+B detected when A and B have same sign and A+B has other sign.
                                if ((add1 >= 0 && add2 >= 0 && sum < 0)
                                        || (add1 < 0 && add2 < 0 && sum >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], sum);
                                SystemIO.printString(add1 + " + " + add2 + " = " + sum + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("sub $t1,$t2,$t3",
                        "Subtraction: set $t1 to ($t2 - $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int sub1 = RegisterFile.getValue(operands[1]);
                                int sub2 = RegisterFile.getValue(operands[2]);
                                int dif = sub1 - sub2;
                                // overflow on A-B detected when A and B have opposite signs and A-B has B's sign
                                if ((sub1 >= 0 && sub2 < 0 && dif < 0)
                                        || (sub1 < 0 && sub2 >= 0 && dif >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], dif);
                                SystemIO.printString(sub1 + " - " + sub2 + " = " + dif + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("subi $t1, $t2, -100",
                        "Subtraction immediate: set $t1 to ($t2 - signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000010 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int sub1 = RegisterFile.getValue(operands[1]);
                                int sub2 = operands[2] << 16 >> 16;
                                int dif = sub1 - sub2;
                                // overflow on A-B detected when A and B have opposite signs and A-B has B's sign
                                if ((sub1 >= 0 && sub2 < 0 && dif < 0)
                                        || (sub1 < 0 && sub2 >= 0 && dif >= 0))
                                {
                                    throw new ProcessingException(statement,
                                            "arithmetic overflow",Exceptions.ARITHMETIC_OVERFLOW_EXCEPTION);
                                }
                                RegisterFile.updateRegister(operands[0], dif);
                                SystemIO.printString(sub1 + " - " + sub2 + " = " + dif + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("mult $t1, $t2, $t3",
                        "Multiplication: set $t1 to ($t2 * $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int mult1 = RegisterFile.getValue(operands[1]);
                                int mult2 = RegisterFile.getValue(operands[2]);
                                int product = mult1 * mult2;
                                RegisterFile.updateRegister(operands[0], product);
                                SystemIO.printString(mult1 + " * " + mult2 + " = " + product + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("multi $t1, $t2, -100",
                        "Multiplication immediate: set $t1 to ($t2 * signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000011 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int mult1 = RegisterFile.getValue(operands[1]);
                                int mult2 = operands[2];
                                int product = mult1 * mult2;
                                RegisterFile.updateRegister(operands[0], product);
                                SystemIO.printString(mult1 + " * " + mult2 + " = " + product + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("div $t1, $t2, $t3",
                        "Division: set $t1 to ($t2 / $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 000100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int dividend = RegisterFile.getValue(operands[1]);
                                int divisor = RegisterFile.getValue(operands[2]);
                                if (divisor == 0)
                                {
                                    // Note: no exceptions, and undefined results for zero divide
                                    return;
                                }
                                int quotient = dividend / divisor;
                                RegisterFile.updateRegister(operands[0], quotient);
                                SystemIO.printString(dividend + " / " + divisor + " = " + quotient + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("divi $t1, $t2, -100",
                        "Division immediate: set $t1 to ($t2 / signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000100 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int dividend = RegisterFile.getValue(operands[1]);
                                int divisor = operands[2];
                                if (divisor == 0)
                                {
                                    // Note: no exceptions, and undefined results for zero divide
                                    return;
                                }
                                int quotient = dividend / divisor;
                                RegisterFile.updateRegister(operands[0], quotient);
                                SystemIO.printString(dividend + " / " + divisor + " = " + quotient + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("mod $t1, $t2, $t3",
                        "Modulo: set $t1 to ($t2 % $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "00000 sssss ttttt fffff 00000 000101",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int mod1 = RegisterFile.getValue(operands[1]);
                                int mod2 = RegisterFile.getValue(operands[2]);
                                int remainder = mod1 % mod2;
                                RegisterFile.updateRegister(operands[0], remainder);
                                SystemIO.printString(mod1 + " % " + mod2 + " = " + remainder + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("modi $t1, $t2, -100",
                        "Modulo immediate: set $t1 to ($t2 % signed 16-bit immediate)",
                        BasicInstructionFormat.I_FORMAT,
                        "000101 sssss fffff tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int mod1 = RegisterFile.getValue(operands[1]);
                                int mod2 = operands[2];
                                int remainder = mod1 % mod2;
                                RegisterFile.updateRegister(operands[0], remainder);
                                SystemIO.printString(mod1 + " % " + mod2 + " = " + remainder + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("j target",
                        "Jump: jump to instruction at target address",
                        BasicInstructionFormat.J_FORMAT,
                        "001010 ffffffffffffffffffffffffff",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                Globals.instructionSet.processJump(
                                        ((RegisterFile.getProgramCounter() & 0xF0000000)
                                                | (operands[0] << 2)));
                            }
                        }));

        instructionList.add(
                new BasicInstruction("beq $t1, $t2, label",
                        "Branch if equal: branch to instruction at label’s address if $t1 = $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000110 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();

                                if (RegisterFile.getValue(operands[0])
                                        == RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("bne $t1, $t2, label",
                        "Branch if not equal: branch to instruction at label’s address if $t1 != $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "000111 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                if (RegisterFile.getValue(operands[0])
                                        != RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("blt $t1, $t2, label",
                        "Branch if less than: branch to instruction at label's address if $t1 < $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "001000 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                if (RegisterFile.getValue(operands[0])
                                        < RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("bgt $t1, $t2, label",
                        "Branch if greater than: branch to instruction at label’s address if $t1 > $t2",
                        BasicInstructionFormat.I_BRANCH_FORMAT,
                        "001001 fffff sssss tttttttttttttttt",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                if (RegisterFile.getValue(operands[0])
                                        > RegisterFile.getValue(operands[1]))
                                {
                                    Globals.instructionSet.processBranch(operands[2]);
                                }
                            }
                        }));

        instructionList.add(
                new BasicInstruction("sqr $t1, $t2",
                        "Square an integer: set $t1 to ($t2) ^ 2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss fffff 00000 00000 000110",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num = RegisterFile.getValue(operands[1]);
                                int result = num * num;
                                RegisterFile.updateRegister(operands[0], result);
                                SystemIO.printString(num + " ^ 2 = " + result + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("cube $t1, $t2",
                        "Cube an integer: set $t1 to ($t2) ^ 3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss fffff 00000 00000 000111",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num = RegisterFile.getValue(operands[1]);
                                int result = num * num * num;
                                RegisterFile.updateRegister(operands[0], result);
                                SystemIO.printString(num + " ^ 3 = " + result + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("pow $t1, $t2, $t3",
                        "Raise to a power: set $t1 to ($t2 ^ $t3)",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int base = RegisterFile.getValue(operands[1]);
                                int exponent = RegisterFile.getValue(operands[2]);
                                int result = (int) Math.pow(base, exponent);
                                RegisterFile.updateRegister(operands[0], result);
                                SystemIO.printString(base + " ^ " + exponent + " = " + result + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("min $t1, $t2, $t3",
                        "Find minimum: set $t1 to the smaller value of $t2 and $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num1 = RegisterFile.getValue(operands[1]);
                                int num2 = RegisterFile.getValue(operands[2]);
                                int min;
                                if (num1 <= num2) {
                                    min = num1;
                                } else {
                                    min = num2;
                                }
                                RegisterFile.updateRegister(operands[0], min);
                                SystemIO.printString("Minimum of " + num1 + " and " + num2 + ": " + min + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("max $t1, $t2, $t3",
                        "Find maximum: set $t1 to the larger value of $t2 and $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001010",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num1 = RegisterFile.getValue(operands[1]);
                                int num2 = RegisterFile.getValue(operands[2]);
                                int max;
                                if (num1 >= num2) {
                                    max = num1;
                                } else {
                                    max = num2;
                                }
                                RegisterFile.updateRegister(operands[0], max);
                                SystemIO.printString("Maximum of " + num1 + " and " + num2 + ": " + max + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("rand $t1, $t2, $t3",
                        "Random integer: set $t1 to a random integer between $t2 and $t3, inclusive",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001011",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int min = RegisterFile.getValue(operands[1]);
                                int max = RegisterFile.getValue(operands[2]);
                                Random random = new Random();
                                int rand = random.nextInt(max - min + 1) + min;
                                RegisterFile.updateRegister(operands[0], rand);
                                SystemIO.printString("Random number between " + min + " and " + max + ": " + rand + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("abs $t1, $t2",
                        "Absolute value: set $t1 to the absolute value of $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss fffff 00000 00000 001100",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num = RegisterFile.getValue(operands[1]);
                                int abs = Math.abs(num);
                                RegisterFile.updateRegister(operands[0], abs);
                                SystemIO.printString("Absolute value of " + num + ": " + abs + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("fact $t1, $t2",
                        "Factorial: set $t1 to ($t2)!",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss fffff 00000 00000 001101",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num = RegisterFile.getValue(operands[1]);
                                int result = 1;
                                for (int i = 1; i <= num; i++) {
                                    result *= i;
                                }
                                RegisterFile.updateRegister(operands[0], result);
                                SystemIO.printString(num + "! = " + result + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("gcd $t1, $t2, $t3",
                        "Greatest common divisor: set $t1 to the GCD of $t2 and $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001110",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num1 = RegisterFile.getValue(operands[1]);
                                int num2 = RegisterFile.getValue(operands[2]);
                                SystemIO.printString("GCD of " + num1 + " and " + num2 + ": ");
                                //Use absolute values for handling negative numbers
                                num1 = Math.abs(num1);
                                num2 = Math.abs(num2);
                                int temp;
                                while (num2 != 0) {
                                    temp = num2;
                                    num2 = num1 % num2;
                                    num1 = temp;
                                }
                                RegisterFile.updateRegister(operands[0], num1);
                                SystemIO.printString(num1 + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("lcm $t1, $t2, $t3",
                        "Least common multiple: set $t1 to the LCM of $t2 and $t3",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss ttttt fffff 00000 001111",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int num1 = RegisterFile.getValue(operands[1]);
                                int num2 = RegisterFile.getValue(operands[2]);
                                SystemIO.printString("LCM of " + num1 + " and " + num2 + ": ");
                                //Use absolute values for handling negative numbers
                                num1 = Math.abs(num1);
                                num2 = Math.abs(num2);
                                int lcm;
                                if (num1 >= num2)
                                    lcm = num1;
                                else
                                    lcm = num2;
                                boolean loop = true;
                                while (loop) {
                                    if (lcm % num1 == 0 && lcm % num2 == 0)
                                        break;
                                    lcm++;
                                }
                                RegisterFile.updateRegister(operands[0], lcm);
                                SystemIO.printString(lcm + "\n");
                            }
                        }));

        instructionList.add(
                new BasicInstruction("swap $t1, $t2",
                        "Swap registers: swap the values of $t1 and $t2",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 sssss fffff 00000 00000 010000",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int temp1 = RegisterFile.getValue(operands[0]);
                                int temp2 = RegisterFile.getValue(operands[1]);
                                RegisterFile.updateRegister(operands[0], temp2);
                                RegisterFile.updateRegister(operands[1], temp1);
                            }
                        }));

        instructionList.add(
                new BasicInstruction("print $t1",
                        "Print register: print the value of $t1",
                        BasicInstructionFormat.R_FORMAT,
                        "000000 00000 00000 fffff 00000 010001",
                        new SimulationCode()
                        {
                            public void simulate(ProgramStatement statement) throws ProcessingException
                            {
                                int[] operands = statement.getOperands();
                                int value = RegisterFile.getValue(operands[0]);
                                String register = getRegister(operands[0]);
                                SystemIO.printString("Value of " + register + ": " + value + "\n");
                            }
                        }));
    }

    private String getRegister(int reg){
        switch (reg) {
            case 0: return "$zero";
            case 1: return "$at";
            case 2: return "$v0";
            case 3: return "$v1";
            case 4: return "$a0";
            case 5: return "$a1";
            case 6: return "$a2";
            case 7: return "$a3";
            case 8: return "$t0";
            case 9: return "$t1";
            case 10: return "$t2";
            case 11: return "$t3";
            case 12: return "$t4";
            case 13: return "$t5";
            case 14: return "$t6";
            case 15: return "$t7";
            case 16: return "$s0";
            case 17: return "$s1";
            case 18: return "$s2";
            case 19: return "$s3";
            case 20: return "$s4";
            case 21: return "$s5";
            case 22: return "$s6";
            case 23: return "$s7";
            case 24: return "$t8";
            case 25: return "$t9";
            case 26: return "$k0";
            case 27: return "$k1";
            case 28: return "$gp";
            case 29: return "$sp";
            case 30: return "$fp";
            case 31: return "$ra";
            default: return "";
        }
    }
}