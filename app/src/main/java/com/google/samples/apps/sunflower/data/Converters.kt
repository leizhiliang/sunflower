package com.google.samples.apps.sunflower.data

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * 类型转换器以允许 Room 引用复杂的数据类型。
 */
class Converters {

    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
