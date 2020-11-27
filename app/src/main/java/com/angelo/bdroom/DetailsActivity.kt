package com.angelo.bdroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsActivity : AppCompatActivity() {

    private var ivProductDetails        : ImageView ?= null
    private var tvNameDetails           : TextView  ?= null
    private var tvPriceDetails          : TextView  ?= null
    private var tvDescriptionDetails    : TextView  ?= null

    private lateinit var database       : ProductDataBase
    private lateinit var productEntity        : ProductEntity
    private lateinit var productEntityLiveData: LiveData<ProductEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Initializations using kotlin synthetic
        ivProductDetails        = iv_product_details
        tvNameDetails           = tv_name_details
        tvPriceDetails          = tv_price_details
        tvDescriptionDetails    = tv_description_details

        database = ProductDataBase.getDatabase(this)

        val data = intent.extras
        if (data != null){
            val idProduct   = data.getInt("id",0)
            productEntityLiveData = database.productDAO().getProductLiveData(idProduct)
            productEntityLiveData.observe(this, Observer {
                productEntity = it
                ivProductDetails?.setImageResource(productEntity.image)
                tvNameDetails?.text         = productEntity.name
                tvPriceDetails?.text        = productEntity.price.toString()
                tvDescriptionDetails?.text  = productEntity.description
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.update_contact->{
                val intent = Intent(this,AddProductActivity::class.java)
                intent.apply {
                    putExtra("id",productEntity.idProduct)
                }
                startActivity(intent)
            }
            R.id.delete_contact->{
                productEntityLiveData.removeObservers(this)
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){ database.productDAO().deleteProduct(productEntity) }
                    this@DetailsActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}