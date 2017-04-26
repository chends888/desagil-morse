package sms;

/**
 * Created by daniruhman on 22/04/17.
 */

public class MorseMainTest {

    public static void main(String[] args) {
//        MorseCoder mc = new MorseCoder();
//
//        mc.inOrderPrint();
//
//        // b decode
//        System.out.println("Decode Test 1");
//        String str = "-...";
//        System.out.println("str = " + str);
//        System.out.println("str should decode to: b");
//        System.out.println("decode(str) = " + mc.decode(str));
//        testResults("b", mc.decode(str));
//
//        // i decode
//        System.out.println("Decode Test 2");
//        str = "..";
//        System.out.println("str = " + str);
//        System.out.println("str should decode to: i");
//        System.out.println("decode(str) = " + mc.decode(str));
//        testResults("i", mc.decode(str));
//
//        // 2 decode
//        System.out.println("Decode Test 3");
//        str = "..---";
//        System.out.println("str = " + str);
//        System.out.println("str should decode to: 2");
//        System.out.println("decode(str) = " + mc.decode(str));
//        testResults("2", mc.decode(str));
//
//        // 9 decode
//        System.out.println("Decode Test 4");
//        str = "----.";
//        System.out.println("str = " + str);
//        System.out.println("str should decode to: 9");
//        System.out.println("decode(str) = " + mc.decode(str));
//        testResults("9", mc.decode(str));
//
//        //sos decode
//        System.out.println("Decode Test 5");
//        str = "... --- ...";
//        System.out.println("str = " + str);
//        System.out.println("str should decode to: sos");
//        System.out.println("decode(str) = " + mc.decode(str));
//        testResults("sos", mc.decode(str));

//        // --.. encode
//        System.out.println("Encode Test 1");
//        str = "z";
//        System.out.println("str = " + str);
//        System.out.println("str should encode to: --..");
//        System.out.println("encode(str) = " + mc.encode(str));
//        testResults("--..", mc.encode(str));
//
//        // ... --- ... encode
//        System.out.println("Encode Test 2");
//        str = "sos";
//        System.out.println("str = " + str);
//        System.out.println("str should encode to: ... --- ...");
//        System.out.println("encode(str) = " + mc.encode(str));
//        testResults("... --- ...", mc.encode(str));
//
//        // .-. ..- -. ..-. --- .-. .-. . ... - .-. ..- -. encode
//        System.out.println("Encode Test 3");
//        str = "runforrestrun";
//        System.out.println("str = " + str);
//        System.out.println("str should encode to: .-. ..- -. ..-. --- .-. .-. . ... - .-. ..- -.");
//        System.out.println("encode(str) = " + mc.encode(str));
//        testResults(".-. ..- -. ..-. --- .-. .-. . ... - .-. ..- -.", mc.encode(str));

    }
    public static void testResults(String one, String two) {
        if(one.trim().equals(two.trim()))
            System.out.println("Test: Passed");
        else
            System.out.println("Test: Failed");
        System.out.println();
    }

}