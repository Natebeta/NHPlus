# NHPlus

##Informationen zur Lernsituation
Du bist Mitarbeiter der HiTec GmbH, die seit über 15 Jahren IT-Dienstleister und seit einigen Jahren ISO/IEC 27001 zertifiziert ist. Die HiTec GmbH ist ein mittelgroßes IT-Systemhaus und ist auf dem IT-Markt mit folgenden Dienstleistungen und Produkten vetreten: 

Entwicklung: Erstellung eigener Softwareprodukte

Consulting: Anwenderberatung und Schulungen zu neuen IT- und Kommunikationstechnologien , Applikationen und IT-Sicherheit

IT-Systembereich: Lieferung und Verkauf einzelner IT-Komponenten bis zur Planung und Installation komplexer Netzwerke und Dienste

Support und Wartung: Betreuung von einfachen und vernetzten IT-Systemen (Hard- und Software)

Für jede Dienstleistung gibt es Abteilungen mit spezialisierten Mitarbeitern. Jede Abteilung hat einen Abteilungs- bzw. Projektleiter, der wiederum eng mit den anderen Abteilungsleitern zusammenarbeitet.

 

##Projektumfeld und Projektdefinition

Du arbeitest als Softwareentwickler in der Entwicklungsabteilung. Aktuell bist du dem Team zugeordnet, das das Projekt "NHPlus" betreut. Dessen Auftraggeber - das Betreuungs- und Pflegeheim "Curanum Schwachhausen" - ist ein Pflegeheim im Bremer Stadteil Schwachhausen - bietet seinen in eigenen Zimmern untergebrachten Bewohnern umfangreiche Therapie- und Serviceleistungen an, damit diese so lange wie möglich selbstbestimmt und unabhängig im Pflegeheim wohnen können. Curanum Schwachhausen hat bei der HiTec GmbH eine Individualsoftware zur Verwaltung der Patienten und den an ihnen durchgeführten Behandlungen in Auftrag gegeben. Aktuell werden die Behandlungen direkt nach ihrer Durchführung durch die entsprechende Pflegekraft handschriftlich auf einem Vordruck erfasst und in einem Monatsordner abgelegt. Diese Vorgehensweise führt dazu, dass Auswertungen wie z.B. welche Behandlungen ein Patient erhalten oder welche Pflegkraft eine bestimmte Behandlung durchgeführt hat, einen hohen Arbeitsaufwand nach sich ziehen. Durch NHPlus soll die Verwaltung der Patienten und ihrer Behandlungen elektronisch abgebildet und auf diese Weise vereinfacht werden.

Bei den bisher stattgefundenen Meetings mit dem Kunden konnten folgende Anforderungen an NHPlus identifiziert werden:

- alle Patienten sollen mit ihrem vollen Namen, Geburtstag, Pflegegrad, dem Raum, in dem sie im Heim untergebracht sind, sowie ihrem Vermögensstand erfasst werden.

- Die Pflegekräfte werden mit ihrem vollen Namen und ihrer Telefonnumer erfasst, um sie auf Station schnell erreichen zu können.

- jede Pflegekraft erfasst eine Behandlung elektronisch, indem sie den Patienten, das Datum, den Beginn, das Ende, die Behandlungsart sowie einen längeren Text zur Behandlung erfasst.

- Die Software muss den Anforderungen des Datenschutzes entsprechen. 

- Die Software ist zunächst als Desktopanwendung zu entwickeln, da die Pflegekräfte ihre Behandlungen an einem stationären Rechner in ihrem Aufenthaltsraum erfassen sollen.

 

Da in der Entwicklungsabteilung der HiTech GmbH agile Vorgehensweisen vorgeschrieben sind, wurde für NHPlus Scum als Vorgehensweise gewählt.

 

##Stand des Projektes

In den bisherigen Sprints wurden die Module zur Erfassung der Patienten- und Behandlungsdaten fertiggestellt. Es fehlt das Modul zur Erfassung der Pflegekräfte. Deswegen kann bisher ebenfalls nicht erfasst werden, welche Pflegekraft eine bestimmte Behandlung durchgeführt hat. In der letzten Sprint Review sind von der Curanum Schwachhausen Zweifel angebracht worden, dass die bisher entwickelte Software den Anforderungen des Datenschutzes genügt.

## Technische Hinweise

Wird das Open JDK verwendet, werden JavaFX-Abhängigkeiten nicht importiert. Die Lösung besteht in der Installation der neuesten JDK-Version der Firma Oracle.

## Technische Hinweise zur Datenbank

- Benutzername: SA
- Passwort: SA
- Bitte nicht in die Datenbank schauen, während die Applikation läuft. Das sorgt leider für einen Lock, der erst wieder verschwindet, wenn IntelliJ neugestartet wird!

## Allgemeine Informationen
- Admin User:
	- Benutzername: A
	- Passwort: 123
- Normal User:
	- Benutzername: B
	- Passwort: 123


| Anforderungen der Datenwaschung | ✔️ |
|--------------------------------------------------------------------------|---|
| Nach Ablauf der Verwahrungsfrist automatische Löschung der Behandlungen.* | ✔️ |
| Sperroption für Pfleger, die Daten werden in der View nicht angezeigt.   | ✔️ |
| Pfleger können keine Daten endgültig löschen.                            | ✔️ |


*Information zur Datenwaschung:
Jeden Tag um 11:47 werden alle Behandlungen gelöscht, die älter als 10 Jahre sind.
Wenn man in Line 10*** die Zahlen anpasst wird der Zeitpunkt der Löschung verändert. Erste Zahl die Stunde und zweite Zahl die Minuten. Hierbei gehen wir von der Zeit des PCs aus.
 **[src](https://github.com/Natebeta/NHPlus/tree/develop/src)/[main](https://github.com/Natebeta/NHPlus/tree/develop/src/main)/[java](https://github.com/Natebeta/NHPlus/tree/develop/src/main/java)/[jobrunner](https://github.com/Natebeta/NHPlus/tree/develop/src/main/java/jobrunner)/**QuartzController.java**  /  startTreatmentDeleter

Userstory: 
https://1drv.ms/w/s!AvaVb7JDv20c3xTZH6a3siwFCuZM?e=Bqfqjs

| Anforderungen des Pflegemoduls| ✔️ |
|--------------------------------------------------------------------------------------------------------------------|---|
| Die persönlichen Daten einer Pflegekraft besteht aus der PflegekraftID, dem Nachnamen, Vornamen und Telefonnummer.                                                                             | ✔️ |
|   Alle Feldbelegungen sind verpflichtend.                                                                                                                 | ✔️ |
|   Alle Pflegekräfte werden mit ihren vollständigen Daten in einer tabellarischen Übersicht dargestellt.                                                                                                                 | ✔️  |
|    Jede Information, außer die ID, zu einer Pflegekraft kann aus der Übersicht heraus geändert werden.                                                                                                                | ✔️  |
|   Es können neue Pflegekräfte hinzugefügt werden.                                                                                                                 |  ✔️ |
|         Eine ausgewählte Pflegekraft kann gesperrt werden.                                                                                                           | ✔️  |
|    Jede getätigte Änderung - Anlegen einer Pflegekraft, Ändern der Stammdaten, und das Löschen - wird in der Datenbank abgebildet.                                                                                                                | ✔️  |
|    Beim Anlegen einer neuen Behandlung soll die entsprechende Pflegekraft mit Hilfe einer Combobox ausgewählt werden können.                                                                                                                |  ✔️ |
|  Die Anzeige einer einzelnen Behandlung soll um die Daten des Pflegers/in ergänzt werden, der/die die Behandlung durchgeführt hat.                                                                                                                  | ✔️  |
|  Gibt es bei einer gesperrten Pflegekraft nur noch Behandlungen, die mehr als 10 Jahre zurückliegen, wird der Eintrag des Pflegers gelöscht.                                                                                                                 | ✔️  |


Userstory: 
https://1drv.ms/w/s!AvaVb7JDv20c3xNJwfCe_F8eSnQe?e=LeDLpS

