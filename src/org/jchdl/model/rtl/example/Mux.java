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
package org.jchdl.model.rtl.example;

import org.jchdl.model.rtl.core.datatype.Bit;
import org.jchdl.model.rtl.core.io.annotation.Input;
import org.jchdl.model.rtl.core.io.annotation.Output;
import org.jchdl.model.rtl.core.meta.Module;
import org.jchdl.model.rtl.core.meta.PropagateManager;

public class Mux extends Module {
    @Input
    private Bit a;
    @Input
    private Bit b;
    @Input
    private Bit sel;
    @Output
    private Bit out;

    public Mux(Module parent, Bit out, Bit a, Bit b, Bit sel) {
        super(parent);
        this.out = out;
        this.a = a;
        this.b = b;
        this.sel = sel;
        construct();
    }

    private void assignO() {
        out.assign(sel.boolVal() ? a : b);
    }

    @Override
    public void logic() {
        assign(out).from(a, b, sel).with(this::assignO);
    }

    public static void main(String[] args) {
        Bit a = new Bit(Bit.BIT_0);
        Bit b = new Bit(Bit.BIT_1);
        Bit s = new Bit(Bit.BIT_1);
        Bit o = new Bit();

        Mux mux = new Mux(null, o, a, b, s);
//        mux.toVerilog();

        PropagateManager.propagate(mux); // no value changed, out has default value: 0.
        System.out.println("out: " + o.toString());

        PropagateManager.propagate(mux); // no value changed, out has default value: 0.
        System.out.println("out: " + o.toString());

        s.assign(Bit.BIT_0);
        PropagateManager.propagate(mux);
        System.out.println("out: " + o.toString());

        b.assign(Bit.BIT_0);
        PropagateManager.propagate(mux);
        System.out.println("out: " + o.toString());

        b.assign(Bit.BIT_1);
        PropagateManager.propagate(mux);
        System.out.println("out: " + o.toString());
    }
}
