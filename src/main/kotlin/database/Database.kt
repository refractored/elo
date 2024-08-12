package net.refractored.database

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.field.DataPersisterManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.table.TableUtils
import net.refractored.Elo
import net.refractored.serializers.LocalDateTimeSerializers
import net.refractored.user.EloUser
import java.util.*

/**
 * A static class used for database operations.
 */
class Database {
    companion object {
        /**
         * The connection source for the database.
         */
        @JvmStatic
        lateinit var connectionSource: JdbcConnectionSource
            private set

        /**
         * The User DAO, used for database operations on orders.
         */
        @JvmStatic
        lateinit var userDao: Dao<EloUser, UUID>
            private set

        /**
         * Initializes the database with values from the config.
         * This should be called once.
         * Call before any other database operations, and after the config has been loaded.
         */
        @JvmStatic
        fun init() {
            Elo.instance.logger.info("Initializing database...")

            if (Elo.instance.config
                    .node("Database", "url")
                    .string == "jdbc:mysql://DATABASE_IP:PORT/DATABASE_NAME"
            ) {
                Elo.instance.logger.error("Database not setup in config.")
                throw Exception("Database not setup in config.")
            }

            connectionSource =
                if (Elo.instance.config
                        .node("Database", "url")
                        .string
                        .equals("file", true)
                ) {
                    JdbcPooledConnectionSource(
                        "jdbc:sqlite:" + Elo.instance.dataFolder.toPath() + "/database.db",
                    )
                } else {
                    JdbcPooledConnectionSource(
                        Elo.instance.config
                            .node("Database", "url")
                            .string,
                        Elo.instance.config
                            .node("Database", "user")
                            .string,
                        Elo.instance.config
                            .node("Database", "password")
                            .string,
                    )
                }

            @Suppress("UNCHECKED_CAST")
            userDao = DaoManager.createDao(connectionSource, EloUser::class.java) as Dao<EloUser, UUID>

            TableUtils.createTableIfNotExists(connectionSource, EloUser::class.java)

            DataPersisterManager.registerDataPersisters(LocalDateTimeSerializers.getSingleton())

            Elo.instance.logger.info("Database initialized")
        }
    }
}
