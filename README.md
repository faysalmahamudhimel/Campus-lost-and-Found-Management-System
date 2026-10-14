# Campus Lost & Found

এটি Java দিয়ে তৈরি একটি console-based Campus Lost & Found application। Student lost/found item report করতে পারে, found item খুঁজে claim request করতে পারে, আর Admin item ও claim দেখতে এবং claim approve/reject করতে পারে।

## Project structure

```text
Campus Lost & Found/
├── src/
│   ├── Main.java
│   ├── model/
│   ├── service/
│   ├── repository/
│   └── exception/
├── test/                 # এখনো কোনো test source নেই
├── lost_items.txt        # lost item-এর saved record
├── found_items.txt       # found item-এর saved record
├── claims.txt            # claim-এর saved record
└── README.md
```

### Folder ও file-এর কাজ

- `src/Main.java` — application entry point; login/menu, user input, item search, report এবং claim-এর flow চালায়।
- `src/model/` — application-এর মূল data ও domain class। এখানে `User`, `Student`, `Admin`, `Item`, `LostItem`, `FoundItem`, `Claim`, `Location` এবং `Reportable` আছে।
- `src/service/` — application-এর কাজের নিয়ম ও orchestration। `LoginService` login যাচাই করে; `LostFoundService` item ও claim-এর কাজ repository-তে পাঠায়; `ReportGenerator` report behavior দেয়।
- `src/repository/` — text file-এ record save, load বা update করে। প্রতিটি repository সংশ্লিষ্ট data file ব্যবহার করে।
- `src/exception/` — application-specific exception। `ItemNotFoundException` item না পেলে ব্যবহার করার জন্য তৈরি।
- `test/` — test রাখার folder; বর্তমানে খালি।
- Root-এর `.txt` file-গুলো — application চালানোর working directory থেকে read/write হওয়া plain-text data file। প্রতিটি record comma দিয়ে আলাদা করা field-এ লেখা হয়।

## Object-oriented programming কোথায় আছে

### Encapsulation

Encapsulation-এর উদাহরণগুলো এই `.java` file-এ আছে:

- `src/model/Item.java` — item-এর field `private`; getter দিয়ে value পাওয়া যায়।
- `src/model/User.java` — user-এর field `private`; getter দিয়ে value পাওয়া যায়।
- `src/model/Claim.java` — claim-এর field `private`; status `approveClaim()` ও `rejectClaim()` method দিয়ে বদলায়।
- `src/model/Location.java` — location-এর field `private`; getter ও `getFullLocation()` দিয়ে তথ্য পাওয়া যায়।
- `src/model/LostItem.java` এবং `src/model/FoundItem.java` — date field private এবং getter দিয়ে পাওয়া যায়।
- `src/repository/LostItemRepository.java`, `src/repository/FoundItemRepository.java`, `src/repository/ClaimRepository.java` — file save/load/update-এর কাজ repository method-এর ভেতরে রাখা হয়েছে।
- `src/service/LoginService.java` এবং `src/service/LostFoundService.java` — login ও lost/found item/claim-এর কাজ service method দিয়ে করা হয়।

### Inheritance

- `Student extends User` এবং `Admin extends User` — দুই ধরনের user-ই `User`-এর common data ও contract পায়।
- `LostItem extends Item` এবং `FoundItem extends Item` — দুই item-ই `Item`-এর common fields ও behavior পায়।
- `ItemNotFoundException extends Exception` — Java-এর exception class-কে extend করে custom exception বানানো হয়েছে।

এই inheritance-গুলো যথাক্রমে `src/model/Student.java`, `src/model/Admin.java`, `src/model/LostItem.java`, `src/model/FoundItem.java` এবং `src/exception/ItemNotFoundException.java`-এ লেখা আছে।

### Abstraction

- `User` একটি abstract class: common user তথ্য ও `showDashboard()` contract রাখে; নির্দিষ্ট dashboard subclass-এ implement হয়।
- `Item` একটি abstract class: common item তথ্য ও abstract `displayItem()` contract রাখে; lost/found item নিজস্বভাবে display করে।
- `Reportable` একটি interface: `generateReport()` behavior-এর contract নির্ধারণ করে। `ReportGenerator` সেটি implement করে।

অর্থাৎ abstraction-এর code আছে `src/model/User.java`, `src/model/Item.java`, `src/model/Reportable.java` এবং `src/service/ReportGenerator.java` file-এ।

### Polymorphism

`Student` ও `Admin` নিজ নিজ `showDashboard()` override করে। `LostItem` ও `FoundItem` নিজ নিজ `displayItem()` override করে। `Main`-এ login result `User` reference-এ রাখা হয়, যা `Student` বা `Admin` object ধারণ করতে পারে।

Override করা method-গুলো আছে `src/model/Student.java`, `src/model/Admin.java`, `src/model/LostItem.java` এবং `src/model/FoundItem.java`-এ। `User` type reference ব্যবহার করা হয়েছে `src/Main.java` ও `src/service/LoginService.java`-এ।

## Login তথ্য

বর্তমানে `LoginService`-এ demo credentials hard-coded:

| Role | ID | Password |
|---|---|---|
| Student | `S001` | `1234` |
| Admin | `A001` | `admin123` |

## চালানোর নিয়ম

JDK ইনস্টল থাকা লাগবে। Project root-এ terminal খুলে compile ও run করুন:

```bash
javac -d out src/Main.java src/model/*.java src/service/*.java src/repository/*.java src/exception/*.java
java -cp out Main
```

Application-এর relative data file-গুলো project root থেকে খুঁজে; তাই root directory থেকেই `java` command চালান।

## Data format

Repository-গুলো প্রতি record-এ একটি line লেখে। Lost/found item-এ ID, নাম, description, location ও date থাকে। Claim-এ claim ID, student ID, item ID, claim date ও status থাকে।

বর্তমান format comma-delimited; user input-এ comma থাকলে field parsing-এ সমস্যা হতে পারে।
