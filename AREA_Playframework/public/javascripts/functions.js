$(document).ready(function() {
	$( ".mainmenu" ).menu();

	$("#content").accordion({
		icons:null,
		heightStyle:"content"
	});
	
	$( ".changeColorButton" ).button();
	
	// Link to open the dialog
	$( ".dialog-link" ).click(function( event ) {
		$( ".dialog" ).dialog( "open" );
		event.preventDefault();
	});
	
	// Hover states on the static widgets
	$( ".dialog-link, #icons li" ).hover(
		function() {
			$( this ).addClass( "ui-state-hover" );
		},
		function() {
			$( this ).removeClass( "ui-state-hover" );
		}
	);
	
	// start and end of waiting circle
	$(this).ajaxStart(function() {
		addAjaxWaiting();
	});
	$(this).ajaxStop(function() {
		removeAjaxWaiting();
	});
	
	// initialize recursion-slider
	updateSlider();
		
	// Read recursion-depth of bot
	readBotRecursion();
	
	// initialize progressbars
	$("#possess1").progressbar({
		max:50
	});
	$("#possess2").progressbar({
		max:50
	});
	playerPossessions();

	// Handle button events for changing colors
	$('.changeColorButton').click(function() {
		var buttonID = ""+$(this).attr("id");
		
		jsRoutes.controllers.Application.changeColorButton(buttonID).ajax({
			success:function() {
				drawField();
			},
			error:function() {
				alert("Error changing color");
				drawField();
			}
		});
	});
	
	// Handle events of menu
	// Start new game
	$("#new").click( function() {
		startNewGame();
	});
	// Load game
	$("#load").click( function() {
		addLoadForm();
	});
	// Save game
	$("#save").click( function() {
		addSaveForm();
	});
	// Resize field
	$("#resize").click( function() {
		addResizeForm();
	});
	
	
	drawField();
});

// Set buttons inactive if colors possessed by one player
function setButtonsActive() {
	alert("Test");
	$("#b").button("enable");
	$("#y").button("enable");
	$("#g").button("enable");
	$("#v").button("enable");
	$("#r").button("enable");
	alert("Before reading inactive colors");
	
	jsRoutes.controllers.Application.readInactColors().ajax({
		success: function(data) {
			var temp = split(";");
			alert("Colors: "+temp);
			$("#" + temp(0)).button("disable");
			$("#" + temp(1)).button("disable");
			removeAjaxWaiting();
			$.unblockUI();
		},
		error: function() {
			alert("Fehler beim Ermitteln der besetzten Farben!");
			$.unblockUI();
		}
	});
}

// Refresh field in browser
function drawField() {
	jsRoutes.controllers.Application.refreshFieldHtml().ajax({
		success: function(data) {
			$("#formContent").html("<div id='load-dialog-temp'></div>");
			$("#field").html(data);
			setFieldImages();
			$("#formContent").empty();
			removeAjaxWaiting();
			$.unblockUI();
		},
		error: function() {
			alert("Fehler beim Zeichnen des Spiels!");
			$.unblockUI();
		}
	});
	
	setButtonsActive();
	readBotRecursion();
	playerPossessions();
}

// Update progressbar
function playerPossessions() {
	jsRoutes.controllers.Application.possessions().ajax({
		success: function(data) {
			var possessions = data.split(";");
			
			var poss1 = parseInt(possessions[0]);
			var poss2 = parseInt(possessions[1]);
			
			var poss1d = parseFloat(possessions[0]);
			var poss2d = parseFloat(possessions[1]);
			
			if(poss1d == 50.0 && poss2d == 50.0) {
				addGameResult("Unentschieden! Versuchen Sie es doch nochmal.");
			} else {
				if(poss1d > 50.0) {
					addGameResult("Gratulation! Sie haben gewonnen!");
				}
				if(poss2d > 50.0) {
					addGameResult("Schade, Sie haben leider verloren.");
				}else {
					$("#possess1").progressbar( "option", "value", poss1);
					$("#possess2").progressbar( "option", "value", poss2);
				}
			}
		},
		error: function() {
			alert("Fehler beim Ermitteln der besetzten Fläche!");
		}
	});
}

// Generate a new game
function startNewGame() {
	var temp = jsRoutes.controllers.Application.refreshField().ajax({
		success: function() {
			drawField();
		},
		error: function() {
			alert("Error refresh");
			drawField();
		}
	});
}

// resize game field
function setFieldSize() {
	jsRoutes.controllers.Application.changeFieldSize( $("#resizeHeight").val(), $("#resizeWidth").val() ).ajax({
		success: function(data) {
			drawField();
		},
		error: function() {
			alert("Fehler beim &Auml;ndern der Spielfeldgr&ouml;&szlig;e!");
			drawField();
		}
	});
}

// Set recursion-depth
function writeBotRecursion() {
	var select = $( "#botRec" );
	var value = select[0].selectedIndex+1;
	
	jsRoutes.controllers.Application.resetBotRecursion(value).ajax({
		success: function(data) {
			var select = $( "#botRec" );
			select[0].selectedIndex = data - 1;
			$("#slider").slider( "value", data );
		},
		error: function(data) {
			alert("Fehler beim &Auml;ndern der Rekursion!");
		}
	});
}

// Set recursion by controller
function readBotRecursion() {
	jsRoutes.controllers.Application.botRecursion().ajax({
		success: function(data) {
			var select = $( "#botRec" );
			select[0].selectedIndex = data - 1;
			$("#slider").slider( "value", data );
		},
		error: function() {
			alert("Fehler beim Erzeugen eines neuen Spiels!");
		}
	});
}

// Save game to XML
function saveToXML(fileName, contextDialog) {
	jsRoutes.controllers.Application.saveGame("public/games/"+fileName).ajax({
		success: function(data) {
			//alert("Spiel wurde erfolgreich gespeichert unter: "+data);
			drawField();
			contextDialog.dialog( "close" );
		},
		error: function() {
			alert("Fehler beim Speichern des Spiels!");
			drawField();
		}
	});
}

// Load game from XML
function loadFromXML(fileName) {
	addAjaxWaiting();

	jsRoutes.controllers.Application.loadGame("public/games/"+fileName).ajax({
		success: function(data) {
			drawField();
			removeAjaxWaiting();
		},
		error: function() {
			alert("Fehler beim Laden des Spiels!");
			removeAjaxWaiting();
		}
	});
}

// Transformation of image sources
function setFieldImages() {
	var imageRoute = $("#imageRoute").attr("class");

	$(".r a img").attr("src", imageRoute+"r.png");
	$(".g a img").attr("src", imageRoute+"g.png");
	$(".y a img").attr("src", imageRoute+"y.png");
	$(".b a img").attr("src", imageRoute+"b.png");
	$(".v a img").attr("src", imageRoute+"v.png");
}

//update recursion-slider
function updateSlider(recursion) {
	var select = $( "#botRec" );
	
	select[0].selectedIndex = recursion - 1;
	
	 var slider = $( "<div id='slider'></div>" ).insertAfter( select ).slider({
	     min: 1,
	     max: 6,
	     range: "min",
	     value: select[ 0 ].selectedIndex + 1,
	     slide: function( event, ui ) {
	         select[ 0 ].selectedIndex = ui.value - 1;
	     },
	 	stop: function() {
	 		writeBotRecursion();
	 	}
	 });
	 $( "#botRec" ).change(function() {
	 	writeBotRecursion();
	 });
}

//update width-slider
function updateWidthSlider() {
	$("#widthSlider").slider({
	     min: 4,
	     max: 20,
	     step: 4,
	     value: $("#resizeWidth").spinner( "value" ),
	     slide: function( event, ui ) {
	    	 $("#resizeWidth").spinner( "value", ui.value );
	     }
	 });
}

//update height-slider
function updateHeightSlider() {
	$("#heightSlider").slider({
	     min: 4,
	     max: 20,
	     step: 4,
	     value: $("#resizeHeight").spinner( "value" ),
	     slide: function( event, ui ) {
	    	 $("#resizeHeight").spinner( "value", ui.value );
	     }
	 });
}

function addSaveForm() {
	var content = "<div class='blockUI'><form id='form-overlay' class='ui-widget-overlay blockUI blockElement'><div id='save-dialog-form' class='blockMsg blockPage' title='Speichern des aktuellen Spielstands'><p class='validateTips'>Bitte geben Sie den gew&uuml;nschten Datenamen an. Dabei d&uuml;rfen Sie keine Ordnerstruktur angeben!</p><form><fieldset><div class='ui-widget'><label for='saveName'>Dateiname</label><input type='text' name='saveName' id='saveName' class='text blockElement ui-widget-content ui-corner-all' /></div></fieldset></form></div></form></div>";
	$("#formContent").html(content);
	
	var availableTags = [
	                     "neuesSpiel.xml",
	                     "temp.xml",
	                     "resizedGame.xml",
	                     "blubb.xml"
	                 ];
	$("#saveName").autocomplete({
		source: availableTags,
		autoFocus: true
	});
	
	$( "#save-dialog-form" ).dialog({
		autoOpen: false,
		width: 400,
		show:"slow",
		hide:"scale",
		resizable:false,
		close: function(event, ui) {
			removeOverlay();
			$.unblockUI();
		},
		buttons: [
			{
				text: "Ok",
				click: function() {
					saveToXML($("#saveName").val(), $(this));
				}
			},
			{
				text: "Abbrechen",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});

	$.blockUI({ message: null });
	$("#save-dialog-form").dialog( "open" );
}

function addLoadForm() {
	$("#formContent").html("<form id='load-dialog-temp' class='ui-widget-overlay ui-corner-all'>preset content</form>");
	
	jsRoutes.controllers.Application.availableFiles().ajax({
		success: function(data) {
			var content = "<form id='form-overlay' class='ui-widget-overlay ui-corner-all'><div id='load-dialog-form' title='Laden eines gespeicherten Spiels'><p class='validateTips'>Bitte w&auml;hlen Sie das zu &ouml;ffnende Spiel aus.</p><form><fieldset><label for='loadName'>Gefundene Spiele:</label><div id='fileList'><div class='scrollbar'><div class='track'><div class='thumb'><div class='end'></div></div></div></div><div class='viewport'><div class='overview'><ol name='loadName' id='loadName' class='ui-widget-content ui-corner-all'></ol></div></div></div>Zu &ouml;ffnende Datei: <p id='selectedFile'></p></fieldset></form></div></form>";
			$("#formContent").empty().html(content);
			$("#loadName").html(data);
			
			$( "#load-dialog-form" ).dialog({
				autoOpen: false,
				width: 400,
				show:"slow",
				hide:"scale",
				resizable:false,
				open: function(event, ui) {
					$("#loadName").selectable({
						tolerance:"fit",
						stop: function() {
							var result = $( "#select-result" ).empty();
			                $("#selectedFile").html($(".ui-selected",this).html());
						}
					});		
				},
				beforeClose: function(event, ui) {
					$("#loadName").selectable("destroy");
					$("#loadName").remove();
					$("#form-overlay").empty();
					removeOverlay();
					$.unblockUI();
				},
				close: function(event, ui) {
					$("#load-dialog-form").dialog("destroy");
				},
				buttons: [
					{
						text: "Ok",
						click: function() {
							removeAjaxWaiting();
							var temp = $("#selectedFile").html();
							$(this).dialog("close");
							loadFromXML(temp);
							drawField();
						}
					},
					{
						text: "Abbrechen",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
			});

			$.blockUI({ message: null });
			$("#load-dialog-form").dialog("open");
			$("#fileList").tinyscrollbar();
		},
		error: function() {
			alert("Fehler! Keine Spieledateien gefunden!");
			$("#formContent").empty();
			drawField();
		}
	});
}

function addResizeForm() {
	var content = "<form id='form-overlay' class='ui-widget-overlay'><div id='resize-dialog-form' title='Gr&ouml;&szlig;e des Spielfelds &auml;ndern'><p class='validateTips'>Bitte w&auml;hlen Sie die neuen Ma&szlig;e f&uuml;r das Spielfeld. Das &Auml;ndern generiert ein neues Spiel.</p><form><fieldset><div class='resizeBox'><label for='resizeWidth'>Breite</label><input name='resizeWidth' id='resizeWidth' class='resizeSpinner' /><div id='widthSlider'></div></div><div class='resizeBox'><label for='resizeHeight'>H&ouml;he</label><input name='resizeHeight' id='resizeHeight' class='resizeSpinner' /><div id='heightSlider'></div></div></fieldset></form></div></form>";
	$("#formContent").html(content);
	
	$("#resizeWidth").spinner({
		min: 4,
		max: 20,
	    range: "min",
		step: 4,
		stop: function(event,ui) {
			$("#widthSlider").slider( "value", $("#resizeWidth").spinner("value") );
		}
	});
	
	$("#resizeHeight").spinner({
		min: 4,
		max: 20,
	    range: "min",
		step: 4,
		stop: function(event,ui) {
			$("#heightSlider").slider( "value", $("#resizeHeight").spinner("value") );
		}
	});
	
	updateWidthSlider();
	updateHeightSlider();
	
	$( "#resize-dialog-form" ).dialog({
		autoOpen: false,
		width: 400,
		show:"slow",
		hide:"scale",
		resizable:false,
		beforeClose: function(event, ui) {
			removeOverlay();
			$.unblockUI();
		},
		buttons: [
			{
				text: "Ok",
				click: function() {
					$( this ).dialog( "close" );
					setFieldSize();
				}
			},
			{
				text: "Abbrechen",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});

	$.blockUI({ message: null });
	$( "#resize-dialog-form" ).dialog( "open" );
}

function addGameResult(resultTitle) {
	var content = "<form id='form-overlay' class='ui-widget-overlay'><div id='game-result-dialog' title='"+resultTitle+"'><form><fieldset><p><span class='ui-icon ui-icon-info' style='float: left; margin: 0 14px 40px 0;'></span>Sie k&ouml;nnen nun entweder ein neues Spiel starten oder das aktuelle beibehalten.</p></fieldset></form></div></form>";
	//alert(content);
	$("#formContent").empty().html(content);

	$( "#game-result-dialog" ).dialog({
		autoOpen: false,
		width: 400,
		show:"slow",
		hide:"scale",
		resizable:false,
		modal:true,
		beforeClose: function(event, ui) {
			removeOverlay();
			$.unblockUI();
		},
		close: function(event,ui) {
			$(this).dialog("destroy");
		},
		buttons: [
					{
						text: "Neues Spiel",
						click: function() {
							$( this ).dialog( "close" );
							$("#new").trigger('click');
						}
					},
					{
						text: "Abbrechen",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
	});
	
	$.blockUI({ message: null });
	$("#game-result-dialog").dialog("open");
}

function addAjaxWaiting() {
	var content = "<div id='ajax-waiting' class='ui-widget-overlay' title='Warte auf Antwort...'><form><fieldset><div class='ball'></div><div class='ball1'></div></fieldset></form></div>";
	$("#formContent").html(content);

	$( "#ajax-waiting" ).dialog({
		autoOpen: false,
		width: 220,
		height: 220,
		show:"slow",
		hide:"scale",
		resizable:false,
		beforeClose: function(event, ui) {
			removeOverlay();
		}
	});
	$.blockUI({ message: null });
	$("#ajax-waiting").dialog("open");
}

function removeAjaxWaiting() {
	$("#ajax-waiting").dialog("close");
	if($("#load-dialog-temp").length > 0) {
		// do nothing
	} else if($("#load-dialog-form").length > 0) {
		// do nothing
	} else {
		$.unblockUI();
	}
}

function removeOverlay() {
	$("#formContent").children().remove();
}