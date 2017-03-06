//  Bind the event handler to the "submit" JavaScript event
//alert('Text-field is empty.VALIDATION 222');
$('form').submit(function () {
	 alert('Text-field is empty.');
    // Get the Login Name value and trim it
    var name = $.trim($('#computerName').val());

    // Check if empty of not
    if (name === '') {
        alert('Text-field is empty.');
        return false;
    }
});