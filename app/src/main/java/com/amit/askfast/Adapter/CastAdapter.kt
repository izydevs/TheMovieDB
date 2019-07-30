package com.amit.askfast.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amit.askfast.Model.CastCrew
import com.amit.askfast.R

class CastAdapter(private val myList: List<CastCrew.Cast>, private val context: Context) :
    RecyclerView.Adapter<CastAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cast_item_layout, viewGroup, false)
        )
    }

    override fun onBindViewHolder(myViewHolder: CastAdapter.MyViewHolder, i: Int) {
        val cast = myList[i]
        setName(myViewHolder.actorName, cast.name)
        setName(myViewHolder.characterName, cast.character)
    }

    private fun setName(characterName: TextView, character: String) {
        if (!character.isEmpty() || character != "") {
            characterName.text = character
        } else
            characterName.text = context.resources.getText(R.string.na_text)
    }

    override fun getItemCount(): Int {
        return if (myList.size > 5) 5 else myList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var actorName: TextView = itemView.findViewById(R.id.actor_name)
        internal var characterName: TextView = itemView.findViewById(R.id.role_name)

    }
}
