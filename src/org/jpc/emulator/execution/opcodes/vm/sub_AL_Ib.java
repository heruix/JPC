package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class sub_AL_Ib extends Executable
{
    final int immb;

    public sub_AL_Ib(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        immb = Modrm.Ib(input);
    }

    public Branch execute(Processor cpu)
    {
        cpu.flagOp1 = (byte)cpu.r_al.get8();
        cpu.flagOp2 = (byte)immb;
        cpu.flagResult = (byte)(cpu.flagOp1 - cpu.flagOp2);
        cpu.r_al.set8((byte)cpu.flagResult);
        cpu.flagIns = UCodes.SUB8;
        cpu.flagStatus = OSZAPC;
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