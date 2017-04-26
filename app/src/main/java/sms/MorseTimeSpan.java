package sms;

/**
 * Created by marceloprado on 22/04/17.
 */

public enum MorseTimeSpan {
    WORD(1500),
    CHARACTER(800),
    TRACO(400);
    private int timeSpan;

    private MorseTimeSpan(int value) {
        timeSpan = value;
    }

    public int getTime(){
        return timeSpan;
    }
}