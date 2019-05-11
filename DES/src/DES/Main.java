package DES;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Main {
//==================================================================================================
//Matrices
//==================================================================================================
    static int[] PI = { 58, 50, 42, 34, 26, 18, 10, 2,
                        60, 52, 44, 36, 28, 20, 12, 4,
                        62, 54, 46, 38, 30, 22, 14, 6,
                        64, 56, 48, 40, 32, 24, 16, 8,
                        57, 49, 41, 33, 25, 17, 9, 1,
                        59, 51, 43, 35, 27, 19, 11, 3,
                        61, 53, 45, 37, 29, 21, 13, 5,
                        63, 55, 47, 39, 31, 23, 15, 7};

    static int[] CP_1 = {57, 49, 41, 33, 25, 17, 9,
                        1, 58, 50, 42, 34, 26, 18,
                        10, 2, 59, 51, 43, 35, 27,
                        19, 11, 3, 60, 52, 44, 36,
                        63, 55, 47, 39, 31, 23, 15,
                        7, 62, 54, 46, 38, 30, 22,
                        14, 6, 61, 53, 45, 37, 29,
                        21, 13, 5, 28, 20, 12, 4};

    static int[] CP_2 = {14, 17, 11, 24, 1, 5, 3, 28,
                        15, 6, 21, 10, 23, 19, 12, 4,
                        26, 8, 16, 7, 27, 20, 13, 2,
                        41, 52, 31, 37, 47, 55, 30, 40,
                        51, 45, 33, 48, 44, 49, 39, 56,
                        34, 53, 46, 42, 50, 36, 29, 32};

    static int[] E = {32, 1, 2, 3, 4, 5,
                    4, 5, 6, 7, 8, 9,
                    8, 9, 10, 11, 12, 13,
                    12, 13, 14, 15, 16, 17,
                    16, 17, 18, 19, 20, 21,
                    20, 21, 22, 23, 24, 25,
                    24, 25, 26, 27, 28, 29,
                    28, 29, 30, 31, 32, 1};

    static int[] PI_2 = {35, 38, 46, 6, 43, 40, 14, 45,
                        33, 19, 26, 15, 23, 8, 22, 10,
                        12, 11, 5, 25, 27, 21, 16, 31,
                        28, 32, 34, 24, 9, 37, 2, 1};

    static int[] P = {16, 7, 20, 21, 29, 12, 28, 17,
                    1, 15, 23, 26, 5, 18, 31, 10,
                    2, 8, 24, 14, 32, 27, 3, 9,
                    19, 13, 30, 6, 22, 11, 4, 25};

    static int[] PI_1 = {40, 8, 48, 16, 56, 24, 64, 32,
                        39, 7, 47, 15, 55, 23, 63, 31,
                        38, 6, 46, 14, 54, 22, 62, 30,
                        37, 5, 45, 13, 53, 21, 61, 29,
                        36, 4, 44, 12, 52, 20, 60, 28,
                        35, 3, 43, 11, 51, 19, 59, 27,
                        34, 2, 42, 10, 50, 18, 58, 26,
                        33, 1, 41, 9, 49, 17, 57, 25};

    static int[] SHIFT = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

    static ArrayList<String> itrKeys = new ArrayList<String>();
    public static final int NUM_ITR = 16;

    public static void main(String []args){

        //==================================================================================================
        //Input
        //==================================================================================================
        System.out.println("Give 8 char key and plain text: ");

        Scanner scanner = new Scanner(System.in);

        String key = scanner.nextLine();

        if(key.length()!=8){
            System.out.println("WRONG KEY LENGTH!!!!!");
            return;
        }

        String plainText = scanner.nextLine();
        String backupPlainText = plainText.substring(0);

//        String bin = charToBinaryString(' ');
//        System.out.println(bin + " = " + binaryStringToChar(bin));

        System.out.println("Key : " + key);
        System.out.println("Plain Text : " + plainText);

        int mod = plainText.length()%8;
        if(mod > 0){
            for (int i = 0; i < 8-mod; i++) {
                plainText = plainText.concat("~");
            }
        }

        System.out.println("Concatenated plain text : " + plainText);
        System.out.println();

        //==================================================================================================
        //generating key for each round
        //==================================================================================================
        String keyBinary = stringToBinaryString(key);
        char []transposedKey = transpose(keyBinary.toCharArray(), CP_1);

        System.out.println("InitialKey    : " + keyBinary);
        System.out.println("TransposedKey : " + new String(transposedKey));


        //generating key for each iteration
        for (int itr = 0; itr < 16; itr++) {
            char[] lk = leftRotate(new String(transposedKey).substring(0, 28).toCharArray(), SHIFT[itr]);
            char[] rk = leftRotate(new String(transposedKey).substring(28).toCharArray(), SHIFT[itr]);

            String fullString = new String(lk) + new String(rk);

            transposedKey = fullString.toCharArray(); //?????????????????????????

            char []kiArr = transpose(fullString.toCharArray(), CP_2);

            String ki = new String(kiArr);
            itrKeys.add(ki);

            //System.out.println("Key " + itr + " : " + ki);

        }


        String cipheredText = encrypt(plainText, true);
        String decipheredText = encrypt(cipheredText, false);

        String unpadded = decipheredText.substring(0);
        while (unpadded.endsWith("~")){
            char[] arr = unpadded.toCharArray();
            arr[unpadded.length()-1] = ' ';
            unpadded = new String(arr);
            unpadded = unpadded.trim();
        }





        //==================================================================================================
        //Output to file
        //==================================================================================================
        try {
            File output = new File("Output.txt");
            output.createNewFile();

            PrintWriter printWriter = new PrintWriter(new FileOutputStream(output));


            printWriter.println("Original Text   : " + backupPlainText);
            printWriter.println("Padded Text     : " + plainText);
            printWriter.println();
            printWriter.println("Ciphered text   : " + cipheredText);
            printWriter.println();
            printWriter.println("Deciphered text : " + decipheredText);
            printWriter.println("Unpadded text   : " + unpadded);

            printWriter.flush();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    static String encrypt(String text, boolean encrypt){
        int numBlock = text.length()/8;        //number of blocks for plain text
        //==================================================================================================
        //Encryption
        //==================================================================================================
        String cipheredText = "";
        for (int blockIdx = 0; blockIdx < numBlock; blockIdx++) {               ///this loop is for each block
            System.out.println();
            if(encrypt){
                System.out.println("ENCRYPTING...");
            }
            else {
                System.out.println("DECRYPTING...");
            }
            String charBlock = text.substring(blockIdx*8, blockIdx*8+8);   //divided into 8 chars

            String binaryBlock = stringToBinaryString(charBlock);       //divided into 64 bits

            char []transposedBlock = transpose(binaryBlock.toCharArray(), PI);
            char []backupTransposedBlock = new char[64];
            for (int i = 0; i < 64; i++) {
                backupTransposedBlock[i] = transposedBlock[i];
            }

            //==================================================================================================
            //Starting iterations
            //==================================================================================================
            for (int itr = 0; itr < 16; itr++) {          //iterations
                char []ki;
                if(encrypt){
                    ki = itrKeys.get(itr).toCharArray(); //key for this iteration
                }
                else {
                    ki = itrKeys.get(NUM_ITR-1-itr).toCharArray(); //key for this iteration
                }


                char []itrBlock = new char[64];

                for (int i = 0; i < 32; i++) {
                    itrBlock[i] = transposedBlock[i+32];    //L(itr) = R(itr-1)
                }

                //==================================================================================================
                //Starting function for each round
                //==================================================================================================

                //expanding L(i-1) to 48 bits
                char[] expanded = transpose(itrBlock, E);

//                System.out.println("Before : " + new String(transposedBlock));
//                System.out.println("After  : " + new String(revTranspose(expanded, E, 32)) );

                //XORing expanded and ki
                char[] ekXOR = XOR(expanded, ki);


                ///sampling 32 bits
                char[]  sampled = transpose(ekXOR, PI_2);
//                System.out.println("Before : " + new String(ekXOR));
//                System.out.println("After  : " + new String(revTranspose(sampled, PI_2, 48)) );

                ///get transposed samples as function output
                char[] funcOut = transpose(sampled, P);

                //==================================================================================================
                //Output for each round
                //==================================================================================================


                char[] Li_1 = new char[32];
                for (int i = 0; i < 32; i++) {
                    Li_1[i] = transposedBlock[i];
                }

                char[] Ri = XOR(Li_1, funcOut);

                for (int i = 0; i < 32; i++) {
                    itrBlock[i+32] = Ri[i];
                }

                transposedBlock = itrBlock;     //to next iteration

                //System.out.println("Transposed Block after iteration " + itr + " : " + new String(transposedBlock));

            }

            //==================================================================================================
            //Iterations Complete
            //==================================================================================================

            //swapping left and right half
            char []swapBlock = new char[64];

            for (int i = 0; i < 32; i++) {
                swapBlock[i] = transposedBlock[i+32];
                swapBlock[i+32] = transposedBlock[i];
            }

            //final transpose
            char [] finalTransposedBlock = transpose(swapBlock, PI_1);


            long blockSentNumber = binaryStringToLong(new String(finalTransposedBlock));
            String blockSentString = binaryStringToString(new String(finalTransposedBlock));

            cipheredText = cipheredText.concat(blockSentString);


            System.out.println("CharBlock    : " + charBlock);
//            System.out.println("BinaryBlock  : " + binaryBlock);
//            System.out.println("TransBlock   : " + new String(backupTransposedBlock));
//            System.out.println("OutputBlock  : " + new String(finalTransposedBlock));
//            System.out.println("SentNumber   : " + blockSentNumber);
            System.out.println("ChangedBlock : " + blockSentString);

        }

        return cipheredText;
    }

    static char[] transpose(char[] source, int[] trans){
        int size = trans.length;
        char []result = new char[size];

        for (int i = 0; i < size; i++) {  //transposing
            result[i] = source[trans[i]-1];
        }

        return result;
    }


    static char[] leftRotate(char[] array, int num ){
        for (int i = 0; i < num; i++) {
            char temp = array[0];
            for (int j = 0; j < array.length-1; j++) {
                array[j] = array[j+1];
            }
            array[array.length-1] = temp;
        }

        return array;
    }

    static char[] XOR(char[] array1, char[] array2){
        int size = array1.length;
        
        if(array2.length!=size){
            System.out.println("SIZE NOT CORRECT!!!!");
            return null;
        }

        char[] array = new char[size];

        for (int i = 0; i < size; i++) {
            if(array1[i] == array2[i]){
                array[i] = '0';
            }
            else {
                array[i] = '1';
            }
        }


        return array;
    }

    static String charToBinaryString(char ch){
        char []binaryString = new char[8];

        int mask = 1;

        for(int i=7; i>=0; i--){
            if( ( (int)ch & mask ) > 0){
                binaryString[i] = '1';
            }
            else{
                binaryString[i] = '0';
            }
            ch = (char) (ch >> 1);
        }
        //binaryString[8] = 0;

        //System.out.println("Check 4 : " + binaryString.length);

        return new String(binaryString);

    }

    static String stringToBinaryString(String str){
        String binaryString = "";

       // System.out.println("Check 3 : " + str.length());

        for (int i = 0; i < str.length(); i++) {
            binaryString = binaryString+charToBinaryString(str.charAt(i));
        }

       // System.out.println("Check 2: " + binaryString.length());

        return binaryString;

    }

    static char binaryStringToChar(String string){
        char []binaryString = string.toCharArray();
        char ch = 0;

        for(int i=0; i<8 && i<string.length(); i++){
            ch =(char) (ch << 1);
            if(binaryString[i]=='1'){
                ch |= 1;
            }
            //printf("%s\n", charToBinaryString(ch));
        }

        //System.out.println("Check: " + ch);

        return ch;

    }

    static String binaryStringToString(String string){
        String fullString = "";

        //System.out.println(string.length()); ///wrong

        for (int i = 0; i+7 < string.length(); i+=8) {
            fullString = fullString + binaryStringToChar(string.substring(i, i+8));
        }


        return fullString;
    }

    static long binaryStringToLong(String string){
        char []binaryString = string.toCharArray();
        long val = 0;

        for(int i=0; i<64 && i<string.length(); i++){
            val =(long) (val << 1);
            if(binaryString[i]=='1'){
                val |= 1;
            }
            //printf("%s\n", charToBinaryString(ch));
        }

        return val;

    }





}
