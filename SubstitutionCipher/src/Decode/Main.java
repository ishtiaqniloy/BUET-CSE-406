package Decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String []args) throws FileNotFoundException {
        System.out.println("Hello World");


//================================================================================================
//  Initialization
//================================================================================================
        char []textToCipher = new char[26];             ///map from normal text character to cipher text character
        boolean []textToCipherFound = new boolean[26];

        char []cipherToText = new char[26];             ///map from cipher text character to normal text character
        boolean []cipherToTextFound = new boolean[26];

        int []cipherCharCount = new int[26];
        int foundLetters = 0;

        for (int i = 0; i < 26; i++) {
            textToCipherFound[i] = false;
            cipherToTextFound[i] = false;
            cipherCharCount[i] = 0;
        }

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


            }




        }catch (Exception e){
            e.printStackTrace();
        }




    }


}
