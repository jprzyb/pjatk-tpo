# Zadanie: kanały plikowe

### This program reqiures jdk1.8.0_321

Katalog {user.home}/TPO1dir  zawiera pliki tekstowe umieszczone w tym katalogu i jego różnych podkatalogach. Kodowanie plików to Cp1250.
Przeglądając rekursywnie drzewo katalogowe, zaczynające się od {user.home}/TPO1dir,  wczytywać te pliki i dopisywać ich zawartości do pliku o nazwie TPO1res.txt, znadującym się w katalogu projektu. Kodowanie pliku TPO1res.txt winno być UTF-8, a po każdym uruchomieniu programu plik ten powinien zawierać tylko aktualnie przeczytane dane z  plików katalogu/podkatalogow.

Poniższy gotowy fragment winien wykonać całą robotę:
      public class Main {
        public static void main(String[] args) {
          String dirName = System.getProperty("user.home")+"/TPO1dir";
          String resultFileName = "TPO1res.txt";
          Futil.processDir(dirName, resultFileName);
        }
      }
Uwagi:
pliku Main.java nie wolno w żaden sposób modyfikować,
trzeba dostarczyć definicji klasy Futil,
oczywiście, nazwa katalogu i pliku oraz ich położenie są obowiązkowe,
należy zastosować FileVisitor do przeglądania katalogu oraz kanały plikowe (klasa FileChannel) do odczytu/zapisu plików (bez tego rozwiązanie nie uzyska punktów).
w wynikach testów mogą być przedstawione dodatkowe zalecenia co do sposobu wykonania zadania (o ile rozwiązanie nie będzie jeszcze ich uwzględniać),.

# Zadanie: klienci usług sieciowych

### This program reqiures jdk1.8.0_321

Napisać aplikację, udostępniającą GUI, w którym po podanu miasta i nazwy kraju pokazywane są:
Informacje o aktualnej pogodzie w tym mieście.
Informacje o kursie wymiany walutu kraju wobec podanej przez uzytkownika waluty.
Informacje o kursie NBP złotego wobec tej waluty podanego kraju.
Strona wiki z opisem miasta.
W p. 1 użyć serwisu api.openweathermap.org, w p. 2 - serwisu open.er-api.com (dok: https://www.exchangerate-api.com/docs/free), w p. 3 - informacji ze stron NBP: https://nbp.pl/statystyka-i-sprawozdawczosc/kursy/tabela-a/ i analogicznie dla tabel B i C.
W p. 4 użyć klasy WebEngine z JavaFX dla wbudowania przeglądarki w aplikację Swingową.

Program winien zawierać klasę Service z konstruktorem Service(String kraj) i metodami::
String getWeather(String miasto) - zwraca informację o pogodzie w podanym mieście danego kraju w formacie JSON (to ma być pełna informacja uzyskana z serwisu openweather - po prostu tekst w formacie JSON),
Double getRateFor(String kod_waluty) - zwraca kurs waluty danego kraju wobec waluty podanej jako argument,
Double getNBPRate() - zwraca kurs złotego wobec waluty danego kraju
Następujące przykładowa klasa  pokazuje możliwe użycie tych metod:
public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
  }
}

Uwaga 1: zdefiniowanie pokazanych metod w sposób niezalezny od GUI jest obowiązkowe.
Uwaga 2:  W katalogu projektu (np. w podkatalogu lib) nalezy umiescic wykorzystywane JARy (w przeciwnym razie program nie przejdzie kompilacji) i skonfigurowac Build Path tak, by wskazania na te JARy byly w Build Path zawarte.
