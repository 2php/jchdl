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
package org.jchdl.model.gsl.core.gate.ni;

import org.jchdl.model.gsl.core.datatype.net.Wire;
import org.jchdl.model.gsl.core.gate.ni.atomic.Gate2i;

public class Xor extends GateNi {

    public Xor(Wire out, Wire in1, Wire... inWires) {
        super(out, in1, inWires);
    }

    @Override
    public Gate2i gate2i(Wire out, Wire in1, Wire in2) {
        return new org.jchdl.model.gsl.core.gate.ni.atomic.Xor(out, in1, in2);
    }

    public static Xor inst(Wire out, Wire in1, Wire... inWires) {
        return new Xor(out, in1, inWires);
    }

    public static Xor[] inst(Wire[] out, Wire[] in1, Wire[] in2) {
        Xor[] xors = new Xor[out.length];
        for (int i = 0; i < out.length; i++) {
            xors[i] = new Xor(out[i], in1[i], in2[i]);
        }
        return xors;
    }
}