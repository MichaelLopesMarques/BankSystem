**SafeBank System**
-
Ein modulares Java-Backend-System zur Verwaltung von Bankkonten. Dieses Projekt demonstriert die Anwendung von Clean Architecture, dem Repository Pattern und Unit Testing mit JUnit 5.

**Features**
- 
- Kontoverwaltung: Erstellen von Konten mit eindeutigen IDs.

- Finanztransaktionen: Einzahlungen und Auszahlungen mit automatischer Validierung.

- Sicherheitsfunktionen: Sperren (lock) und Entsperren (unlock) von Konten.

- Transaktionshistorie: Lückenlose Aufzeichnung aller Kontobewegungen (Immutable History).

- Fehlerbehandlung: Eigene Exceptions für Geschäftsregeln (z.B. InsufficientBalanceException).

----------------------------------------------------
**Architektur & Design**
-
Das Projekt ist in drei logische Schichten unterteilt, um eine klare Trennung von Verantwortlichkeiten (Separation of Concerns) zu gewährleisten:

1. Domain (safebank.domain): Enthält die Kern-Logik und Entities (BankAccount, Transaction). Hier liegen die Geschäftsregeln.

2. Service (safebank.service): Koordiniert die Abläufe. Der BankAccountService nutzt das Repository, um Daten zu laden und zu speichern.

3. Repository (safebank.repository): Abstrahiert den Datenzugriff. Aktuell ist eine MemoryBankAccountRepository-Implementierung vorhanden, die Daten im RAM hält.

--------------------------------------------
**Installation & Nutzung**
-
**Voraussetzungen**
- Java 17 oder höher
- Maven oder Gradle (optional, falls du das Projekt als Build-Tool verwaltest)

**Beispiel-Code**

Hier ein kurzes Beispiel, wie der BankAccountService verwendet wird:

**Java**:

```java
// Repository und Service initialisieren
BankAccountRepository repository = new MemoryBankAccountRepository;
BankAccountService service = new BankAccountService(repository);

// Konto erstellen und nutzen
service.createAccount("DE-89-2026", "Admin Name");
service.deposit("DE-89-2026",500);
service.withdraw("DE-89-2026",200);

System.out.println("Kontostand: " + service.getBalance("DE-89-2026"));
```
----------------------------------------------------
**Testing**
-
Das Projekt legt großen Wert auf Qualität. Die Geschäftslogik ist durch umfassende Unit-Tests abgesichert.

**Getestete Szenarien:**

- Erfolgreiche Transaktionen.

- Verhinderung von Überziehungen.

- Sperrung von Transaktionen bei aktivem lockStatus.

- Korrekte Erzeugung der Transaktionshistorie.

Um die Tests auszuführen:

**Konsole**:
```java
mvn test
```

------------------------------------------------
**Zukünftige Erweiterungen**
-

-[ ] Anbindung einer SQL-Datenbank (JDBC/JPA Repository).

-[ ] Einführung einer REST-API mit Spring Boot.

-[ ] Unterstützung für verschiedene Währungen.