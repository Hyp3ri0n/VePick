// Gestion du type d'abo
$(document).ready(function() {
    $('input[type=radio][name=type]').change(function() {
    	var disabled = true;
        if (this.value == 'Abonne') {
        	disabled = false;
        }
        else if (this.value == 'NonAbonne') {
        	disabled = true;
        }

    	$("#nom").prop('disabled', disabled);
    	$("#prenom").prop('disabled', disabled);
    	$("#codeSecret").prop('disabled', disabled);
    	$("#date").prop('disabled', disabled);
    	$("#sexe").prop('disabled', disabled);
    	$("#adresse").prop('disabled', disabled);
    });
});


function creerUtilisateur() {
	
	var type = $("input[type=radio][name=type]").val();
	var nom = $("#nom").val();
	var prenom = $("#prenom").val();
	var code = $("#codeSecret").val();
	var date = $("#date").val();
	var sexe = $("input[type=radio][name=sexe]").val();
	var adresse = $("#adresse").val();
	var cb = $("#cb").val();
	
//	console.log(type);
//	console.log(nom);
//	console.log(prenom);
//	console.log(code);
//	console.log(date);
//	console.log(sexe);
//	console.log(adresse);
//	console.log(cb);
	

	var params = "commande=client&action=inscription";
	
	if (type == "Abonne") {
		params += "&type=abonne&nom=" + nom + "&prenom=" + prenom + "&code=" + code + "&date=" + date + "&sexe=" + sexe + "&adresse=" + adresse + "&cb=" + cb;
	} else {
		params += "&type=nonAbonne" + "&cb=" + cb;
	}
	
	var http = new XMLHttpRequest();
	var url = "/java/Client";
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


//Toujours là...