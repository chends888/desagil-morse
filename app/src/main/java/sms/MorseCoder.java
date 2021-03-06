package sms;
import android.util.Log;

import java.io.*;
import java.util.*;
import sms.MorseNode;

/**
 * Created by daniruhman on 22/04/17.
 */

public class MorseCoder{

    private List<MorseNode> queue;
    private List<String> dictionary;
    protected MorseNode root;
//    private String encoding;

    public MorseCoder(String encoding) {
        root = new MorseNode();
//        this.encoding = encoding;
        readTreeInfo(encoding);
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
    public List<String> create_dictionary(){
        MorseNode current = this.root;
        while (true){
            if(current.getLeft()!=null){
                queue.add(current.getLeft());
            }
            if(current.getRight()!=null){
                queue.add(current.getRight());
            }
            if(queue.size() >0){
                if(!dictionary.contains(queue.get(0))){
                    dictionary.add( encode(queue.get(0).getLetter()) + " -> " + queue.get(0).getLetter());
                }
                current = queue.get(0);
                queue.remove(0);
            }
            if(queue.size() ==0){
                break;
            }
        }
        return dictionary;
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


    public String encode(char ltr) {
        MorseNode current = root;
        if (current != null) {
            String s = "";
            Stack<MorseNode> stack = new Stack<>();
            stack.push(current);

            while(!stack.isEmpty()) {
                MorseNode node = stack.peek();
                MorseNode left = node.getLeft();
                MorseNode right = node.getRight();
                if (node.getLetter() == ltr) {
                    return s;
                }
                else if(left != null && left.isOpen()) {
                    s = s + ".";
                    left.setOpen(false);
                    stack.push(left);
                }
                else if(right != null && right.isOpen()) {
                    s = s + "-";
                    right.setOpen(false);
                    stack.push(right);
                }
                else {
                    stack.pop();
                    if (!s.isEmpty()){
                        s = s.substring(0, (s.length() - 1));
                    }
                }
            }
            return s;
        }return null;
    }
}