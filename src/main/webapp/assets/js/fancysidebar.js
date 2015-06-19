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

		if (isClosed) {          
			overlay.hide();
			isClosed = !isClosed;
		} else {   
			overlay.show();
			isClosed = !isClosed;
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
		$('.pointer').removeClass('fa-caret-down');
		$('.pointer').addClass('fa-caret-left');
		$('#entity-menu').collapse('hide');
		$('#item-menu').collapse('hide');
		$('#plant-menu').collapse('hide');
		$('#organization-menu').collapse('hide');
		$('#workorder-menu').collapse('hide');
		$('#language-menu').collapse('hide');
		$('#administration-menu').collapse('hide');
		$('#account-menu').collapse('hide');
		isClosed = !isClosed;
	});

	$('#entity').click(function() {
		if (languageOpen) {
			$('#language-menu').collapse('hide');
			$('#language-caret').removeClass('fa-caret-down');
			$('#language-caret').addClass('fa-caret-left');
			languageOpen = !languageOpen;
		} if (administrationOpen) {
			$('#administration-menu').collapse('hide');
			$('#administration-caret').removeClass('fa-caret-down');
			$('#administration-caret').addClass('fa-caret-left');
			administrationOpen = !administrationOpen;
		} if (accountOpen) {
			$('#account-menu').collapse('hide');
			$('#account-caret').removeClass('fa-caret-down');
			$('#account-caret').addClass('fa-caret-left');
			accountOpen = !accountOpen;
		} if (entityOpen) {
			$('#entity-caret').removeClass('fa-caret-down');
			$('#entity-caret').addClass('fa-caret-left');
		} else {
			$('#entity-caret').removeClass('fa-caret-left');
			$('#entity-caret').addClass('fa-caret-down');
		}
		entityOpen = !entityOpen;
		colsole.log(entityOpen);
	});

	$('#item').click(function() {
		if (plantOpen) {
			$('#plant-menu').collapse('hide');
			$('#plant-caret').removeClass('fa-caret-down');
			$('#plant-caret').addClass('fa-caret-left');
			plantOpen = !plantOpen;
		} if(organizationOpen) {
			$('#organization-menu').collapse('hide');
			$('#organization-caret').removeClass('fa-caret-down');
			$('#organization-caret').addClass('fa-caret-left');
			organizationOpen = !organizationOpen;
		} if(workorderOpen) {
			$('#workorder-menu').collapse('hide');
			$('#workorder-caret').removeClass('fa-caret-down');
			$('#workorder-caret').addClass('fa-caret-left');
			workorderOpen = !workorderOpen;
		} if (itemOpen) {
			$('#item-caret').removeClass('fa-caret-down');
			$('#item-caret').addClass('fa-caret-left');
		} else {
			$('#item-caret').removeClass('fa-caret-left');
			$('#item-caret').addClass('fa-caret-down');
		}
		itemOpen = !itemOpen;
	});

	$('#plant').click(function() {
		if (itemOpen) {
			$('#item-menu').collapse('hide');
			$('#item-caret').removeClass('fa-caret-down');
			$('#item-caret').addClass('fa-caret-left');
			itemOpen = !itemOpen;
		} if(organizationOpen) {
			$('#organization-menu').collapse('hide');
			$('#organization-caret').removeClass('fa-caret-down');
			$('#organization-caret').addClass('fa-caret-left');
			organizationOpen = !organizationOpen;
		} if(workorderOpen) {
			$('#workorder-menu').collapse('hide');
			$('#workorder-caret').removeClass('fa-caret-down');
			$('#workorder-caret').addClass('fa-caret-left');
			workorderOpen = !workorderOpen;
		} if (plantOpen) {
			$('#plant-caret').removeClass('fa-caret-down');
			$('#plant-caret').addClass('fa-caret-left');
		} else {
			$('#plant-caret').removeClass('fa-caret-left');
			$('#plant-caret').addClass('fa-caret-down');
		}
		plantOpen = !plantOpen;
	});

	$('#organization').click(function() {
		if (itemOpen) {
			$('#item-menu').collapse('hide');
			$('#item-caret').removeClass('fa-caret-down');
			$('#item-caret').addClass('fa-caret-left');
			itemOpen = !itemOpen;
		} if(plantOpen) {
			$('#plant-menu').collapse('hide');
			$('#plant-caret').removeClass('fa-caret-down');
			$('#plant-caret').addClass('fa-caret-left');
			plantOpen = !plantOpen;
		} if(workorderOpen) {
			$('#workorder-menu').collapse('hide');
			$('#workorder-caret').removeClass('fa-caret-down');
			$('#workorder-caret').addClass('fa-caret-left');
			workorderOpen = !workorderOpen;
		} if (organizationOpen) {
			$('#organization-caret').removeClass('fa-caret-down');
			$('#organization-caret').addClass('fa-caret-left');
		} else {
			$('#organization-caret').removeClass('fa-caret-left');
			$('#organization-caret').addClass('fa-caret-down');
		}
		organizationOpen = !organizationOpen;
	});

	$('#workorder').click(function() {
		if (itemOpen) {
			$('#item-menu').collapse('hide');
			$('#item-caret').removeClass('fa-caret-down');
			$('#item-caret').addClass('fa-caret-left');
			itemOpen = !itemOpen;
		} if(plantOpen) {
			$('#plant-menu').collapse('hide');
			$('#plant-caret').removeClass('fa-caret-down');
			$('#plant-caret').addClass('fa-caret-left');
			plantOpen = !plantOpen;
		} if(organizationOpen) {
			$('#organization-menu').collapse('hide');
			$('#organization-caret').removeClass('fa-caret-down');
			$('#organization-caret').addClass('fa-caret-left');
			organizationOpen = !organizationOpen;
		} if (workorderOpen) {
			$('#workorder-caret').removeClass('fa-caret-down');
			$('#workorder-caret').addClass('fa-caret-left');
		} else {
			$('#workorder-caret').removeClass('fa-caret-left');
			$('#workorder-caret').addClass('fa-caret-down');
		}
		workorderOpen = !workorderOpen;
	});

	$('#language').click(function() {
		if (entityOpen) {
			$('#entity-menu').collapse('hide');
			$('#entity-caret').removeClass('fa-caret-down');
			$('#entity-caret').addClass('fa-caret-left');
			entityOpen = !entityOpen;
		} if (administrationOpen) {
			$('#administration-menu').collapse('hide');
			$('#administration-caret').removeClass('fa-caret-down');
			$('#administration-caret').addClass('fa-caret-left');
			administrationOpen = !administrationOpen;
		} if (accountOpen) {
			$('#account-menu').collapse('hide');
			$('#account-caret').removeClass('fa-caret-down');
			$('#account-caret').addClass('fa-caret-left');
			accountOpen = !accountOpen;
		} if (languageOpen) {
			$('#language-caret').removeClass('fa-caret-down');
			$('#language-caret').addClass('fa-caret-left');
		} else {
			$('#language-caret').removeClass('fa-caret-left');
			$('#language-caret').addClass('fa-caret-down');
		}
		languageOpen = !languageOpen;
	});

	$('#administration').click(function() {
		if (entityOpen) {
			$('#entity-menu').collapse('hide');
			$('#entity-caret').removeClass('fa-caret-down');
			$('#entity-caret').addClass('fa-caret-left');
			entityOpen = !entityOpen;
		} if (languageOpen) {
			$('#language-menu').collapse('hide');
			$('#language-caret').removeClass('fa-caret-down');
			$('#language-caret').addClass('fa-caret-left');
			languageOpen = !languageOpen;
		}
		if (accountOpen) {
			$('#account-menu').collapse('hide');
			$('#account-caret').removeClass('fa-caret-down');
			$('#account-caret').addClass('fa-caret-left');
			accountOpen = !accountOpen;
		} if (administrationOpen) {
			$('#administration-caret').removeClass('fa-caret-down');
			$('#administration-caret').addClass('fa-caret-left');
		} else {
			$('#administration-caret').removeClass('fa-caret-left');
			$('#administration-caret').addClass('fa-caret-down');
		}
		administrationOpen = !administrationOpen;
	});

	$('#account').click(function() {
		if (entityOpen) {
			$('#entity-menu').collapse('hide');
			$('#entity-caret').removeClass('fa-caret-down');
			$('#entity-caret').addClass('fa-caret-left');
			entityOpen = !entityOpen;
		} if (languageOpen) {
			$('#language-menu').collapse('hide');
			$('#language-caret').removeClass('fa-caret-down');
			$('#language-caret').addClass('fa-caret-left');
			languageOpen = !languageOpen;
		} if (administrationOpen) {
			$('#administration-menu').collapse('hide');
			$('#administration-caret').removeClass('fa-caret-down');
			$('#administration-caret').addClass('fa-caret-left');
			administrationOpen = !administrationOpen;
		} if (accountOpen) {
			$('#account-caret').removeClass('fa-caret-down');
			$('#account-caret').addClass('fa-caret-left');
		} else {
			$('#account-caret').removeClass('fa-caret-left');
			$('#account-caret').addClass('fa-caret-down');
		}
		accountOpen = !accountOpen;
	});

});
