package com.angelo.bdroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ProductAdapter.OnProductClickListener {

    private var rvProduct   : RecyclerView ?= null
    private val mAdapter    : ProductAdapter by lazy { ProductAdapter(this,this) }

    private var dataList = mutableListOf<ProductEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initializations
        rvProduct = rv_product

        val dataBase = ProductDataBase.getDatabase(this)

        //RecyclerView
        rvProduct?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter       = mAdapter
        }

        dataBase.productDAO().getAllProducts().observe(this, Observer {
            dataList = it
            mAdapter.apply {
                setProductData(dataList)
                notifyDataSetChanged()
            }
        })

    }

    override fun onItemClicked(idProduct: Int) {
        Toast.makeText(this, "$idProduct", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,DetailsActivity::class.java)
        intent.apply {
            putExtra("id",idProduct)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_contact->{
                val intent = Intent(this,AddProductActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}