package uk.bradford.app_project;

import android.util.SparseLongArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//TODO rename class
// TODO consider spaces in the text
public class Crypto {


    // TODO maybe outsource alphabets?
    static final String[] characters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9".split(" ");
    // Alphabet with only uppercase letters
    static final Set<String> alphabet1 = Arrays.stream(characters).limit(26).collect(Collectors.toSet());

    // Alphabet with uppercase letters + numbers
    static final Set<String> alphabet2 = Arrays.stream(characters).collect(Collectors.toSet());

    // Checks if the alphabet used in key/message works for this cipher
    // Matches cipher to alphabet
    // e.g. Vigenere does not allow for numbers
    private static boolean checkAlphabet(Cipher cipher, String text) {
        Set<String> alphabet;

        switch (cipher) {

            case VIGENERE:
                alphabet = Crypto.alphabet1;
                break;
            default:
                throw new IllegalArgumentException("Given cipher does not exist or corresponding alphabet has not yet been implemented");
        }

        // Returns false if at least one char in the message is not contained in the corresponding alphabet
        for (char c : text.toCharArray()) {
            if (!alphabet.contains(c)) return false;
        }
        return true;
    }

    public static String encrypt(Cipher cipher, String plaintext, String key) {

        switch (cipher) {

            case VIGENERE:
                checkAlphabet(cipher, plaintext);
                checkAlphabet(cipher, key);
                return encryptVigenere(plaintext, key);

            default:
                throw new IllegalArgumentException("Given Cipher does not exist or is not implemented (Encryption)");
        }

    }

    public static String decrypt(Cipher cipher, String ciphertext, String key) {

        switch (cipher) {

            case VIGENERE:
                checkAlphabet(cipher, ciphertext);
                checkAlphabet(cipher, key);
                return decryptVigenere(ciphertext, key);

            default:
                throw new IllegalArgumentException("Given Cipher does not exist or is not implemented (Decryption)");


        }

    }

    private static String encryptVigenere(String plaintext, String key) {

        // make the key as long as the plaintext
        if(key.length() > plaintext.length()){

        }
        else if (key.length() < plaintext.length()){

            while (key.length() < plaintext.length()){
                key += key;
            }
        }

        // Creates 2 int arrays that contain the index of each letter in the alphabet
        int[] keyArr = key.chars().limit(plaintext.length()).map(c -> c -= 65).toArray();
        int[] plaintextArr = plaintext.chars().map(c -> c -= 65).toArray();

        int[] out = new int[plaintextArr.length];
        // Here, the main encryption happens
        for(int i = 0; i < plaintext.length(); i++)
            out[i] = plaintextArr[i] + keyArr[i];

        return Arrays.stream(out).map(c -> c += 65).map(c -> (char) c).toArray().toString();

    }

    private static String decryptVigenere(String ciphertext, String key) {

        return null;

    }

}
