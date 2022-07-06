package developer.abdusamid.asynctaskthread.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import developer.abdusamid.asynctaskthread.databinding.ItemRvBinding
import developer.abdusamid.asynctaskthread.entity.User

class UserAdapter() : ListAdapter<User, UserAdapter.VH>(MyDiffUtil()) {
    inner class VH(var itemRV: ItemRvBinding) : RecyclerView.ViewHolder(itemRV.root) {
        fun onBind(user: User) {
            itemRV.tv1.text = user.userName
            itemRV.tv2.text = user.password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    class MyDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }
    }
}