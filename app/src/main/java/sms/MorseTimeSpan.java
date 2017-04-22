package sms;

/**
 * Created by marceloprado on 22/04/17.
 */

public enum MorseTimeSpan {
    WORD(1000),
    CHARACTER(500),
    TRACO(250);
    private int timeSpan;

    private MorseTimeSpan(int value) {
        timeSpan = value;
    }

    public int getTime(){
        return timeSpan;
    }
}