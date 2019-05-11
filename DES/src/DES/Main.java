package DES;

import java.util.Scanner;

public class Main {

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


    public static void main(String []args){
        System.out.println("Give key and plain text: ");

        Scanner scanner = new Scanner(System.in);

        String key = scanner.nextLine();
        String plainText = scanner.nextLine();

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

        int numBlock = plainText.length()/8;

        for (int blockIdx = 0; blockIdx < numBlock; blockIdx++) {               ///this loop is for each block
            String charBlock = plainText.substring(blockIdx*8, blockIdx*8+8);

            String binaryBlock = stringToBinaryString(charBlock);






            System.out.println(charBlock);
            System.out.println(binaryBlock);
            System.out.println(binaryStringToString(binaryBlock));
        }


//        System.out.println(charToBinaryString('c'));
//        System.out.println(binaryStringToLong(Long.toBinaryString(12345678)));



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

        for (int i = 0; i < string.length(); i+=8) {
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
