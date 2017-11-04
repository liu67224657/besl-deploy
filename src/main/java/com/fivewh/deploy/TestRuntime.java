/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy;

public class TestRuntime {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String code = "ab.";
        String a, b;
        int idx = code.indexOf(".");

        a = code.substring(0, idx);
        b = code.substring(idx + 1, code.length());
        System.out.println(a + "|" + b + ";");
    }
}
