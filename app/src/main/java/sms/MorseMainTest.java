package sms;
import sms.MorseCoder;
/**
 * Main test class for Morsecode project. Do not modify this file.
 * <p>
 * Use this file to test your morsecoder class. You output should match the output from the assignment description.
 *
 */
public class MorseMainTest {

    public static void main(String[] args) {
        //Do not modify this file.
        MorseCoder mc = new MorseCoder();
        MorseCoder mc1 = new MorseCoder();
        MorseCoder mc2 = new MorseCoder();
        MorseCoder mc3 = new MorseCoder();

        mc.inOrderPrint();

        // sos decode
        System.out.println("Decode Test 1");
        String str = "... --- ...";
        System.out.println("str = " + str);
        System.out.println("str should decode to: sos");
        System.out.println("decode(str) = " + mc.decode(str));
        testResults("sos", mc.decode(str));

        // abcdef...xyz decode
        System.out.println("Decode Test 2");
        str = ".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -."
                + " --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..";
        System.out.println("str = " + str);
        System.out.println("str should decode to: abcdefghijklmnopqrstuvwxyz");
        System.out.println("decode(str) = " + mc.decode(str));
        testResults("abcdefghijklmnopqrstuvwxyz", mc.decode(str));

        // helpmeobiwanyouremyonlyhope decode
        System.out.println("Decode Test 3");
        str = ".... . .-.. .--. -- . --- -... .. .-- .- -. -.-- --- ..-"
                + " .-. . -- -.-- --- -. .-.. -.-- .... --- .--. .";
        System.out.println("str = " + str);
        System.out.println("str should decode to: helpmeobiwanyouremyonlyhope");
        System.out.println("decode(str) = " + mc.decode(str));
        testResults("helpmeobiwanyouremyonlyhope", mc.decode(str));

        // --.. encode
        System.out.println("Encode Test 1");
        char ltr = 'z';
        String answ = mc1.encode(ltr);
        System.out.println("str = " + ltr);
        System.out.println("str should encode to: --..");
        System.out.println("encode(str) = " + answ);
        testResults("--..", answ);

        // ... --- ... encode
        System.out.println("Encode Test 2");
        ltr = 'b';
        answ = mc2.encode(ltr);
        System.out.println("str = " + ltr);
        System.out.println("str should encode to: -...");
        System.out.println("encode(str) = " + answ);
        testResults("-...", answ);

        // .-. ..- -. ..-. --- .-. .-. . ... - .-. ..- -. encode
        System.out.println("Encode Test 3");
        ltr = 't';
        answ = mc3.encode(ltr);
        System.out.println("str = " + ltr);
        System.out.println("str should encode to: -");
        System.out.println("encode(str) = " +answ);
        testResults("-", answ);

    }
    public static void testResults(String one, String two) {
        System.out.println(one);
        System.out.println(two);
        if(one.trim().equals(two.trim()))
            System.out.println("Test: Passed");
        else
            System.out.println("Test: Failed");
        System.out.println();
    }

}