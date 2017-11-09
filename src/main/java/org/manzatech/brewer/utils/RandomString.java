package org.manzatech.brewer.utils;

public class RandomString {

    /*
     * This define the default length of random string
     */
    private static final int DEFAULT_LENGTH = 12;
    /*
     * Here is the allowed characters in the random string
     */
    private static final char[] DEFAULT_ALLOWED_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};


    public static String generate() {
        return generateRandomString(DEFAULT_LENGTH, DEFAULT_ALLOWED_CHARS);
    }

    public static String generate(int length) {
        return generateRandomString(length, DEFAULT_ALLOWED_CHARS);
    }

    public static String generate(char[] allowedCharacters) {
        return generateRandomString(DEFAULT_LENGTH, allowedCharacters);
    }

    public static String generate(int length, char[] allowedCharacters) {
        return generateRandomString(length, allowedCharacters);
    }

    private static String generateRandomString(int length, char[] allowedCharacters) {
        String tmp = "";
        for (int i = 0; i < length; i++) {
            int c = (int) Math.floor(Math.random()*allowedCharacters.length);
            tmp += allowedCharacters[c];
        }
        return tmp;
    }

}