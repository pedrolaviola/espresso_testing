package laviola.pucminas.espressoapp.ui

import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_user.*
import laviola.pucminas.espressoapp.listener.UserListener

class UserItem(private val user: User, private val listener: UserListener) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.userNameText.text = user.name
        viewHolder.userJobText.text = user.job
        viewHolder.itemContainer.setOnClickListener {
            listener.onUserClick(user)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_user
    }
}