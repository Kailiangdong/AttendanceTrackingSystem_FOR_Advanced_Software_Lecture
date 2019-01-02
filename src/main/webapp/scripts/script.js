function sendRequest() {
    var attendance_id = $('#attendance_text').val();
    var student_id = $('#student_text').val();
    var group = $('#select-group').val();
    var week = $('#select-week').val();
    var present = $('#presented').checked;
    //     $.ajax({
    //         type: "GET",
    //         url: "",
    //         datatype: "",
    //         success: 
    //    });
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/rest/attendance', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    /*xhr.onload = function () {
        // do something to response
        $('#result').html("Success");
    };*/
    xhr.onreadystatechange = function() {
        //Call a function when the state changes.
        if(xhr.readyState == 4 && xhr.status == 200) {
            var data = xhr.responseText;
            $('#result').html(data);
        }
    }
    xhr.send('attendance_id=' + attendance_id + '&student_id=' + student_id + '&group=' + group + '&week=' + week + '&presented=' + present);

}

function login() {
    var email = $('#email').val();
    var password = $('#password').val();
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/rest/login', true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    /*xhr.onload = function () {
        // do something to response
        $('#result').html("Success");
    };*/
    xhr.onreadystatechange = function() {
        //Call a function when the state changes.
        if(xhr.readyState == 4 && xhr.status == 200) {
            var data = xhr.responseText;
            $('#result').html(data);
        }
    }
    xhr.send('email=' + email + '&password=' + password);
}