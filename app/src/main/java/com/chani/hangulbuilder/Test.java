package com.chani.hangulbuilder;

class Test {
    private void test() {
        HangulBuilder builder = HangulBuilder.INSTANCE;
        String s = builder.assemble('ㄱ', 'ㅏ', 'ㄱ');
        System.out.println("SSS " + s);
    }
}
