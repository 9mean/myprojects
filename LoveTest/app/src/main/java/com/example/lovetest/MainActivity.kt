package com.example.lovetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(),OnDeleteListener {

    lateinit var db:MemoDatabase
    var memoList= listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db= MemoDatabase.getInstance(this)!!
        recyclerView.layoutManager=LinearLayoutManager(this)
        
        //초기 데이터베이스에 저장된 값 가져와서 세팅하기
        runBlocking {
            getAllMemos()
            setRecyclerView(memoList)
        }

        //버튼클릭시 동기적으로 ui업데이트
        btn_add.setOnClickListener {
            val memo=MemoEntity(null,edittext_memo.text.toString())
            edittext_memo.setText("")
            Log.d("TAG","코루틴스코프시작전")
            //코루틴스코프 만들기
            //동기 처리 가능
            //DREAM CODE
           runBlocking {
                insertMemo(memo)
               getAllMemos()
               setRecyclerView(memoList)
            }
        }



    }//onCreate end

    //1.Insert
    suspend fun insertMemo(memo:MemoEntity){
        db.memoDAO().insert(memo)
        Log.d("TAG","insert성공")
    }
    //2.Get
    suspend fun getAllMemos(){
        memoList=db.memoDAO().getAll()

        Log.d("TAG","get성공")
    }
    //3. Delete
   suspend fun deleteMemo(memo:MemoEntity){
        db.memoDAO().delete(memo)
        Log.d("TAG","deleteMemo from activity")
    }
    //4. 리사이클러뷰
    fun setRecyclerView(memoList:List<MemoEntity>){
        recyclerView.adapter=MyAdapter(this,memoList,this)
        Log.d("TAG","리사이클러뷰 연결성공")
    }

    override suspend fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
        Log.d("TAG","deleteMemo from activity listener")
        getAllMemos()
        setRecyclerView(memoList)
    }
}