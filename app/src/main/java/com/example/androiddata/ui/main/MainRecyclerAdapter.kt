package com.example.androiddata.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androiddata.R
import com.example.androiddata.data.Monster
import com.example.androiddata.utilities.PrefsHelper

/**
 * Set constructor arguments in class declaration signature. So include parens with
 * input arguments. Use 'val' keyword so they persist for the lifetime of the adapter
 */

class MainRecyclerAdapter(
    val context: Context,
    val monsters: List<Monster>,
    val itemListener: MonsterItemListener):
RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.nameText)
        val monsterImage = itemView.findViewById<ImageView>(R.id.monsterImage)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //Get the type of layout from the shared preferences file
        //Will need similar code in MainFragment to ensure the layout starts up with the user's
        //previous choice
        val layoutStyle = PrefsHelper.getItemType(parent.context)
        val layoutId = if (layoutStyle == "grid") {
            R.layout.monster_grid_item
        } else {
            R.layout.monster_list_item
        }

        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val monster = monsters[position]
        with(holder) {
            nameText?.let {
                it.text = monster.monsterName
                it.contentDescription = monster.monsterName
            }
            ratingBar?.rating = monster.scariness.toFloat()

            //Glide will load the image once then cache it locally
            Glide.with(context).load(monster.thumbnailUrl).into(monsterImage)

            //Handle the click event. Attach the listener and send the object item to the Fragment
            itemView.setOnClickListener{
                itemListener.onMonsterItemClick(monster)
            }
        }
    }

    /**
     * Interface for communication with MainFragment
     */
    interface MonsterItemListener {
        fun onMonsterItemClick(monster: Monster)
    }
}