package com.example.shadiapp.DetailPage

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shadiapp.Main.MainActivity
import com.example.shadiapp.R
import kotlinx.android.synthetic.main.fragment_detail_page.*

class DetailPageFrag : Fragment() ,View.OnClickListener,DetailPageInterface{
    lateinit var mainActivity: MainActivity
    lateinit var detailPagePresenter: DetailPagePresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    fun bindView(){
        mainActivity= context as MainActivity
        detailPagePresenter = DetailPagePresenter(requireContext(),this)
        setOnClickListener()
        setData()
    }

    fun setData(){
        Glide.with(requireContext())
            .asBitmap()
            .load(mainActivity.responseData!!.results[mainActivity.selectedPosition].picture.large)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    iv_image.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

        val data = mainActivity.responseData!!.results[mainActivity.selectedPosition]
        tv_name.setText("${data.name.first} ${data.name.last}")
        if(data.gender.startsWith("M",ignoreCase = true)){
            tv_gender.setText("Male")
        }else if(data.gender.startsWith("F",ignoreCase = true)){
            tv_gender.setText("Female")
        }else{
            tv_gender.setText(data.gender)
        }
        tv_dob.setText("${data.dob.age}")
        setAcceptReject()
    }

    fun setAcceptReject(){
        if(mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted.equals("N")){
            ly_accept.setText("Accept")
            ly_reject.setText("Member Rejected")
        }else if(mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted.equals("Y")){
            ly_accept.setText("Member Accepted")
            ly_reject.setText("Reject")
        }else{
            ly_accept.setText("Accept")
            ly_reject.setText("Reject")
        }
    }

    fun setOnClickListener(){
        ly_accept.setOnClickListener(this)
        ly_reject.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.ly_accept->{
                if(!mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted.equals("Y") ){
                    mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted = "Y"
                    setAcceptReject()
                    detailPagePresenter.saveDataToDb(mainActivity.responseData!!)
                }
            }

            R.id.ly_reject->{
                if(!mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted.equals("N")){
                    mainActivity.responseData!!.results[mainActivity.selectedPosition].accepted = "N"
                    setAcceptReject()
                    detailPagePresenter.saveDataToDb(mainActivity.responseData!!)
                }
            }
        }
    }

    override fun setSelectedDone() {
        mainActivity.onBackPressed()
    }
}