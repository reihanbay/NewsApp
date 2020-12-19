package com.task.newsapp.utils.helper

class DateHelper() {
    fun convertDate(publish: String): String{
        var time = publish
        var date = ""
        for (a in 0..9){
            date += time[a]
        }
        var splitDate = date.split("-")
        val year = splitDate[0]
        val splitMonth = splitDate[1].split("")
        val month : Int = splitMonth[1].toInt()
        fun Month(numberMonth: Int): String{
            var month = arrayOf("Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember")
            return month[numberMonth]
        }
        val day = splitDate[2]
        return "$day ${Month(month)} $year"
    }
}