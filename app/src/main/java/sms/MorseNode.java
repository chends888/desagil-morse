package sms;

/**
 * Created by daniruhman on 22/04/17.
 */

public class MorseNode {

    private char letter;
    private MorseNode left;
    private MorseNode right;
    public static final char EMPTY = ' ';

    public MorseNode() {
        letter = EMPTY;
        left = null;
        right = null;
    }


    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public MorseNode getLeft() {
        return left;
    }

    public void setLeft(MorseNode left) {
        this.left = left;
    }

    public MorseNode getRight() {
        return right;
    }

    public void setRight(MorseNode right) {
        this.right = right;
    }

}