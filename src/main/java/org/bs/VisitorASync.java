package org.bs;

import org.cef.callback.CefStringVisitor;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class VisitorASync {
    private CefStringVisitor extVis;
    private CefStringVisitor intVis;
    private Consumer<CefStringVisitor> cap;
    private final String[] temp = new String[] {""};

    public VisitorASync(CefStringVisitor vis, Consumer<CefStringVisitor> method) {
        this.extVis = vis;
        cap = method;
        intVis = new CefStringVisitor() {
            @Override
            public void visit(String string) {
                temp[0] = string;
            }
        };
        does();
    }

    public void does() {
        cap.accept(intVis);
        while (temp[0] == "") {}
        extVis.visit(temp[0]);
    }

}
