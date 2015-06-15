$(document).ready(function () {
	var trigger = $('.hamburger'),
	overlay = $('.overlay'),
	isClosed = false,
	entityOpen = false,
	languageOpen = false,
	administrationOpen = false,
	accountOpen = false,
	itemOpen = false,
	plantOpen = false,
	organizationOpen = false,
	workorderOpen = false;

	trigger.click(function () {
		hamburger_cross();      
	});

	function hamburger_cross() {

		if (isClosed == true) {          
			overlay.hide();
			trigger.removeClass('is-open');
			trigger.addClass('is-closed');
			isClosed = false;
		} else {   
			overlay.show();
			trigger.removeClass('is-closed');
			trigger.addClass('is-open');
			isClosed = true;
		}
	}

	$('[data-toggle="offcanvas"]').click(function () {
		$('#wrapper').toggleClass('toggled');
	});

	$('#sidebar-wrapper').mouseleave(function() {
		overlay.hide();
		$('#wrapper').toggleClass('toggled');
		trigger.removeClass('is-open');
		trigger.addClass('is-closed');
		$('#entity-menu').collapse('hide');
		$('#item-menu').collapse('hide');
		$('#plant-menu').collapse('hide');
		$('#organization-menu').collapse('hide');
		$('#workorder-menu').collapse('hide');
		$('#language-menu').collapse('hide');
		$('#administration-menu').collapse('hide');
		$('#account-menu').collapse('hide');
		isClosed = false;
	});

});
