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
        fun create(user: User): EloUser {
            if (Database.userDao
                    .queryBuilder()
                    .where()
                    .eq("user", user.idLong)
                    .queryForFirst() != null
            ) {
                throw IllegalArgumentException("User already exists")
            }
            val eloUser =
                EloUser(
                    UUID.randomUUID(),
                    user.idLong,
                    LocalDateTime.now(),
                    0,
                )
            Database.userDao.create(
                eloUser,
            )
            return eloUser
        }

        /**
         * Gets the points of a user.
         * @return The points of the user, or 0 if the user does not exist.
         */
        fun getPoints(user: User): Int {
            val eloUser =
                Database.userDao
                    .queryBuilder()
                    .where()
                    .eq("user", user.idLong)
                    .queryForFirst()
            return eloUser?.points ?: 0
        }

        /**
         * Sets the points of a user.
         * @param user The user to set the points for.
         * @param points The points to set.
         */
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
                    ?: create(user)
            eloUser.points = points
            Database.userDao.update(eloUser)
        }

        /**
         * Removes points from a user.
         * @param user The user to remove points from.
         * @param points The points to remove.
         */
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
                    ?: create(user)
            eloUser.points -= points
            Database.userDao.update(eloUser)
        }

        /**
         * Adds points to a user.
         * @param user The user to add points to.
         * @param points The points to add.
         */
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
                    ?: create(user)
            eloUser.points += points
            Database.userDao.update(eloUser)
        }
    }
}
