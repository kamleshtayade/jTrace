$(document).ready(function() {
	
	$('#accordion').hide();
	
	var isOpen = false;
	var entityCaret = true;
	var itemCaret = true;
	var plantCaret = true;
	var organizationCaret = true;
	var languageCaret = true;
	var administrationCaret = true;
	
	function openSidebar() {
		$('#menubutton').animate({
			left: '147px'
		});
		$('#accordion').animate({
			width: 'toggle',
			height: 'toggle'
		});
		$('#menubutton').removeClass("fa-bars");
		$('#menubutton').addClass("fa-remove");
		isOpen = !isOpen;
	}
	
	function closeSidebar() {
		$('#accordion').animate({
			width: 'toggle',
			height: 'toggle'
		});
		$('#menubutton').animate({
			left: '5px'
		});
		$('#menubutton').removeClass("fa-remove");
		$('#menubutton').addClass("fa-bars");
		isOpen = !isOpen;
	}
	
	$('#menubutton').click(function() {
		if(!isOpen) {
			openSidebar();
		}
		else {
			closeSidebar();
		}
	});	
	
	/*$('#sidebarmenu').mouseleave(function() {
		if (isOpen) {
			closeSidebar();
		}
	});*/
	
	$('.homeheading').on({
		mouseenter: function() {
			$('.homeheading').css('background-color', '#000000');
			$('.home').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.homeheading').css('background-color', '#FFFFFF');
			$('.home').css('color', '#000000');
	}
	});
	
	$('.entitymenu').on({
		mouseenter: function() {
			$('.entityheading').css('background-color', '#000000');
			$('.entity').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.entityheading').css('background-color', '#FFFFFF');
			$('.entity').css('color', '#000000');
	}
	});
	$('.entity').click(function() {
		if (entityCaret) {
			if(!languageCaret) {
				$('#languagecaret').removeClass("fa-caret-down");
				$('#languagecaret').addClass("fa-caret-left");
				languageCaret = !languageCaret;
			}
			else if(!administrationCaret) {
				$('#administrationcaret').removeClass("fa-caret-down");
				$('#administrationcaret').addClass("fa-caret-left");
				administrationCaret = !administrationCaret;
			}
			$('#entitycaret').removeClass("fa-caret-left");
			$('#entitycaret').addClass("fa-caret-down");
			entityCaret = !entityCaret;
		}
		else {
			$('#entitycaret').removeClass("fa-caret-down");
			$('#entitycaret').addClass("fa-caret-left");
			entityCaret = !entityCaret;
		}
	});
	
	$('.itemmenu').on({
		mouseenter: function() {
			$('.itemheading').css('background-color', '#000000');
			$('.item').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.itemheading').css('background-color', '#FFFFFF');
			$('.item').css('color', '#000000');
		}
	});
	$('.item').click(function() {
		if (itemCaret) {
			if(!plantCaret) {
				$('#plantcaret').removeClass("fa-caret-down");
				$('#plantcaret').addClass("fa-caret-left");
				plantCaret = !plantCaret;
			}
			else if(!organizationCaret) {
				$('#organizationcaret').removeClass("fa-caret-down");
				$('#organizationcaret').addClass("fa-caret-left");
				organizationCaret = !organizationCaret;
			}
			$('#itemcaret').removeClass("fa-caret-left");
			$('#itemcaret').addClass("fa-caret-down");
			itemCaret = !itemCaret;
		}
		else {
			$('#itemcaret').removeClass("fa-caret-down");
			$('#itemcaret').addClass("fa-caret-left");
			itemCaret = !itemCaret;
		}
	});
	
	$('.plantmenu').on({
		mouseenter: function() {
			$('.plantheading').css('background-color', '#000000');
			$('.plant').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.plantheading').css('background-color', '#FFFFFF');
			$('.plant').css('color', '#000000');
	}
	});
	$('.plant').click(function() {
		if (plantCaret) {
			if(!itemCaret) {
				$('#itemcaret').removeClass("fa-caret-down");
				$('#itemcaret').addClass("fa-caret-left");
				itemCaret = !itemCaret;
			}
			else if(!organizationCaret) {
				$('#organizationcaret').removeClass("fa-caret-down");
				$('#organizationcaret').addClass("fa-caret-left");
				organizationCaret = !organizationCaret;
			}
			$('#plantcaret').removeClass("fa-caret-left");
			$('#plantcaret').addClass("fa-caret-down");
			plantCaret = !plantCaret;
		}
		else {
			$('#plantcaret').removeClass("fa-caret-down");
			$('#plantcaret').addClass("fa-caret-left");
			plantCaret = !plantCaret;
		}
	});
	
	
	$('.organizationmenu').on({
		mouseenter: function() {
			$('.organizationheading').css('background-color', '#000000');
			$('.organization').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.organizationheading').css('background-color', '#FFFFFF');
			$('.organization').css('color', '#000000');
	}
	});
	$('.organization').click(function() {
		if (organizationCaret) {
			if(!itemCaret) {
				$('#itemcaret').removeClass("fa-caret-down");
				$('#itemcaret').addClass("fa-caret-left");
				itemCaret = !itemCaret;
			}
			else if(!plantCaret) {
				$('#plantcaret').removeClass("fa-caret-down");
				$('#plantcaret').addClass("fa-caret-left");
				plantCaret = !plantCaret;
			}
			$('#organizationcaret').removeClass("fa-caret-left");
			$('#organizationcaret').addClass("fa-caret-down");
			organizationCaret = !organizationCaret;
		}
		else {
			$('#organizationcaret').removeClass("fa-caret-down");
			$('#organizationcaret').addClass("fa-caret-left");
			organizationCaret = !organizationCaret;
		}
	});

	$('.languagemenu').on({
		mouseenter: function() {
			$('.languageheading').css('background-color', '#000000');
			$('.language').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.languageheading').css('background-color', '#FFFFFF');
			$('.language').css('color', '#000000');
	}
	});
	$('.language').click(function() {
		if (languageCaret) {
			if(!entityCaret) {
				$('#entitycaret').removeClass("fa-caret-down");
				$('#entitycaret').addClass("fa-caret-left");
				entityCaret = !entityCaret;
			}
			else if(!administrationCaret) {
				$('#administrationcaret').removeClass("fa-caret-down");
				$('#administrationcaret').addClass("fa-caret-left");
				administrationCaret = !administrationCaret;
			}
			$('#languagecaret').removeClass("fa-caret-left");
			$('#languagecaret').addClass("fa-caret-down");
			languageCaret = !languageCaret;
		}
		else {
			$('#languagecaret').removeClass("fa-caret-down");
			$('#languagecaret').addClass("fa-caret-left");
			languageCaret = !languageCaret;
		}
	});
	
	$('.administrationmenu').on({
		mouseenter: function() {
			$('.administrationheading').css('background-color', '#000000');
			$('.administration').css('color', '#FFFFFF');
		},
		mouseleave: function() {
			$('.administrationheading').css('background-color', '#FFFFFF');
			$('.administration').css('color', '#000000');
	}
	});
	$('.administration').click(function() {
		if (administrationCaret) {
			if(!entityCaret) {
				$('#entitycaret').removeClass("fa-caret-down");
				$('#entitycaret').addClass("fa-caret-left");
				entityCaret = !entityCaret;
			}
			else if(!languageCaret) {
				$('#languagecaret').removeClass("fa-caret-down");
				$('#languagecaret').addClass("fa-caret-left");
				languageCaret = !languageCaret;
			}
			$('#administrationcaret').removeClass("fa-caret-left");
			$('#administrationcaret').addClass("fa-caret-down");
			administrationCaret = !administrationCaret;
		}
		else {
			$('#administrationcaret').removeClass("fa-caret-down");
			$('#administrationcaret').addClass("fa-caret-left");
			administrationCaret = !administrationCaret;
		}
	});

});