package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class bound_Gd_M_mem extends Executable
{
    final int op1Index;
    final Address op2;

    public bound_Gd_M_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Gd(modrm);
        op2 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        int addr = op2.get(cpu);
        int lower = cpu.linearMemory.getDoubleWord(addr);
	int upper = cpu.linearMemory.getDoubleWord(addr+4);
	int index = op1.get32();
	if ((index < lower) || (index > (upper + 4)))
	    throw ProcessorException.BOUND_RANGE;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}