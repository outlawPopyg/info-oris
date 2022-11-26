package com.outlaw;

import java.util.Arrays;

public class TestKey {
    public static void main(String[] args) {
        SuperPacket packet = SuperPacket.create("key");
        packet.setValue(1, "abc");

        SuperPacket dec = SuperPacket.parse(packet.toByteArray());
        System.out.println(Arrays.toString(dec.getKey()));
    }
}
