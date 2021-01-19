package com.example.shadiapp.MatchList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shadiapp.Adapter.MatchListAdapter
import com.example.shadiapp.CustomDialog.CustomProgressDialog
import com.example.shadiapp.Main.MainActivity
import com.example.shadiapp.Network.ApiClient
import com.example.shadiapp.Network.ApiInterface
import com.example.shadiapp.Network.OnResponseListener
import com.example.shadiapp.R
import com.example.shadiapp.dataclass.ResponseData
import com.example.shadiapp.dataclass.Result
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_match_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchListFrag : Fragment(),OnResponseListener {
    lateinit var matchListPresenter: MatchListPresenter
    lateinit var mainActivity: MainActivity
    var customProgressDialog : CustomProgressDialog?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    fun bindView() {
        mainActivity = context as MainActivity
        matchListPresenter = MatchListPresenter(requireContext(),this)
        customProgressDialog = CustomProgressDialog(requireContext())
        customProgressDialog!!.show()
        val api = ApiClient.getClient()!!.create(ApiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val observable = api.getMatchList()
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val response = Gson().fromJson(result, ResponseData::class.java)
                        matchListPresenter.parseJson(response)
                    },
                    { error ->
                        matchListPresenter.parseJson( null)
                    }
                )

        }
    }

    fun setRecyclerView(list: List<Result>) {
        rv_matchList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = MatchListAdapter(requireContext(), list)
        }
    }

    override fun onSuccess(message: String, response: ResponseData) {
        mainActivity.responseData = response
        setRecyclerView(response.results)
        if(customProgressDialog!=null){
            customProgressDialog!!.cancel()
        }
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        if(customProgressDialog!=null){
            customProgressDialog!!.cancel()
        }
    }
}