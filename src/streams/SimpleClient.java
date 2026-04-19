package streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    static void main() {
        try (Socket client = new Socket("localhost", 5678)) {

            while (true) {
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                String request;
                String response;

                Scanner scanner = new Scanner(System.in);

                IO.print("Enter message: ");
                request = scanner.nextLine();
                if (request.equals("exit")) {
                    client.close();
                    break;
                }

                output.println(request);
                response = input.readLine();
                IO.print("Response: ");
                IO.println(response);
            }

        }catch (IOException e) {
            IO.println("Client Error " + e.getMessage());
        }
    }
}
