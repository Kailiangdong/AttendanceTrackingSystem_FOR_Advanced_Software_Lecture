# Group5-1-AttendanceTrackingSystem

ASE Project

## API Definitions
**[Register](#register)**  
**[Login](#login)**  
**[Logout](#logout)**  
**[Show all attendance log](#show-all-attendance-log)**  
**[Get token from server(JSON format)](#get-token-from-server(json-format))**  
**[Get token from server(XML format)(not implemented yet)](#get-token-from-server(xml-format)(not-implemented-yet))**  
**[Record attendance TUTOR(JSON format)](#record-attendance-tutor(json-format))**  
**[Record attendance TUTOR(XML format)(not implemented yet)](#record-attendance-tutor(xml-format)(not-implemented-yet))**  
**[Record attendance STUDENT(JSON format)](#record-attendance-student(jsonformat))**  
**[Record attendance STUDENT(XML format)(not implemented yet)](#record-attendance-student(xml-format)(not-implemented-yet))**  
**[Cloud messaging for android](#cloud-messaging-for-android)**  
**[Claim(not implemented yet)](#claim(not-implemented-yet))**  
**[Validation of missing attendance(not implemented yet)](#validation-of-missing-attendance(not-implemented-yet))**  

### Register
using POST method\
https://my-first-project-222110.appspot.com/rest/register  
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
https://my-first-project-222110.appspot.com/rest/login  
POST element:\
email=your_email_address&\
password=your_password

Response

```JSON
{
    "status" : "SUCCESS",
    "id" : "1234567891011",
    "first_name" : "firstName",
    "last_name" : "lastName",
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
### Logout
https://my-first-project-222110.appspot.com/rest/logout  
**GET Method:**  
Redirect to homepage

**POST Method:**  
No request element needed  
Response
```JSON
{
    "status" : "SUCCESS",
    "message" : "You have successfully logged out",
}
```

### Show all attendance log
using POST method\
https://my-first-project-222110.appspot.com/rest/attendance/log  
**for TUTOR:**  
POST element:  
group=selected_group_id/all&  
week=selected_week/all  
**for STUDENT:**  
No request element needed

Response
```JSON
{
	"status" : "SUCCESS",
	"attendance_log" : [
        {
            "student_id" : "stud_student_id",
            "first_name" : "firstName",
            "last_name" : "lastName",
            "group" : "groupxxx",
            "week_num" : "week_num",
        },{
            "student_id" : "stud_student_id",
            "first_name" : "firstName",
            "last_name" : "lastName",
            "group" : "groupxxx",
            "week_num" : "week_num",
        }
    ]
}
```

### Get token from server(JSON format):
using GET method\
https://my-first-project-222110.appspot.com/rest/attendance/get/json  
Response

```JSON
{
	"status" : "SUCCESS",
	"tokens" : [
        {
            "week" : "1",
            "token" : "1234567891011"
        },{
            "week" : "2",
            "token" : "1234567891011"
        },
        ...,
        {
            "week" : "12",
            "token" : "1234567891011"
        }
    ]
}
```
or
```JSON
{
    "status" : "ERROR",
    "reason" : "You are not a student"
}
```

### Get token from server(XML format)(not implemented yet):
using GET method\
~~https://my-first-project-222110.appspot.com/rest/attendance/get/xml?student_id=your_student_id&week_num=this_week_number~~  
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
https://my-first-project-222110.appspot.com/rest/attendance/record/json  
POST element:\
token=stud_token&\
student_id=stud_student_id&\
group=this_group&\
week=this_week&\
presented=true/false

Response:
```JSON
{
    "status" : "SUCCESS",
    "reason" : "",
}
```

### Record attendance TUTOR(XML format)(not implemented yet):
Using POST method:\
~~https://my-first-project-222110.appspot.com/rest/attendance/record/xml~~  
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
https://my-first-project-222110.appspot.com/rest/attendance/post/json  
POST element:\
token=stud_token&\
student_id=stud_student_id&\
group=this_group&\
week=this_week&\
presented=true/false

Response:
```JSON
{
    "status" : "ERROR",
    "reason" : "Token used before",
}
```
### Record attendance STUDENT(XML format)(not implemented yet):
Using POST method:\
~~https://my-first-project-222110.appspot.com/rest/attendance/post/xml~~  
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
**NOT in use: Validation notification**  

using GET method:\
https://my-first-project-222110.appspot.com/rest/message?student_id=your_student_id&date=last_updated_time  
Response
```JSON
{
    "status" : "NEW_ATTENDANCE",
    "attendance_log" : [
        {
            "student_id" : "stud_student_id",
            "group" : "group_num",
            "week" : "week_num"
        },{
            "student_id" : "stud_student_id",
            "group" : "group_num",
            "week" : "week_num"
        }
    ]  
}
```
or
```JSON
{
    "status" : "VALIDATION",
    "validations" : [
        {
            "validate_id" : "validate_id",
            "student_id" : "stud_student_id",
            "token" : "stud_token",
            "week" : "week_num",
            "group" : "group_num"
        },{
            "validate_id" : "validate_id",
            "student_id" : "stud_student_id",
            "token" : "stud_token",
            "week" : "week_num",
            "group" : "group_num"
        }
    ]
    
}
```

### Claim(not implemented yet)
using GET method:\
~~https://my-first-project-222110.appspot.com/rest/claim?student_id=your_student_id~~  

Response
```JSON
{
    "status" : "OK",
}
```

### Validation of missing attendance(not implemented yet)
using GET method:\
https://my-first-project-222110.appspot.com/rest/validate?validate_id=validate_id&student_id=this_student_id&result=true\false

Response
```JSON
{
    "status" : "OK",
}
```