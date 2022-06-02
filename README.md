
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
I wątek działający w nieskończonej pętli monitoruje folder wejściowy. Jeżeli znajdzie nowy plik 
to tworzy obiekt **AttributesHolder** zawierający atrybutu każdego wykrytego plik.<br />

W obiekcie **AttributesHolder** ustawia atrybuty:<br />
- nazwa pliku
- ścieżka
- czy plik jest "parzysty" - na podstawie daty jego utworzenia

Wszystkie utworzone obiekty **AttributesHolder** dodaje do kolekcji (FIFO).<br />

Następnie obiekty zdejmowane są z kolejki (wątek II aplikacji) i obsługiwane.<br />
Obsługa to przeniesienie pliku zapisanego we właściwościach **AttributesHolder** do prawidłowego docelowego folderu.

# Licencja
### The MIT License (MIT)

Copyright © `<2022>` `<Dawid Bielecki - dawciobiel>`

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the “Software”), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

