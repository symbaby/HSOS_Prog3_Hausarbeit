Autoren: Johannes Belaschow, Berkan Yildiz

Die Grundlage unseres Codes bildet ein Tutorial von Youtube.
Quelle: https://www.youtube.com/watch?v=ARpn-1FPNE4&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118
Weitere Quellen finden Sie im Projektbericht.

Wir haben für unsere Ordner Struktur haben wir die Datein wie folgt gegliedert:

    ~Activities: Hier sind die Übersicht Activities gesammelt
    ~Add: Alle Bearbeitungs und Hinzufügen Activities sind hier gesammelt
    ~Adapter: Hier liegen alle Adapter. Diese verwalten die RecyclerViews

    ~Entitaete: Alle Entitäten unserer Datenbank liegen hier vor
    ~Beziehungen: Hält die Datein die unsere Fremdschlüssel-Beziehungen garantieren
    ~Dao: Alle zu den Entitäten gehörigen DAOs liegen hier vor
    ~Datenbank: Hält die Datenbank für unsere Entitäten und DAOs
    ~Repository: Hält das Repository für unsere Datenbank
    ~Viewmodel: Hält alle nötigen ViewModel Klassen, die alle Repository Methoden für ihre zuständigen Entitäten implementieren
    ~Converter: Hält die Converter Klasse die unsere Bitmaps zu Byte Array konvertieren kann

