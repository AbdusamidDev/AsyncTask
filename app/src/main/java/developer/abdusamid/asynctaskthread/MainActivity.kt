package developer.abdusamid.asynctaskthread

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import developer.abdusamid.asynctaskthread.adapters.UserAdapter
import developer.abdusamid.asynctaskthread.databases.AppDataBase
import developer.abdusamid.asynctaskthread.databinding.ActivityMainBinding
import developer.abdusamid.asynctaskthread.entity.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var appDatabase: AppDataBase
    lateinit var userAdapter: UserAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDataBase.getInstance(this)

//        RvTask().execute()

        binding.btnSave.setOnClickListener {
            val user = User()
            user.userName = binding.edt1.text.toString()
            user.password = binding.edt2.text.toString()

            Observable.fromCallable {
                appDatabase.userDao().addUser(user)
            }.subscribe(object : Consumer<Unit>, io.reactivex.functions.Consumer<Unit> {
                override fun accept(t: Unit) {
                    Toast.makeText(this@MainActivity, "Added", Toast.LENGTH_SHORT).show()
                }
            })

            MyAsyncTask().execute(user)
            //UserAdapter endi bu holatda ishlamaydi chunki avval u recycleView adapteri bo'lgan endi esa listAdapter'
        }

        userAdapter = UserAdapter()
        appDatabase.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>>,
                io.reactivex.functions.Consumer<List<User>> {
                override fun accept(t: List<User>) {
                    userAdapter.submitList(t)
                    binding.progressBar.visibility = View.GONE
                }
            }, object : Consumer<Throwable>, io.reactivex.functions.Consumer<Throwable> {
                override fun accept(t: Throwable) {

                }
            }, object : Action, io.reactivex.functions.Action {
                override fun run() {
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                }
            })
        binding.rv.adapter = userAdapter
    }

//    inner class RvTask : AsyncTask<Void, Void, List<User>>() {
//
//        //jarayon boshlanishidan oldin
//        @Deprecated("Deprecated in Java")
//        override fun onPreExecute() {
//            super.onPreExecute()
//            binding.progressBar.visibility = View.VISIBLE
//        }
//
//        //Jarayon fonda davom etadi bunda viewlarga o'zgartirish kiritish tavsiya etilmaydi
//        @Deprecated("Deprecated in Java")
//        override fun doInBackground(vararg params: Void): Flowable<List<User>> {
//            try {
//                //Database tez ishlagani uchun to'xtatib turailmoqda. AsyncTask ni tekshirish uchun
//                TimeUnit.SECONDS.sleep(3)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return appDatabase.userDao().getAllUsers()
//        }
//
//        //Jarayon tugaganidan keyin
//        @Deprecated("Deprecated in Java")
//        override fun onPostExecute(result: List<User>?) {
//            super.onPostExecute(result)
//            userAdapter = UserAdapter()
//            binding.rv.adapter = userAdapter
//            binding.progressBar.visibility = View.GONE
//        }
//
//    }

    inner class MyAsyncTask : AsyncTask<User, Void, Void>() {

        //taskni bajarishdan oldin ishlaydi
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: User): Void? {
            appDatabase.userDao().addUser(params[0])
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
        }

    }
}