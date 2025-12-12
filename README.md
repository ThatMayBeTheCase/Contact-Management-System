# Contact-Management-System
### *Grupp 4 | Grundläggande Java-programmering | MU25*
<sub>*Muamer Brankovic | Josefin Vingeskog | Moody Amberdal | Timmy Wramborg*</sub>

---

🗂️ **Kontaktregister (Java Console App)**

Ett enkelt kontaktregister byggt i Java som körs i konsolen. Projektet är en gruppuppgift med syfte att träna på objektorienterad programmering (OOP).

---

## Funktionalitet

Programmet erbjuder klassiska CRUD-operationer:
- **Create** - lägga till nya kontakter.
- **Read** - söka efter och visa kontakter.
- **Update** - uppdatera kontaktinformation.
- **Delete** - ta bort kontakter.

Varje **kontakt** innehåller:
- Förnamn
- Efternamn
- Ålder
- Adress
  - Stad
  - Postkod
  - Gatunamn
  - Portnummer
- Ett eller flera telefonnummer

---

## Användarroller

Programmet har två typer av användare.
Administratörsbehörighet krävs för alla förändringar av registret.

### Gästanvändare
- Kan söka efter kontakter
- Kan lista kontakter
- Kan avsluta programmet

### Administratör
- Kan lägga till kontakter
- Kan uppdatera kontakter
- Kan ta bort kontakter
- Har samma rättigheter som gästanvändare


---

## Hur man kör projektet

1. Klona detta repository:
```bash
git clone <repo-url>
cd <projektmapp>
```

2. Öppna projektet i valfri Java-IDE (t.ex. IntelliJ)
3. Kör `Main`-klassen.
4. Följ instruktionerna i konsolen för att använda kontaktregistret.

---

### Trello
Planering och uppdelning av arbetet finns dokumenterat i Trello: [Klicka här](https://trello.com/invite/b/69381d7356c3d975086b0fb3/ATTIf36155da876ead34deb3d44bf0f951d8F6B9326A/java-gruppexamination-mu25)

### UML
En översikt av programmets struktur finns att se i UML-diagrammet:
`/UML-Diagram-Skiss/diagram.png`


--- 


