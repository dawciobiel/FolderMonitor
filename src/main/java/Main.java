import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private final static File dev = new File(System.getProperty("user.dir") + File.separator + "dev");
    private final static File home = new File(System.getProperty("user.dir") + File.separator + "home");
    private final static File test = new File(System.getProperty("user.dir") + File.separator + "test");
    /**
     * fixme
     * 3 razy używasz:
     * System.getProperty("user.dir")
     * więc też można to wy-ekstraktować.
     * <p>
     * Druga sprawa:
     * W tym miejscu łączysz kilka String'ów za pomocą operatora "+". Więc na pewno jest mało wydajnie. Bo JVM musi stworzyć w każdej linijce 5 instancje nowego obiektu String.
     * Co pomnożyć przez 3 linijki daje 15.
     * A więc lepiej StringBuilder lub StringBuffer trzeba użyć. Nie podpowiem tobie który w tym przypadku trzeba abyś sobie poczytał i się dowiedział. Bo juniorów czasami pytają o to na rozmowach kw.
     * <p>
     * Trzecia sprawa:
     * Być może tutaj można użyć bloku statycznego dla zbudowania odpowiednich String'ów dla tych trzech pól.
     * Być może warto - a być może się tak nie robi. Być może lepiej ten kod przenieść do specjalnej prywatnej metody i wywoływać tą metodę w metodzie main().
     * Musisz przeczytać.
     * <p>
     * Czwarta sprawa... tak sobie myślę...
     * - używasz tutaj pól statycznych
     * - Tworzysz tylko jedną instancję (czyli obiekt) na podstawie klasy Main
     * <p>
     * więc jak rozumiem chcesz mieć statyczne pola bo pewnie niże w klasie sobie "łatwo" się do nich odwołujesz, więc "wygodnie" tobie się pisze taki kod.
     * (p.s. nie rób tak - ale dlaczego to też poczytaj)
     */


    private final static Map<String, Integer> countMap = new HashMap<>();

    private final static Gson gson = new Gson();
    public static final String FILE_ALREADY_EXISTS = "File already exists!";
    public static final String FILE_WITH_AN_UNKNOWN_EXTENSION_ADDED_TO_HOME = "file with an unknown extension added to HOME";

    private static PrintWriter count;

    public static void main(String[] args) throws InterruptedException, IOException { // fixme A gdzie potem przechwytujesz te wyjątki? nigdzie? I tak leci stackTrace na ekran konsoli użytkownika
        // fixme Wy-ekstraktuj te 3 linijki do prywatnej metody
        home.mkdirs();
        dev.mkdirs();
        test.mkdirs();

        countMap.put("dev", dev.listFiles().length); // fixme Nie wczytywłem się za bardzo co ten program robi, ale zastanawiam się po co mapa a nie kolekcja.
        countMap.put("test", test.listFiles().length);

        count = new PrintWriter(home.getPath() + File.separator + "count.txt"); // O! A tu znowu używasz jakiegoś dzikiego łączenia Stringów na chama operatorem "+". Czyli już 4-ty raz. Więc może faktycznie warto napiasc metodę do której się podaje parametry i... ta metoda zwraca gotowy twór?
        printToCount();

        WatchService watchService = FileSystems.getDefault().newWatchService();

        Paths.get(home.getPath()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey watchKey; // fixme tutaj IDE (InteliJ IDEA) podpowiada co można z tym zrobić

        while ((watchKey = watchService.take()) != null) {
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                int creationHour =
                        LocalDateTime.ofInstant(
                                Files.readAttributes(
                                                Paths.get(home.getPath() + File.separator + event.context().toString()), BasicFileAttributes.class)
                                        .creationTime().toInstant(), ZoneId.systemDefault()).getHour();

                switch (event.context().toString().substring(event.context().toString().lastIndexOf(".") + 1)) { // fixme Wy-ekstraktuj zawartość tego całego wyliczania, szukania znaku "." do prywatnej metody którą tutaj użyjesz. IDE bardzo ładnie to robi za ciebie (patrz: Refactor -> Extract method, CTRL+ALT+M pod InteliJ IDEA)
                    case "jar": { // fixme Zamiast "jar", "xml" - to powinieneś tutaj używać stałych statycznych, pisanych wielką literą, tj.: JAR, XML lub FILE_EXT_JAR, FILE_EXT_XML
                        if (creationHour % 2 == 0) {
                            try {
                                moveFile(event, dev);
                            } catch (IOException ex) {
                                System.out.println(FILE_ALREADY_EXISTS);
                            }
                        } else {
                            try {
                                moveFile(event, test);
                            } catch (IOException ex) {
                                System.out.println(FILE_ALREADY_EXISTS);
                            }
                        }
                        break;
                    }
                    case "xml": {
                        try {
                            moveFile(event, dev);
                        } catch (IOException ex) {
                            System.out.println(FILE_ALREADY_EXISTS);
                        }
                        break;
                    }
                    default: {
                        System.out.println(FILE_WITH_AN_UNKNOWN_EXTENSION_ADDED_TO_HOME);
                        break;
                    }
                }
            }
            watchKey.reset();
        }

    }

    private static void printToCount() {
        count.println(gson.toJson(countMap));
        count.flush();
    }

    private static void moveFile(WatchEvent event, File folder) throws IOException {
        Files.move(Paths.get(home.getPath() + File.separator + event.context().toString()), Paths.get(folder.getPath() + File.separator + event.context().toString())); // fixme Ooo!! Piąty i szósty raz znow łączenie String'ów
        countMap.put(folder.getName(), folder.listFiles().length);
        printToCount();
    }
}

// fixme Java to jest język obiektowy. Więc nie można tak rzeźbić, że na sztywno się odwołujesz do pola statycznego jak tylko chcesz znać jego wartość lub do niego coś zapisać. Jeżeli chcesz mieć do niego dostęp wewnątrz metody to przekazuj do tej metody jako parametr.

/* fixme
   Jeżeli używasz w jednej klasie wiele razy:

        System.out.println()

   to zainteresuj się "statycznym importem"

   https://en.wikipedia.org/wiki/Static_import
 */

// fixme ____________________________________________________________________________________________________________
// fixme Teraz jestem trochę zajęty, ale jak masz czas to możemy obgadać jak napisać ten program na głosowym czacie.
// fixme    Pozdro dawciobiel
// fixme ____________________________________________________________________________________________________________