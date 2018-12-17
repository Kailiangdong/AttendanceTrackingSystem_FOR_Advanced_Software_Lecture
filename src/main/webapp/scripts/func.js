function sendRest(){
    BASE_URL = "http://localhost:8080/rest/" // Your REST interface URL goes here

    var postcode = $(".select").
    // First do some basic validation of the postcode like
    // correct format etc.
    if (!validatePostcode(postcode)) {
        alert("Invalid Postal Code, please try again");
        return false;
    }
    var finalUrl = BASE_URL += "?postcode=" + postcode; 
    $.ajax({
        url: finalUrl,
        cache: false,
        success: function (html) {
            // Parse the recieved data here.
            console.log(html);
        }
    });
}