package app.trian.tudu.data.utils

enum class DateTimeFormatStyle(val text:String,val value:String){
    YYYYMMDD("2022/02/06","yyyy/MM/dd"),
    DDMMYYYY("06/02/2022","d MMMM, yyyy"),
    MMDDYYYY("02/06/2022","MM/dd/YYYY"),
    DEFAULT("Default System","DEFAULT"),
    TWELVE("12 Hour","12"),
    TWENTY("24 Hour","24")
}