# Group5-1-AttendanceTrackingSystem

ASE Project

## API Definitions
**[Register](#register)**  
**[Login](#login)**  
**[Logout](#logout)**  
**[Show all attendance log](#show-all-attendance-log)**  
**[Get token from server](#get-token-from-server)**  
**[Record attendance TUTOR(JSON format)](#record-attendance-tutorjson-format)**  
**[Record attendance TUTOR(XML format)(ignored)](#record-attendance-tutorxml-formatignored)**  
**[Record attendance STUDENT(JSON format)(deprecated)](#record-attendance-studentjson-formatdeprecated)**  
**[Record attendance STUDENT(XML format)](#record-attendance-studentxml-format)**  
**[Cloud messaging for android](#cloud-messaging-for-android)**  
**[Claim(not implemented yet)](#claimnot-implemented-yet)**  
**[Validation of missing attendance(not implemented yet)](#validation-of-missing-attendancenot-implemented-yet)**  

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
**DEBUG**: student_id=your_student_id

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

### Get token from server:
using GET method\
https://my-first-project-222110.appspot.com/rest/attendance/get/json  
**DEBUG**:  
https://my-first-project-222110.appspot.com/rest/attendance/get/json?student_id=your_student_id  
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
**DEBUG**:&id=your_id

Response:
```JSON
{
    "status" : "SUCCESS",
    "reason" : "",
}
```

### Record attendance TUTOR(XML format)(ignored):
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

### Record attendance STUDENT(JSON format)(deprecated):
Using POST method:\
~~https://my-first-project-222110.appspot.com/rest/attendance/post/json~~  
POST element:\
~~token=stud_token&\
student_id=stud_student_id&\
group=this_group&\
week=this_week&\
presented=true/false~~

Response:
```JSON
{
    "status" : "ERROR",
    "reason" : "Token used before",
}
```
### Record attendance STUDENT(XML format):
Using POST method:\
https://my-first-project-222110.appspot.com/rest/attendance/post/xml  
POST element:\
```XML
<record>
    <student_id>stu_id</student_id>
    <token>stu_token</token>
    <group>[1-6]</group>
    <week>[1-12]</week>
</record>
```

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