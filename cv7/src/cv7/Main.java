package cv7;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Loader l1 = new Loader();
        //System.out.println(l1.load("persons.txt").toString());
        l1.load("persons.txt");
        System.out.println(l1.toString());

        /*FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("xanadu.txt");
            out = new FileOutputStream("outagain.txt");
            int c;

            while((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        }

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("xanadu.txt");
            outputStream = new FileWriter("outagain2.txt");
            int c;

            while((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(outputStream != null) {
                outputStream.close();
            }
        }

        //BufferedReader
        BufferedReader inputStream2 = null;
        BufferedWriter outputStream2 = null;

        try {
            inputStream2 = new BufferedReader(new FileReader("xanadu.txt"));
            outputStream2 = new BufferedWriter(new FileWriter("outagain3.txt"));
            int c;

            while((c = inputStream2.read()) != -1) {
                outputStream2.write(c);
            }
        } finally {
            if(inputStream2 != null) {
                inputStream2.close();
            }
            if(outputStream2 != null) {
                outputStream2.close();
            }
        }

        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("xanadu.txt")));

            while(s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if(s != null) {
                s.close();
            }
        }*/
    }
}
