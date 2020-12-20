package com.example.lovetest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MyAdapter(val context: Context, var list:List<MemoEntity>, var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder> (){
    //뷰홀더 생성되면 바인드뷰홀더로 넘어감
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(context).inflate(R.layout.item_memo,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val memo=list[position]
        holder.memo.text=memo.memo
        holder.check.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch{
                onDeleteListener.onDeleteListener(memo)
                Log.d("TAG","click")
            }
        }
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val memo=itemView.textview_memo
        val check=itemView.check
    }

}