
# Folder Monitor

## Treść zadania
 App ma tworzyć foldery — [ _home | dev | test_ ]<br />
 Do folderu _home_ dostają się pliki JAR lub XML.

 Jeżeli jest to JAR i godzina jego utworzenia jest parzysta — program przenosi go do folderu _dev_.<br />
 Jeżeli jest to JAR i nieparzysta — to do folderu _test_.

Jeżeli jest to XML — to do folderu _dev_.

W folderze _home_ w pliku _json_ (_count.txt_) trzymana bieżąca liczba plików w każdym z folderów:<br />
{ "dev": 1,"test": 3 }.


## Zalecane biblioteki
- Apache Guava — Strings operations.
- File operations — system files operations.
- Json operations — JSON parser/generator.<br />
  - https://www.studytrails.com/2016/09/12/java-google-json-introduction/
  - https://studytrails.com/2016/09/12/java-google-json-parse-json-to-java/


## Struktura programu i działanie
Tworzę obiekt **AttributesHolder** zawierający atrybutu każdego obiektu plik, który zostanie wykryty w folderze wejściowym.<br />

W obiekcie **AttributesHolder** ustawiam atrybuty:<br />
- nazwa pliku
- ścieżka
- czy plik jest "parzysty" - na podstawie daty jego utworzenia

Wszystkie utworzone obiekty **AttributesHolder** dodaje do kolekcji (FIFO).<br />

Następnie obiekty zdejmowane są z kolejki (przez inny wątek applikacji) i obsługiwane.<br />
Obsługa poleca na przeniesieniu pliku zapisanego we właściwościach **AttributesHolder** do prawidłowego docelowego folderu.

