package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class and_o16_rAX_Iw extends Executable
{
    final int immw;

    public and_o16_rAX_Iw(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        immw = Modrm.Iw(input);
    }

    public Branch execute(Processor cpu)
    {
        cpu.of = cpu.af = cpu.cf = false;
        cpu.flagResult = (short)(cpu.r_eax.get16() & immw);
        cpu.r_eax.set16((short)cpu.flagResult);
        cpu.flagStatus = SZP;
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