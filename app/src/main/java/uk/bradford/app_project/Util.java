package uk.bradford.app_project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Util {

    public static String keyToMessageLength(String msg, String key) {
        while (key.length() < msg.length()) key += key;

        if (key.length() != msg.length()) key = key.substring(0, msg.length());

        return key;
    }

    // Used for XOR
    public static String fromStringToBinaryString(String input) {
        StringBuilder builder = new StringBuilder();

        for (char c : input.toCharArray()) {

            String binaryString = Integer.toBinaryString(c);

            while (binaryString.length() < 8) binaryString = "0" + binaryString;

            builder.append(binaryString);
        }

        return builder.toString();
    }

    public static String fromBinaryStringToString(String input) {

        StringBuilder sb = new StringBuilder();
        for (int i = 8; i <= input.length(); i += 8)
            sb.append((char) Integer.parseInt(input.substring(i - 8, i), 2));
        return sb.toString();
    }

    public static int[] fromStringToIntArray(String binaryString) {
        return binaryString.chars().map(c -> Character.getNumericValue(c)).toArray();
    }

    public static String fromIntArrayToString(int[] binaryArray) {
        return Arrays.stream(binaryArray).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining());
    }

    // For substitution and transposition ciphers
    public static HashMap<Integer, Integer> parsePermutationTransposition(String inputString, boolean reversed, int messageLength) {

        char[] input = inputString.replaceAll("\\)\\s+\\(", ")(").toCharArray();

        if (input[0] != '(' || input[input.length - 1] != ')')
            throw new IllegalArgumentException("Badly formatted: Does not start with \'(\' or end with \')\'");

        HashMap<Integer, Integer> cipherMapping = new HashMap<>();
        HashSet<Integer> used = new HashSet<>();

        String[] cycles = inputString.replaceAll("\\)\\s+\\(", ")(").substring(1, input.length - 1).split("\\)\\(");

        for (String cycle : cycles) {
            if (cycle.contains("(") || cycle.contains(")"))
                throw new IllegalArgumentException("Badly formatted: Wrongly placed \'(\' or \')\'");
            if(cycle.contains("0"))
                throw new IllegalArgumentException("Badly formatted: 0 is not allowed as an index");

            String[] numbersStrings = cycle.split(" ");

            try {
                if (reversed) {
                    for (int i = numbersStrings.length - 1; i >= 0; i--) {

                        int cur = Integer.valueOf(numbersStrings[i]) - 1; //
                        int next = (i > 0) ? Integer.valueOf(numbersStrings[i - 1]) - 1 : Integer.valueOf(numbersStrings[numbersStrings.length - 1]) - 1;

                        if (used.contains(cur))
                            throw new IllegalArgumentException("Badly formatted: Value \'" + cur + "\' used more than once");

                        if (cur >= messageLength)
                            throw new IllegalArgumentException("Badly formatted: Value \'" + numbersStrings[i] + "\' is too big for message of size " + messageLength);

                        cipherMapping.put(cur, next);
                        used.add(Integer.valueOf(numbersStrings[i]));
                    }
                } else {
                    for (int i = 0; i < numbersStrings.length; i++) {

                        int cur = Integer.valueOf(numbersStrings[i]) - 1;
                        int next = (i < numbersStrings.length - 1) ? Integer.valueOf(numbersStrings[i + 1]) - 1 : Integer.valueOf(numbersStrings[0]) - 1;

                        if (used.contains(cur))
                            throw new IllegalArgumentException("Badly formatted: Value \'" + numbersStrings[i] + "\' used more than once");

                        if (cur >= messageLength)
                            throw new IllegalArgumentException("Badly formatted: Value \'" + numbersStrings[i] + "\' is too big for message of size " + messageLength);

                        cipherMapping.put(cur, next);


                    }

                }

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Badly formatted: Only integers allowed");
            }

        }

        return cipherMapping;
    }

    /*
    Takes as input a permutation in the form of disjoint cycles and returns a HashMap
    of characters with the respective mappings.
     */
    public static HashMap<Character, Character> parsePermutationSubstitution(String inputString, boolean reversed) throws IllegalArgumentException {

        // also replaces all whitespaces in the permutation (cannot be used for tranposition as whitespaces there are used to seperate numbers)
        char[] input = inputString.replaceAll("\\)\\s+\\(", ")(").toCharArray();


        // must start and end with bracket
        if (input[0] != '(' || input[input.length - 1] != ')')
            throw new IllegalArgumentException("Badly formatted: Does not start with \'(\' or end with \')\'");

        HashMap<Character, Character> cipherMapping = new HashMap<>();
        HashSet<Character> used = new HashSet<>(); // used to efficiently check if char is used multiple times

        String[] cycles = inputString.replaceAll("\\)\\s+\\(", ")(").substring(1, input.length - 1).split("\\)\\(");

        /*
         creating the cipherMapping from the cycles and checking for remaining brackets, which would mean
         that the input was badly formatted as no brackets should be left -> Needs to be adapted if
         escaped brackets should be used later. Also checks for
         */
        for (String cycle : cycles) {
            if (cycle.contains("(") || cycle.contains(")"))
                throw new IllegalArgumentException("Badly formatted: Wrongly placed \'(\' or \')\'");

            char[] cycleArray = cycle.toCharArray();

            if (reversed) { // Reverses the mapping (for decryption)
                for (int i = cycleArray.length - 1; i >= 0; i--) {
                    char cur = cycleArray[i];
                    if (used.contains(cur))
                        throw new IllegalArgumentException("Badly formatted: Character \'" + cur + "\' used more than once");
                    used.add(cur);

                    if (i == 0) // the first char is mapped to the last one (cycle)
                        cipherMapping.put(cur, cycleArray[cycleArray.length - 1]);
                    else cipherMapping.put(cur, cycleArray[i - 1]);

                }

            } else {
                for (int i = 0; i < cycleArray.length; i++) {
                    char cur = cycleArray[i];
                    if (used.contains(cur))
                        throw new IllegalArgumentException("Badly formatted: Character \'" + cur + "\' used more than once");

                    used.add(cur);

                    if (i + 1 >= cycleArray.length) // the last char is mapped to the first one (cycle)
                        cipherMapping.put(cur, cycleArray[0]);
                    else cipherMapping.put(cur, cycleArray[i + 1]);
                }
            }
        }
        return cipherMapping;

    }


}


