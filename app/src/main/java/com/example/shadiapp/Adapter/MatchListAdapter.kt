package com.example.shadiapp.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shadiapp.Main.MainActivity
import com.example.shadiapp.R
import com.example.shadiapp.dataclass.Result

class MatchListAdapter(val context:Context, val matchList : List<Result>):RecyclerView.Adapter<MatchListAdapter.ViewHolder>() {
    var mainActivity = context as MainActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: MatchListAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var cv_list_view:CardView
        var iv_image:ImageView
        var tv_name:TextView

        init {
            cv_list_view = itemView.findViewById(R.id.cv_list_view)
            iv_image = itemView.findViewById(R.id.iv_image)
            tv_name = itemView.findViewById(R.id.tv_name)
        }

        fun bind(position: Int){
            val list = matchList[position]
            tv_name.setText("${list.name.first} ${list.name.last}")

            Glide.with(context)
                .asBitmap()
                .load(list.picture.large)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        iv_image.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
            cv_list_view.id = position
            cv_list_view.setOnClickListener {
                mainActivity.selectedPosition = it.id
                it.findNavController().navigate(R.id.navigateToDetailPage)
            }
        }
    }
}