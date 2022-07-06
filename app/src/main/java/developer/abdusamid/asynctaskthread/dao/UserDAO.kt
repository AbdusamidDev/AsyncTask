package developer.abdusamid.asynctaskthread.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import developer.abdusamid.asynctaskthread.entity.User
import io.reactivex.Flowable

@Dao
interface UserDAO {
    @Insert
    fun addUser(user: User)

    @Query("select * from users")
    fun getAllUsers(): Flowable<List<User>>
}