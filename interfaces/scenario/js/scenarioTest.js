function premierScenarioPartUn() {
	var http = new XMLHttpRequest();
	var url = "/java/Scenario";
	var params = "commande=scenario&num=1&part=1";
	http.open("POST", url, true);

	//Sendtheproperheaderinformationalongwiththerequest
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function(){//Callafunctionwhenthestatechanges.
		if(http.readyState == 4 && http.status == 200){
			console.log(http.responseText);
			premierScenarioPartDeux();
		}
	}
	http.send(params);
}

function premierScenarioPartDeux() {
	var http = new XMLHttpRequest();
	var url = "/java/Scenario";
	var params = "commande=scenario&num=1&part=2";
	http.open("POST", url, true);

	//Sendtheproperheaderinformationalongwiththerequest
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function(){//Callafunctionwhenthestatechanges.
		if(http.readyState == 4 && http.status == 200){
			console.log(http.responseText);
		}
	}
	http.send(params);
}

// Je comprends pas pourquoi mais cette ligne de commentaire est obligatoire ... (caractère caché)