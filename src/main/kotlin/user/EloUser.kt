package net.refractored.user

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import net.dv8tion.jda.api.entities.User
import net.refractored.database.Database
import net.refractored.serializers.LocalDateTimeSerializers
import java.time.LocalDateTime
import java.util.*

@DatabaseTable(tableName = "joblistings_mail")
data class EloUser(
    @DatabaseField(id = true)
    val id: UUID,
    @DatabaseField
    var user: Long,
    @DatabaseField(persisterClass = LocalDateTimeSerializers::class)
    var timeCreated: LocalDateTime,
    @DatabaseField
    var points: Int = 0,
) {
    /**
     * This constructor should only be used for ORMLite
     */
    constructor() : this(
        UUID.randomUUID(),
        0,
        LocalDateTime.now(),
        0,
    )

    companion object {
        fun create(user: Long): EloUser =
            EloUser(
                UUID.randomUUID(),
                user,
                LocalDateTime.now(),
                0,
            )

        fun setPoints(
            user: User,
            points: Int,
        ) {
            val eloUser =
                Database.userDao
                    .queryBuilder()
                    .where()
                    .eq("user", user.idLong)
                    .queryForFirst()
                    ?: create(user.idLong)
            eloUser.points = points
            Database.userDao.update(eloUser)
        }

        fun removePoints(
            user: User,
            points: Int,
        ) {
            val eloUser =
                Database.userDao
                    .queryBuilder()
                    .where()
                    .eq("user", user.idLong)
                    .queryForFirst()
                    ?: create(user.idLong)
            eloUser.points -= points
            Database.userDao.update(eloUser)
        }

        fun addPoints(
            user: User,
            points: Int,
        ) {
            val eloUser =
                Database.userDao
                    .queryBuilder()
                    .where()
                    .eq("user", user.idLong)
                    .queryForFirst()
                    ?: create(user.idLong)
            eloUser.points += points
            Database.userDao.update(eloUser)
        }
    }
}
