package com.example.exersicemesure

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exersicemesure.databinding.ItemExersicseStatusBinding

class ExersciseStatusAdapter(val exerciseList : ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExersciseStatusAdapter.itmeViewHolder>(){
    class itmeViewHolder(binding: ItemExersicseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvitem = binding.tvitem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itmeViewHolder {
        return itmeViewHolder(ItemExersicseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {

        return exerciseList.size
    }

    override fun onBindViewHolder(holder: itmeViewHolder, position: Int) {
        val model : ExerciseModel = exerciseList[position]
        holder.tvitem.text = model.getId().toString()
        when{
            model.getIsSelected()->{
                holder.tvitem.background = ContextCompat.getDrawable(holder.tvitem.context,R.drawable.item_selected)
                holder.tvitem.setTextColor(Color.parseColor("#000000"))

            }
            model.getIsCompleted()->{
                holder.tvitem.background = ContextCompat.getDrawable(holder.tvitem.context,R.drawable.item_circular_color_ascent_backgraound)
                holder.tvitem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.tvitem.background = ContextCompat.getDrawable(holder.tvitem.context,R.drawable.item_circular_grey_background)
                holder.tvitem.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
}