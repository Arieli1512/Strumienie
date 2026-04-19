package SelectorServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorServer
{
    static void main()
    {
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open())
        {
            serverChannel.bind(new InetSocketAddress(5678));
            serverChannel.configureBlocking(false); //!! ważne nie blokuje jesli nie ma odpowiedzi na operacji wejscia wyjscia

            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);   // selektor bedzie akceptowac klientow

            while (true)
            {
                selector.select();      //odczytuje z selektora
                Set<SelectionKey> selectedKeys = selector.selectedKeys(); //zwraca zestaw kluczy
                Iterator<SelectionKey> iterator = selectedKeys.iterator(); // iterator pozwlajacy przechodzic kolekje (jak for ale mozna usunac w trakcie )

                while (iterator.hasNext()) // dopoki mamy zdarzenia
                {
                    SelectionKey key = iterator.next();  // pobieramy nastepne zdarzenie
                    iterator.remove();  //usuwamy z zestawu zdarzenie

                    if(key.isAcceptable())
                    {
                        SocketChannel clientChannel = serverChannel.accept(); // akceptujemy klienta
                        System.out.println("Client connected: " + clientChannel.getRemoteAddress()); //wyswietlamy adres klienta
                        clientChannel.configureBlocking(false); // nie blokuje jesli nie ma odpowiedzi na operacji wejscia wyjscia
                        clientChannel.register(selector, SelectionKey.OP_READ); // obserwujemy kanal i zwracamy uwage na to czy zostalo cos wyslane
                    } else if (key.isReadable())
                    {
                        echoData(key); // wyswietlamy dane
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    private static void echoData(SelectionKey key) throws IOException
    {
        SocketChannel clientChannel = (SocketChannel) key.channel(); // pobiermay chanel z klucza
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int bytesRead = clientChannel.read(buffer); // czytamy dane z kanalu
        if(bytesRead > 0)
        {
            buffer.flip(); // brzelaczamy  w tryb odczytu
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data); // zapisujemy dane do tablicy
            String message = "Echo: " + new String(data); // tworzymy wiadomosc
            clientChannel.write(ByteBuffer.wrap(message.getBytes())); // wysylamy wiadomosc
        } else if (bytesRead == -1)
        {
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            key.cancel(); //usuwamy klucz
            clientChannel.close(); //zamyka kanal
        }
    }
}