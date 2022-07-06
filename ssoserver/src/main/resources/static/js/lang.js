$(document).ready(function() {
	$('.sso-lang-btn').each(function(){
		var btn = $(this);
		var locale = btn.attr('data-lang');
		btn.click(function(e){
			e.preventDefault();
			var url = window.location.href;
			var pattern = /(lang=).*?(&|$)/;
			if (url.match(pattern)){
				url = url.replace(pattern,'$1' + locale + '$2');
			} else if (url.match(/\?/)) {
				url = window.location.href + '&lang=' + locale;
			} else {
				url = window.location.href + '?lang=' + locale;
			}
			window.location.href = url;
			//console.log(url);
		});
	});
});