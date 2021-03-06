// jchdl: Jianchang Constructed Hardware Description Library
// Copyright (c) 2018 Jianchang Wang <wjcdx@qq.com>
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. Neither the name of the copyright holders nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// THE POSSIBILITY OF SUCH DAMAGE.
package org.jchdl.model.gsl.operator.equality;

import org.jchdl.model.gsl.core.datatype.helper.WireVec;
import org.jchdl.model.gsl.core.datatype.net.Wire;
import org.jchdl.model.gsl.core.io.Input;
import org.jchdl.model.gsl.core.meta.AtomicNode;
import org.jchdl.model.gsl.core.value.Value;

// TODO: impl using gates & switches
public class LogicalEqual extends AtomicNode {
    private int nBits = 0;

    public LogicalEqual(Wire out, WireVec in1, WireVec in2) {
        nBits = in1.nBits();
        in(in1.wires());
        in(in2.wires());
        out(out);
        construct();
    }

    @Override
    public void atomic() {
        for (Input input : inputs()) {
            if (input.value.equals(Value.Vx) || input.value.equals(Value.Vz)) {
                out(0).value.v = Value.VALUE_X;
                return;
            }
        }

        for (int i = 0; i < nBits; i++) {
            Input input1 = in(i);
            Input input2 = in(i + nBits);
            if (input1.value.equals(input2.value)) {
                out(0).value.v = Value.VALUE_1;
            } else {
                out(0).value.v = Value.VALUE_0;
                break;
            }
        }
    }

    @Override
    public String primitive() {
        return "logiceq";
    }

    public static LogicalEqual inst(Wire out, WireVec in1, WireVec in2) {
        return new LogicalEqual(out, in1, in2);
    }

    public static void main(String args[]) {
        WireVec in1 = new WireVec(8);
        WireVec in2 = new WireVec(8);
        Wire out = new Wire();

        LogicalEqual.inst(out, in1, in2);

        in1.assign(new Value[] {
                Value.V1, Value.V1, Value.V1, Value.V1,
                Value.V0, Value.V0, Value.V0, Value.V1,
        });
        in2.assign(new Value[] {
                Value.V1, Value.V1, Value.V1, Value.V1,
                Value.V0, Value.Vx, Value.V0, Value.V1,
        });

        in1.propagate();
        in2.propagate();

        System.out.println("out: " + out.getValue().toString());
    }
}
