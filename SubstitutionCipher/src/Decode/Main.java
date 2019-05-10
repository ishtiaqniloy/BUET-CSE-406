package Decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static final int THR = 4;
    public static final int MAX_ITR = 10;

    static char []textToCipher = new char[26];             ///map from normal text character to cipher text character
    static boolean []textToCipherFound = new boolean[26];

    static char []cipherToText = new char[26];             ///map from cipher text character to normal text character
    static boolean []cipherToTextFound = new boolean[26];

    static int foundLetters = 0;


    public static void main(String []args) throws FileNotFoundException {
//================================================================================================
//  Initialization
//================================================================================================
        int []cipherCharCount = new int[26];

        for (int i = 0; i < 26; i++) {
            textToCipherFound[i] = false;
            cipherToTextFound[i] = false;
            cipherCharCount[i] = 0;
        }

        System.out.println("Check1 : " + checkValidity());

        try{

//================================================================================================
//  Input
//================================================================================================
            File input = new File("Input.txt");
            if(input.exists()){
                System.out.println("Input file found!");
            }
            else{
                System.out.println("Input file not found. Exiting...");
                return;
            }

            Scanner scanner = new Scanner(new FileInputStream(input));

            String cipheredText = scanner.nextLine();
            System.out.println("Ciphered Text : " + cipheredText);

            String backupCipheredText = cipheredText.substring(0);



            String freq = scanner.nextLine();
            freq = freq.replaceAll(",", "");
            System.out.println("Frequent Letters : " + freq);

            String[] freqLetters = freq.split(" ");
//            for ( String s: freqLetters) {
//                System.out.println(s);
//            }



            String w = scanner.nextLine();
            w = w.replaceAll(",", "");
            System.out.println("Words : " + w);

            String[] words = w.split(" ");
//            for ( String s: words) {
//                System.out.println(s);
//            }


//================================================================================================
//  Counting Letter frequency
//================================================================================================


            for (int i = 0; i < cipheredText.length(); i++) {
                cipherCharCount[cipheredText.charAt(i)-'A']++;
            }

//            for (int i = 0; i < 26; i++) {
//                System.out.println( (char)(i+'A') + " : " +  cipherCharCount[i]);
//            }


//================================================================================================
//  Using most used letters to find few mappings
//================================================================================================

            for (int i = 0; i < freqLetters.length; i++) {
                int max = 0;
                int maxi = -1;

                for (int j = 0; j < 26; j++) {
                    if(cipherCharCount[j] > max){
                        max = cipherCharCount[j];
                        maxi = j;
                    }
                }

                cipherCharCount[maxi] = -1;

                textToCipher[freqLetters[i].charAt(0)-'a'] = (char)(maxi+'A');
                textToCipherFound[freqLetters[i].charAt(0)-'a'] = true;

                cipherToText[maxi] = freqLetters[i].charAt(0);
                cipherToTextFound[maxi] = true;

                foundLetters++;

                System.out.println(freqLetters[i].charAt(0) + " = " + (char)(maxi+'A') );

                cipheredText = cipheredText.replace( (char)(maxi+'A'), freqLetters[i].charAt(0));


            }

            System.out.println(cipheredText);

            System.out.println("Check2 : " + checkValidity());



//================================================================================================
//  Using available words to find few mappings
//================================================================================================

            boolean []wordMatched = new boolean[words.length];
            int numWordMatched = 0;
            for (int i = 0; i < words.length; i++) {
                wordMatched[i] = false;
            }

            for (int itr = 0; itr < MAX_ITR; itr++) {

                for (int wordIdx = 0; wordIdx < words.length ; wordIdx++) {
                    if(wordMatched[wordIdx]){
                        continue;
                    }

                    String word = words[wordIdx];

                    char matchingChar = 0;
                    int matchingIdx = -1;

                    for (int i = 0; i < word.length(); i++) {
                        if(textToCipherFound[word.charAt(i)-'a']){
                            matchingChar = word.charAt(i);
                            matchingIdx = i;
                            break;
                        }
                    }

                    for (int i = 0; i < cipheredText.length(); i++) {
                        int numMathcedChar = 1;
                        boolean mismatch = false;

                        if(cipheredText.charAt(i)==matchingChar){
                            for (int k = 1; matchingIdx+k < word.length() && i+k < cipheredText.length(); k++) {
                                if( !textToCipherFound[word.charAt(matchingIdx+k)-'a'] && cipheredText.charAt(i+k)>='A' && cipheredText.charAt(i+k)<='Z'){
                                    continue;
                                }
                                else if(cipheredText.charAt(i+k)!= word.charAt(matchingIdx+k)){
                                    mismatch = true;
                                    break;
                                }
                                else{
                                    numMathcedChar++;
                                }
                            }
                        }
                        if(mismatch  || numMathcedChar < THR){
                            continue;
                        }

                        ///updating map
                        wordMatched[wordIdx] = true;
                        numWordMatched++;

                        for (int j = 0; j < word.length(); j++) {
                            int ctIdx = i-matchingIdx+j;

                            if(word.charAt(j) == cipheredText.charAt(ctIdx)){
                                continue;
                            }
                            else if(cipheredText.charAt(ctIdx)>='a' && cipheredText.charAt(ctIdx)<='z'){
                                System.out.println("ERROR!!!!! Found mismatch!!!!!");
                                return;
                            }

                            textToCipher[word.charAt(j)-'a'] = cipheredText.charAt(ctIdx);
                            textToCipherFound[word.charAt(j)-'a'] = true;

                            cipherToText[cipheredText.charAt(ctIdx)-'A'] = word.charAt(j);
                            cipherToTextFound[cipheredText.charAt(ctIdx)-'A'] = true;

                            foundLetters++;

                            cipheredText = cipheredText.replace(cipheredText.charAt(ctIdx), word.charAt(j));

                        }

                        System.out.println("Found word " + wordIdx + " at index " + (i-matchingIdx) + " : " + word);
                        System.out.println("Changed text: " + cipheredText);
                        System.out.println("check3 : " + checkValidity());

                        break;



                    }





                }

                if(words.length-numWordMatched==1){
                    itr = MAX_ITR-2;
                }
                else if(words.length-numWordMatched<1){
                    break;
                }

            }


//================================================================================================
//  RESULTS
//================================================================================================

            System.out.println();
            System.out.println();
            System.out.println();

            System.out.println("===========================RESULT===========================");

            System.out.println("Cipher Text  : " + backupCipheredText);
            System.out.println("Decoded Text : " + cipheredText);







        }catch (Exception e){
            e.printStackTrace();
        }


    }

    static boolean checkValidity(){
        int num1 = 0, num2 = 0;

        for (int i = 0; i < 26; i++) {
            if(textToCipherFound[i]){
                if( cipherToText[textToCipher[i]-'A']-'a' != i ){
                    System.out.println("MISMATCH 1");
                    return false;
                }
                else if(!cipherToTextFound[textToCipher[i]-'A']){
                    System.out.println("MISMATCH 2");
                    return false;
                }
                num1++;
            }
            if(cipherToTextFound[i]){
                if( textToCipher[cipherToText[i]-'a']-'A' != i  ){
                    System.out.println("MISMATCH 3");
                    return false;
                }
                else if( !textToCipherFound[cipherToText[i]-'a']  ){
                    System.out.println("MISMATCH 4");
                    return false;
                }
                num2++;
            }
        }

        if(num1 != foundLetters || num2 != foundLetters){
            System.out.println("MISMATCH 5");
            return false;
        }

        return true;

    }


}
