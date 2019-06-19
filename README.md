# HackerMail

## Introduction
This Android app is designed for engineers who are too busy to send an email for various reasons.

For example: send emails to their wives and/or husbands when they might be home late for reason ''you can't say''.

By creating templates for different topics, each mail will automatcially choose a template as mail body and send to target mail address.

## Members
    
- ND8071500
- P76063016
- Q56073013

## List of tools, libraries, platform, etc. used in the project
- Database
    - `RoomDatabase`+`AndroidViewModel`+`SupportSQLiteDatabase`
    - `LiveData`+`Observer`+`AsyncTask`
    - One-to-Many relationship (using foreign key `@Foreign` and `@Index`)
- Alarm
    - `AlarmManager`
    - `BroadcastReceiver`
- View
    - Total **5** activities
    - Nested `RecylerView` for One-to-Many relationship
    - `FrameLayout` for different activities
- Email
    - Reference [RFC 6068](https://tools.ietf.org/html/rfc6068) for `mailto@` schema
    - Build-in `Intent`
    - Create `mailto@` schema using `Uri.from`
    
## Contributions of each team member
### Total Contribution
- ND8071500 33%
- P76063016 33%
- Q56073013 33%

### System
||ND8071500|P76063016|Q56073013|
|-|-|-|-|
|Idea Pitch|O|O|O|
|System Architecture|O|O|O|
|Database Design|O|O|X|
|Object Relational Mapping|O|O|X|
|Asynchronous Fetch Data|O|X|X|
|Alarm Manager|O|O|X|
|Broadcase Receiver|O|X|X|
|Email RFC|O|X|X|
|Email Intent|O|O|X|
|**TOTAL**|100%|66.6%|22.2%|

### UI & UX
||ND8071500|P76063016|Q56073013|
|-|-|-|-|
|System Flow|O|O|O|
|Interface|X|O|O|
|Interaction|X|O|O|
|Optimization|X|X|O|
|Design|X|X|O|
|UI Flow|X|X|O|
|**TOTAL**|16.6%|50%|100%|

# APK download
[click me](https://drive.google.com/file/d/1TddXkahoW7U3ZLPS4y6ENnitVfboo-jv/view?usp=sharing)
