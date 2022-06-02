
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
Tworzę obiekt opakowujący (DTO), który opakuje mi obiekt "plik"
Dodam do DTO aktualną godzinę.
Następnie inny moduł będzie odczytywał właściwość z DTO i na jego podstawie podejmuje kroki — np. ustawia inną właściwość na DTO.

Na koniec inny moduł na podstawie wpisu w tej ostatniej właściwości decyduje gdzie umieścić DTO.
