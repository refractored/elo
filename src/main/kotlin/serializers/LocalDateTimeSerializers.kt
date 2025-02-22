package net.refractored.serializers

import com.j256.ormlite.field.FieldType
import com.j256.ormlite.field.SqlType
import com.j256.ormlite.field.types.BaseDataType
import com.j256.ormlite.support.DatabaseResults
import java.time.LocalDateTime

class LocalDateTimeSerializers private constructor() : BaseDataType(SqlType.LONG_STRING, arrayOf<Class<*>>(LocalDateTime::class.java)) {
    override fun parseDefaultString(
        fieldType: FieldType?,
        defaultStr: String?,
    ): Any? = null

    override fun javaToSqlArg(
        fieldType: FieldType?,
        javaObject: Any?,
    ): String? {
        if (javaObject == null) {
            return null
        }
        return (javaObject as LocalDateTime).toString()
    }

    override fun resultToSqlArg(
        fieldType: FieldType?,
        results: DatabaseResults?,
        columnPos: Int,
    ): Any? = results?.getString(columnPos)

    override fun sqlArgToJava(
        fieldType: FieldType?,
        sqlArg: Any?,
        columnPos: Int,
    ): Any? {
        if (sqlArg == null) {
            return null
        }
        return LocalDateTime.parse(sqlArg as String)
    }

    companion object {
        private val singleTon = LocalDateTimeSerializers()

        @JvmStatic
        fun getSingleton(): LocalDateTimeSerializers = singleTon
    }
}
