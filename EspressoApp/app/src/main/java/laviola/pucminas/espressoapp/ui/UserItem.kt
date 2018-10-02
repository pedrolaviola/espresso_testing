package laviola.pucminas.espressoapp.ui

import laviola.pucminas.espressoapp.R
import laviola.pucminas.espressoapp.model.User
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_user.*

class UserItem(private val user: User) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.userNameText.text = user.name
        viewHolder.userJobText.text = user.job
    }

    override fun getLayout(): Int {
        return R.layout.item_user
    }
}