package com.angelo.bdroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddProductActivity : AppCompatActivity() {

    private var etName          : EditText ?= null
    private var etPrice         : EditText ?= null
    private var etDescription   : EditText ?= null
    private var btnSave         : Button   ?= null

    private var idProduct       : Int      ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //Initializations
        etName          = et_name
        etPrice         = et_price
        etDescription   = et_description
        btnSave         = btn_save

        val database = ProductDataBase.getDatabase(this)

        val data = intent.extras
        if(data != null){
            idProduct = data.getInt("id")
            lifecycleScope.launch {
                val productDatabase = withContext(Dispatchers.IO) { database.productDAO().getProduct(idProduct.toString().toInt()) }
                etName?.setText(productDatabase.name)
                etPrice?.setText(productDatabase.price.toString())
                etDescription?.setText(productDatabase.description)
            }
        }

        btnSave?.apply {
            setOnClickListener {
                val name        = etName?.text.toString().trim()
                val price      = etPrice?.text.toString().trim().toDouble()
                val description = etDescription?.text.toString().trim()
                val image          = R.drawable.ic_launcher_background

                val product = ProductEntity(name,price,description,image)

                if(idProduct != null){
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            product.idProduct = idProduct.toString().toInt()
                            database.productDAO().updateProduct(product)
                        }
                        this@AddProductActivity.finish()
                    }
                }else{
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO){ database.productDAO().insertProduct(product) }
                        this@AddProductActivity.finish()
                    }
                }
            }
        }
    }

}