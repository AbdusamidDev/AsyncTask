package developer.abdusamid.asynctaskthread.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import developer.abdusamid.asynctaskthread.dao.UserDAO
import developer.abdusamid.asynctaskthread.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDataBase::class.java, "user_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}