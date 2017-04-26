package sms;
import java.io.*;
import java.util.*;
import sms.MorseNode;

/**
 * Created by daniruhman on 22/04/17.
 */

public class MorseCoder{

    static private MorseNode root;

    public MorseCoder(String encoding) {
        root = new MorseNode();
        readTreeInfo(encoding);
    }

    public MorseCoder() {
    }

    //Le arvore do arquivo
    private void readTreeInfo(String input) {
        Scanner input2 = new Scanner(input);
        while (input2.hasNextLine()) {
            String data = input2.nextLine().trim();
            if (data.length() > 0) {
                add(data.substring(1).trim(), data.charAt(0));
            }
        }
        input2.close();
    }

    //Add letra na arvore
    private void add(String mcode, char ltr) {
        MorseNode current = root;
        String signal = " ";

        for (int i = 0; i < mcode.length(); i++) {
            signal = mcode.substring(i, i + 1);
            if (signal.equals(".")) {
                if (current.getLeft() != null) {
                    current = current.getLeft();
                } else {
                    current.setLeft(new MorseNode());
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() != null) {
                    current = current.getRight();
                } else {
                    current.setRight(new MorseNode());
                    current = current.getRight();
                }
            }
        }
        current.setLetter(ltr);
    }

    public void inOrderPrint() {
        MorseNode current = root;
        printInorder(current);
    }

    //helper method
    private void printInorder(MorseNode current) {
        if (current != null) {
            printInorder(current.getLeft());
            System.out.print(current.getLetter());
            printInorder(current.getRight());
        }
    }

    //Recebe string em morse e retorna caracter ou frase em romano
    public String decode(String str) {
        String signal = "";
        StringBuffer result = new StringBuffer("");
        MorseNode current = root;

        for (int i = 0; i < str.length(); i++) {
            signal = str.substring(i, i + 1);
            if (signal.equals(".")) {
                if (current.getLeft() != null) {
                    current = current.getLeft();
                } else {
                    current.setLeft(new MorseNode());
                    current = current.getLeft();
                }
            } else if (signal.equals("-")) {
                if (current.getRight() != null) {
                    current = current.getRight();
                } else {
                    current.setRight(new MorseNode());
                    current = current.getRight();
                }
            } else {
                result = result.append(current.getLetter());
                current = root;
            }
        }
        result = result.append(current.getLetter());

        return result.toString();
    }


    //Ficou para a proxima Sprint
//    public String encode(String str) {
//        MorseNode current = root;
//        String result = "";
//        String s = "";
//        char ltr;
//
//        for (int i = 0; i < str.length(); i++) {
//            ltr = str.charAt(i);
//            result = searchTree(current, ltr, s);
//        }
//        return result;
//    }
//
//    public String searchTree(MorseNode current, char ltr, String s) {
//        char temp = current.getLetter();  //for debugging purposes
//
//        if (current.getLetter() == ltr) {
//            return s;
//        } else {
//            if (current.getLeft() != null) {
//                return searchTree(current.getLeft(), ltr, s + ".");
//            }
//            if (current.getRight() != null) {
//                return searchTree(current.getRight(), ltr, s + "-");
//            }
//            return s;
//        }
//    }
}