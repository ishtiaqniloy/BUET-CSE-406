package Decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {



    public static void main(String []args) throws FileNotFoundException {
        System.out.println("Hello World");

        char []textToCipher = new char[26];             ///map from normal text character to cipher text character
        boolean []textToCipherFound = new boolean[26];

        char []cipherToText = new char[26];             ///map from cipher text character to normal text character
        boolean []cipherToTextFound = new boolean[26];

        int []cipherCharCount = new int[26];

        for (int i = 0; i < 26; i++) {
            textToCipherFound[i] = false;
            cipherToTextFound[i] = false;
            cipherCharCount[i] = 0;
        }

        try{
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



        }catch (Exception e){
            e.printStackTrace();
        }




    }


}
