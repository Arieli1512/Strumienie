package streams;

import java.io.*;

public class Streams
{
    static void main() throws IOException {
        try(FileInputStream fis = new FileInputStream("data.txt")){
            int data;
            while((data = fis.read()) != -1){
                IO.print((char)data);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IO.println();

        InputStreamReader isr = new InputStreamReader(new FileInputStream("data.txt"));
        int data;
        while ((data = isr.read()) != -1)
        {
            IO.print((char)data);
        }
        IO.println();
        isr.close();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data.txt")));
        while ((line = br.readLine()) != null)
        {
            IO.println(line);
        }
        IO.println();
        br.close();

        PrintWriter pw = new PrintWriter("out.txt");
        pw.println("Hello World");
        pw.close();
    }
}

