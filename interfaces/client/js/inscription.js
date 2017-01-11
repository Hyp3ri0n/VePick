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
	var nom = $("input[type=text][id=nom]").val();
	var prenom = $("#prenom").val();
	var code = $("#codeSecret").val();
	var date = $("#date").val();
	var sexe = $("#sexe").val();
	var nom = $("#adresse").val();
	
	alert(type, nom, prenom, code, date, sexe, nom);
	
//	var http = new XMLHttpRequest();
//	var url = "/java/Scenario";
//	var params = "commande=scenario&num=1&part=1";
//	http.open("POST", url, true);
//
//	//Sendtheproperheaderinformationalongwiththerequest
//	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//
//	http.onreadystatechange = function(){//Callafunctionwhenthestatechanges.
//		if(http.readyState == 4 && http.status == 200){
//			console.log(http.responseText);
//			premierScenarioPartDeux();
//		}
//	}
//	http.send(params);
}


//Toujours là...