angular.module('gFooter').service('gFooterData', function(){
    var data = {};

    data.footer = {
        "footerLinks":[
            {"name":"Over ING","href":""},
            {"name":"Veilig bankieren","href":""},
            {"name":"Privacy en cookies","href":""},
            {"name":"Disclaimer","href":""},
            {"name":"Toegangkelijkheid","href":""}
        ],
        "socialMediaTitle":"Volg ons:",
        "socialMediaLinks":[
            {
                "medium":"facebook",
                "rawUrl":"https://www.facebook.com/INGnl",
                "linkTitle":"ING op Facebook",
                "target":"Nieuw venster",
                "url":"https://www.facebook.com/INGnl"
            },
            {
                "medium":"twitter",
                "rawUrl":"https://www.facebook.com/INGnl",
                "linkTitle":"ING op Twitter",
                "target":"Nieuw venster",
                "url":"https://www.facebook.com/INGnl"
            },
            {
                "medium":"linkedin",
                "rawUrl":"https://www.facebook.com/INGnl",
                "linkTitle":"ING op LinkedIn",
                "target":"Nieuw venster",
                "url":"https://www.facebook.com/INGnl"
            },
            {
                "medium":"youtube",
                "rawUrl":"https://www.youtube.com/INGnl",
                "linkTitle":"ING op youtube",
                "target":"Nieuw venster",
                "url":"https://www.youtube.com/INGnl"
            },
            {
                "medium":"googleplus",
                "rawUrl":"https://www.facebook.com/INGnl",
                "linkTitle":"ING op Google+",
                "target":"Nieuw venster",
                "url":"https://www.facebook.com/INGnl"
            },
            {
                "medium":"instagram",
                "rawUrl":"http://instagram.com/ingnederland",
                "linkTitle":"ING op Instagram",
                "target":"Nieuw venster",
                "url":"http://instagram.com/ingnederland"
            }
        ]
    };
    return data;
});