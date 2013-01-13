

$(function () {
	mainTabs = $('#someTabs').tabs({
			        select: function(ev, ui) {
			            location.href = ui.tab.href;
			            return true;
			        }
	    });
});