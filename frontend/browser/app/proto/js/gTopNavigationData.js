angular.module('gTopNavigation').service('gTopNavigationData', function(){
    var data = {};

    data.header = {"loginLink": {"name": "Inloggen", "href": "https://mijn.ing.nl/internetbankieren/SesamLoginServlet"}, "loginMoreInfoLink": {"name": "Hulp bij inloggen", "href": "https://www.ing.nl/particulier/internetbankieren/index.aspx"}, "headerLinks": [
        {"name": "Rentepuntenwinkel", "href": "#rentepunten"},
        {"name": "Service", "href": "https://www.ing.nl/particulier/klantenservice/index.aspx"},
        {"name": "Contact", "href": "https://www.ing.nl/particulier/klantenservice/contact/index.aspx"}
    ], "showSearchbox": true, "searchboxText": "Zoeken in Particulier"};
    data.segments = [
        {"href": "/particulier/index.html", "name": "Particulier"},
        {"href": "#personal", "name": "Personal Banking"},
        {"href": "#priba", "name": "Private Banking"},
        {"href": "#zak", "name": "Zakelijk"}
    ];
    data.nav = [
        {"href": "/app/sandbox.html", "name": "Home"},
        {"name": "Producten",
            "list": [
                {"name": "Betalen",
                    "introItem": {
                        "introTitle": "Uw betaalpas limiet",
                        "intro": "Hier is een stukje tekst",
                        "introLink": {"name": "Meer Informatie", "href": "betalen.html"},
                        "listTitle1": "Mogelijkheden",
                        "listItems1": [
                            {"name": "Betaalrekening", "href": "http://mijn.ing.nl/betaalrekening/"},
                            {"name": "(Unieke) Betaalpas", "href": ""},
                            {"name": "Creditcard", "href": "http://mijn.ing.nl/creditcard/"},
                            {"name": "Kinder- of jongerenrekening", "href": ""},
                            {"name": "Studentenrekening", "href": ""},
                            {"name": "Rood staan", "href": ""},
                            {"name": "Actuele tarieven", "href": ""}
                        ], "listTitle2": "Zo werkt het", "listItems2": [
                            {"name": "Betalen in binnen- en buitenland", "href": ""},
                            {"name": "Pas gestolen/verloren", "href": ""},
                            {"name": "Pincode", "href": ""},
                            {"name": "Klant worden bij ING", "href": ""}
                        ], "listTitle3": "Zo werkt het", "listItems3": [
                            {"name": "Betalen in binnen- en buitenland", "href": ""},
                            {"name": "Pas gestolen/verloren", "href": ""},
                            {"name": "Pincode", "href": ""},
                            {"name": "Klant worden bij ING", "href": ""}],
                        "moreLink": {"name": "Alles over Betalen", "href": "betalen.html"}}},
                {"name": "Sparen", "introItem": {"introTitle": "Nieuw: Spaarplanner", "intro": "Kies zelf uw daglimiet:\n<br  />\nStandaard of juist persoonlijk", "introLink": {"name": "Meer Informatie", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Overzicht spaarrekeningen", "href": "https://mijnzakelijk.ing.nl/spaarrekeningen/"},
                    {"name": "Keuzehulp sparen", "href": ""},
                    {"name": "Direct inleggen", "href": ""},
                    {"name": "Actuele spaarrentes", "href": ""}
                ], "listTitle2": "Zo werkt het", "listItems2": [
                    {"name": "Doelsparen", "href": ""},
                    {"name": "Rentepunten", "href": ""},
                    {"name": "SpaarrenteWekker", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": {"name": "Alles over sparen", "href": ""}}},
                {"name": "Beleggen", "introItem": {"introTitle": "Onze rendementen", "introImage": null, "intro": null, "introLink": {"name": "Bekijk onze rendementen", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Zelf op de beurs", "href": ""},
                    {"name": "Zelf Vermogensopbouw", "href": ""},
                    {"name": "Beleggen met Advies", "href": ""},
                    {"name": "Beleggen met Beheer", "href": ""},
                    {"name": "Beleggingsrekening openen", "href": ""}
                ], "listTitle2": "Voor meer inzicht", "listItems2": [
                    {"name": "Keuzehulp beleggen", "href": ""},
                    {"name": "sparen en beleggen", "href": ""},
                    {"name": "Online seminars", "href": ""},
                    {"name": "Fondsinformatie", "href": ""},
                    {"name": "Actuele tarieven", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": {"name": "alles over Beleggen", "href": ""}}},
                {"name": "Hypotheken", "introItem": {"introTitle": "Online seminar", "introImage": null, "intro": null, "introLink": {"name": "Meld u gratis aan", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Maximale hypotheek berekenen", "href": ""},
                    {"name": "Kunt u het huis betalen?", "href": ""},
                    {"name": "Hypotheekvormen", "href": ""},
                    {"name": "Actuele hypotheekrente", "href": ""}
                ], "listTitle2": "Zo werkt het", "listItems2": [
                    {"name": "Aflossen", "href": ""},
                    {"name": "Uw rentevaste periode", "href": ""},
                    {"name": "Bouwdepot", "href": ""},
                    {"name": "Hypotheek advies", "href": ""},
                    {"name": "een huis kopen", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": null}},
                {"name": "Verzekeren", "introItem": {"introTitle": "Goed verzekerd", "introImage": null, "intro": null, "introLink": {"name": "Meer informatie", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Reizen", "href": ""},
                    {"name": "Wonen", "href": ""},
                    {"name": "Auto", "href": ""},
                    {"name": "Rechtsbijstand", "href": ""},
                    {"name": "Aansprakelijkheids", "href": ""},
                    {"name": "Studenten", "href": ""},
                    {"name": "Overlijdensrisico", "href": ""}
                ], "listTitle2": "Direct doen", "listItems2": [
                    {"name": "Scan: bent u goed verzekerd?", "href": ""},
                    {"name": "Schade melden", "href": ""},
                    {"name": "Verzekering opzeggen", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": {"name": "Alles over Verzekeren", "href": ""}}},
                {"name": "Lenen", "introItem": {"introTitle": "Uw verbouwing betalen?", "introImage": null, "intro": null, "introLink": {"name": "Doe de verbouwwijzer", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Rood staan", "href": ""},
                    {"name": "Persoonlijke lening", "href": ""},
                    {"name": "Doorlopend krediet", "href": ""},
                    {"name": "Creditcards", "href": ""},
                    {"name": "Keuzehulp lenen", "href": ""},
                    {"name": "Actuele tarieven", "href": ""}
                ], "listTitle2": "Zo werkt het", "listItems2": [
                    {"name": "Aanvragen", "href": ""},
                    {"name": "Oversluiten", "href": ""},
                    {"name": "Uw lening gebruiken", "href": ""},
                    {"name": "Wijzigen", "href": ""},
                    {"name": "Opzeggen", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": null}},
                {"name": "Pensioenen", "introItem": {"introTitle": "Online seminar", "introImage": null, "intro": null, "introLink": {"name": "Meld u gratis aan", "href": ""}, "listTitle1": "Mogelijkheden", "listItems1": [
                    {"name": "Pensioen opbouwen", "href": ""},
                    {"name": "Pensioen laten uitkeren", "href": ""},
                    {"name": "Banksparen voor pensioen", "href": ""},
                    {"name": "Ontslagvergoeding gebruiken", "href": ""},
                    {"name": "Lijfrente als aanvulling", "href": ""}
                ], "listTitle2": "Voor meer inzicht", "listItems2": [
                    {"name": "Waaruit bestaat pensioen", "href": ""},
                    {"name": "Uw situatie nu en pensioen", "href": ""},
                    {"name": "Adviesgesprek", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": null}},
                {"name": "Internet & Mobiel", "introItem": {"introTitle": "Tik 'm aan", "introImage": null, "intro": null, "introLink": {"name": "Meer informatie", "href": ""}, "listTitle1": "Internetbankieren", "listItems1": [
                    {"name": "Aanvragen Mijn ING", "href": ""},
                    {"name": "Hulp bij inloggen", "href": "https://www.ing.nl/particulier/internetbankieren/index.aspx"},
                    {"name": "Zo werkt mijn ING", "href": ""},
                    {"name": "Betalen met iDeal", "href": ""},
                    {"name": "Veilig internetbankieren", "href": ""}
                ], "listTitle2": "Mobiel bankieren", "listItems2": [
                    {"name": "Wat kunt u met de app?", "href": ""},
                    {"name": "Zo werkt de app", "href": ""},
                    {"name": "Veilig mobiel bankieren", "href": ""},
                    {"name": "Downloaden en activeren", "href": ""}
                ], "listTitle3": null, "listItems3": null, "moreLink": null}}
            ]},
        {"name": "Financieel Fit", "list": [
            {"name": "Beleggen", "introItem": {"introTitle": null, "introImage": null, "intro": null, "introLink": null, "listTitle1": "Zakelijke rekeningen", "listItems1": [
                {"name": "Starterspakket", "href": "/zakelijk/betalen/zakelijke-rekening-openen/starterspakket/index.html"},
                {"name": "Ondernemerspakket", "href": ""},
                {"name": "Zakelijke rekening openen", "href": "/zakelijk/betalen/zakelijke-rekening-openen/index.html"},
                {"name": "Welke betaalrekening past bij mij?", "href": "/zakelijk/betalen/zakelijke-rekening-openen/starterspakket/index.html#tab_voor-wie"},
                {"name": "Overstapservice", "href": ""},
                {"name": "Tarieven", "href": ""},
                {"name": "Veelgestelde vragen", "href": "/zakelijk/betalen/zakelijke-rekening-openen/starterspakket/index.html#tab_veelgestelde-vragen"}
            ], "listTitle2": "Geld ontvangen", "listItems2": [
                {"name": "iDEAL", "href": ""},
                {"name": "Pinpakket compleet", "href": "/zakelijk/betalen/geld-ontvangen/pin-pakket-compleet/index.html"},
                {"name": "Europees Incasso", "href": ""},
                {"name": "Tarieven", "href": ""},
                {"name": "Veelgestelde vragen", "href": ""}
            ], "listTitle3": "Betalingen doen", "listItems3": [
                {"name": "SEPA Europees betalen", "href": ""},
                {"name": "Betaalpas", "href": ""},
                {"name": "Zakelijke credit cards", "href": ""},
                {"name": "Tarieven", "href": ""},
                {"name": "Veelgestelde vragen", "href": ""}
            ], "moreLink": {"name": "Alles over Betalen", "href": ""}}}
        ], "introItem": {"introTitle": "Financieel Fit", "introImage": null, "intro": null, "introLink": null, "listTitle1": null, "listItems1": null, "listTitle2": null, "listItems2": null, "listTitle3": null, "listItems3": null, "moreLink": null}},
        {"href": "/GAWEG.HTML", "name": "Hoeveel ben je waard"}
    ];
    data.segmentActive = 'Particulier';

    return data;
});
