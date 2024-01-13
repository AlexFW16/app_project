package uk.bradford.app_project;


//import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.bradford.app_project.Util.*;

//TODO rename class
// TODO consider spaces in the text
public class Crypto {


    // TODO maybe outsource alphabets? -> alphabets are not that relevant anymore
    static final String[] characters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z 0 1 2 3 4 5 6 7 8 9".split(" ");
    // Alphabet with only uppercase letters
    static final Set<Character> alphabet1 = Arrays.stream(characters).limit(26).map(s -> s.charAt(0)).collect(Collectors.toSet());

    // Alphabet with uppercase letters + numbers
    static final Set<String> alphabet2 = Arrays.stream(characters).collect(Collectors.toSet());

    // Checks if the alphabet used in key/message works for this cipher
    // Matches cipher to alphabet
    // e.g. Vigenere does not allow for numbers
    private static boolean checkAlphabet(Cipher.Type cipher, String text) {
        Set<Character> alphabet;

        switch (cipher) {

            case VIGENERE:
                alphabet = Crypto.alphabet1;
                text = text.toUpperCase(); // Vigenere is only considered in uppercase, but accepts mixed inputs
                break;
            case XOR: // WIth XOR, everything is converted to its binary representation, hence no alphabet check needed
            case SUBSTITUTION:
            case TRANSPOSITION:
                return true;
            default:
                throw new IllegalArgumentException("Given cipher does not exist or corresponding alphabet has not yet been implemented");
        }

        // Returns false if at least one char in the message is not contained in the corresponding alphabet
        for (char c : text.toCharArray()) {
            if (!alphabet.contains(c)) return false;
        }
        return true;
    }

    public static String encrypt(Cipher.Type cipher, String plaintext, String key) throws IllegalArgumentException {

        if (!checkAlphabet(cipher, plaintext + key))
            throw new IllegalArgumentException("Contains letters that are not in the alphabet of this cipher");
        if (plaintext.trim().equals("") || key.trim().equals(""))
            throw new IllegalArgumentException("Key or plaintext cannot be empty");


        switch (cipher) {

            case VIGENERE:
                return encryptVigenere(plaintext.toUpperCase(), key.toUpperCase());
            case XOR:
                return encryptXOR(plaintext, key);
            case SUBSTITUTION:
                return encryptSubstitutionCipher(plaintext, key);
            case TRANSPOSITION:
                return encryptTranspositionCipher(plaintext, key);
            default:
                throw new IllegalArgumentException("Given Cipher does not exist or is not implemented (Encryption)");
        }

    }

    public static String decrypt(Cipher.Type cipher, String ciphertext, String key) {

        if (!checkAlphabet(cipher, ciphertext + key))
            throw new IllegalArgumentException("Contains letters that are not in the alphabet of this cipher");

        if (ciphertext.trim().equals("") || key.trim().equals(""))
            throw new IllegalArgumentException("Key or plaintext cannot be empty");

        switch (cipher) {

            case VIGENERE:
                return decryptVigenere(ciphertext, key);
            case XOR:
                return decryptXOR(ciphertext, key);
            case SUBSTITUTION:
                return decryptSubstitutionCipher(ciphertext, key);
            case TRANSPOSITION:
                return decryptTranspositionCipher(ciphertext, key);
            default:
                throw new IllegalArgumentException("Given Cipher does not exist or is not implemented (Decryption)");


        }

    }

    /* Encrypts the given string by shifting each character for the amount of the corresponding character in the key
    , with A = 0, B = 1, ..., ex.:
   ABCABCA
   MESSAGE
   -------
   MFUSBIE

    */
    private static String encryptVigenere(String plaintext, String key) {

        // make the key as long as the plaintext
        key = keyToMessageLength(plaintext, key);

        // Creates 2 int arrays that contain the index of each letter in the alphabet
        int[] keyArr = key.chars().limit(plaintext.length()).map(c -> c - 65).toArray();
        int[] plaintextArr = plaintext.chars().map(c -> c - 65).toArray();

        int[] out = new int[plaintextArr.length];
        // Here, the main encryption happens
        for (int i = 0; i < plaintext.length(); i++)
            out[i] = (plaintextArr[i] + keyArr[i]) % 26; // to stay in the alphabet range


        return Arrays.stream(out).map(c -> c + 65) // mapping back to the ASCII representation
                .mapToObj(c -> (char) c) // casting back to character
                .map(String::valueOf) // maps every character to a String object
                .collect(Collectors.joining());
    }

    private static String decryptVigenere(String ciphertext, String key) {

        // make the key as long as the plaintext
        key = keyToMessageLength(ciphertext, key);

        // Creates 2 int arrays that contain the index of each letter in the alphabet
        int[] keyArr = key.chars().limit(ciphertext.length()).map(c -> c - 65).toArray();
        int[] plaintextArr = ciphertext.chars().map(c -> c - 65).toArray();

        int[] out = new int[plaintextArr.length];
        // Here, the main decryption happens
        for (int i = 0; i < ciphertext.length(); i++)
            out[i] = (plaintextArr[i] - keyArr[i] + 26) % 26;

        return Arrays.stream(out).map(c -> c + 65) // mapping back to the ASCII representation
                .mapToObj(c -> (char) c) // casting back to character
                .map(String::valueOf) // maps every character to a String object
                .collect(Collectors.joining());


    }

    private static String encryptXOR(String plainText, String key) {
        key = keyToMessageLength(plainText, key);

        int[] binaryPlaintext = fromStringToIntArray(fromStringToBinaryString(plainText));
        int[] binaryKey = fromStringToIntArray(fromStringToBinaryString(key));
        int[] out = new int[binaryPlaintext.length];

        for (int i = 0; i < binaryPlaintext.length; i++) {
            //out[i] = binaryCiphertext[i] ^ binaryKey[i];
            out[i] = (binaryPlaintext[i] == binaryKey[i]) ? 0 : 1;
            //System.out.printf("%d XOR %d = %d\n", binaryCiphertext[i], binaryKey[i], out[i]);
        }

        return fromBinaryStringToString(fromIntArrayToString(out));
    }

    private static String decryptXOR(String ciphertext, String key) {
        return encryptXOR(ciphertext, key);
    }

    private static String encryptSubstitutionCipher(String plaintext, String key) throws IllegalArgumentException {

        Map characterMapping = parsePermutationSubstitution(key, false);

        StringBuilder sb = new StringBuilder();

        for (char c : plaintext.toCharArray()) {

            if (characterMapping.keySet().contains(c)) sb.append(characterMapping.get(c));
            else sb.append(c);
        }

        return sb.toString();
    }

    private static String decryptSubstitutionCipher(String ciphertext, String key) throws IllegalArgumentException {

        Map characterMapping = parsePermutationSubstitution(key, true);
        StringBuilder sb = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {

            if (characterMapping.keySet().contains(c)) sb.append(characterMapping.get(c));
            else sb.append(c);
        }

        return sb.toString();
    }

    /*
    Similar to substitution cipher, but here the permutation refers to the position of the character, so
    (12) e.g. switches the first 2 chars
     */
    private static String encryptTranspositionCipher(String plaintext, String key) {
        Map<Integer, Integer> characterMapping = parsePermutationTransposition(key, false, plaintext.length());
        char[] plaintextArray = plaintext.toCharArray();
        char[] out = Arrays.copyOf(plaintextArray, plaintextArray.length);

        for (int i = 0; i < plaintext.length(); i++) {

            if (characterMapping.keySet().contains(i))
                out[characterMapping.get(i)] = plaintextArray[i];
        }
        return String.valueOf(out);


    }

    private static String decryptTranspositionCipher(String ciphertext, String key) {

        Map<Integer, Integer> characterMapping = parsePermutationTransposition(key, true, ciphertext.length());
        char[] ciphertextArray = ciphertext.toCharArray();
        char[] out = Arrays.copyOf(ciphertextArray, ciphertextArray.length);

        for (int i = 0; i < ciphertext.length(); i++){

            if (characterMapping.keySet().contains(i))
                out[characterMapping.get(i)] = ciphertextArray[i];
        }

        return String.valueOf(out);
    }

}

