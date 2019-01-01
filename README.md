# Group5-1-AttendanceTrackingSystem

ASE Project

## API Definitions
### Register
using POST method\
https://my-first-project-222110.appspot.com/rest/register\
POST element:\
first_name=your_first_name&\
last_name=your_last_name&\
email=your_email_address&\
password=your_password&\
group_name=[1-6]&\
is_tutor=false

Response

```JSON
{
	"status" : "SUCCESS",
    "id" : "1234567891011",
    "reason" : ""
}
```

### Login
using POST method\
https://my-first-project-222110.appspot.com/rest/login\
POST element:\
email=your_email_address&\
password=your_password

Response

```JSON
{
	"status" : "SUCCESS",
    "id" : "1234567891011",
    "is_tutor" : "false",
    "reason" : ""
}
```
or
```JSON
{
	"status" : "ERROR",
    "reason" : "Either email or password is incorrect"
}
```

### Show all attendance log
using POST method\
https://my-first-project-222110.appspot.com/rest/attendance/log\
POST element:\
group=your_group_id\
session=session_id

Response

```JSON
{
	"status" : "SUCCESS",
	"attendance_log" : [
        {
            "student_id" : "stud_student_id",
            "group" : "groupxxx",
            "week_num" : "week_num",
            "presented" : "true",
        },{
            "student_id" : "stud_student_id",
            "group" : "groupxxx",
            "week_num" : "week_num",
            "presented" : "true",
        }
    ]
}
```

### Get token from server(JSON format):
using GET method\
https://my-first-project-222110.appspot.com/rest/attendance/get/json?student_id=your_student_id&week_num=this_week_number\
Response

```JSON
{
	"status" : "SUCCESS",
	"token" : "1234567891011"
}
```

### Get token from server(XML format):
using GET method\
~~https://my-first-project-222110.appspot.com/rest/attendance/get/xml?student_id=your_student_id&week_num=this_week_number~~\
Response:
```XML
<authentification>
    <status>SUCCESS</status>
    <token>1234567891011</token>
</authentification>
```

### Record attendance TUTOR(JSON format):
Only tutors are allowed to use this endpoint(credential id will be checked)

Using POST method:\
https://my-first-project-222110.appspot.com/rest/attendance/record/json\
POST element:\
attendance_id=stud_attendance_id&\
student_id=stud_student_id&\
group=this_group&\
week_num=this_week_num&\
presented=true/false&\
credential_id=tutor_credential_id

Response:
```JSON
{
    "status" : "SUCCESS",
    "reason" : "",
}
```

### Record attendance TUTOR(XML format):
Using POST method:\
https://my-first-project-222110.appspot.com/rest/attendance/record/xml\
POST element:\
~~attendance_id=stud_attendance_id&\
student_id=stud_student_id&\
group=this_group&\
week_num=this_week_num&\
presented=true/false&\
credential_id=tutor_credential_id~~

Response:
```XML
<attendance>
    <status>SUCCESS</status>
    <reason></reason>
</attendance>
```

### Record attendance STUDENT(JSON format):
Using POST method:\
https://my-first-project-222110.appspot.com/rest/attendance/post/json\
POST element:\
student_id=your_student_id&\
token=your_token

Response:
```JSON
{
    "status" : "ERROR",
    "reason" : "Token used before",
}
```
### Record attendance STUDENT(XML format):
Using POST method:\
https://my-first-project-222110.appspot.com/rest/attendance/post/xml\
POST element:\
~~student_id=your_student_id&\
token=your_token~~

Response:
```XML
<attendance>
    <status>ERROR</status>
    <reason>This token doesn't belong to you</reason>
</attendance>
```

### Cloud messaging for android
using GET method:\
https://my-first-project-222110.appspot.com/rest/message?student_id=your_student_id\
Response
```JSON
{
    "status" : "NEW_ATTENDANCE",
    "student_id" : "stud_student_id",
    "group" : "group_num",
    "week" : "week_num"
}
```
or
```JSON
{
    "status" : "VALIDATION",
    "validate_id" : "validate_id",
    "student_id" : "stud_student_id",
    "token" : "stud_token",
    "week" : "week_num",
    "group" : "group_num"
}
```

### Validation of missed attendance
using GET method:\
https://my-first-project-222110.appspot.com/rest/validate?validate_id=validate_id&student_id=this_student_id&result=true\false

Response
```JSON
{
    "status" : "OK",
}
```