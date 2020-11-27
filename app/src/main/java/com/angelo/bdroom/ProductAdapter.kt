package com.angelo.bdroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(private val ctx: Context, private val itemClickListener: OnProductClickListener):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    interface OnProductClickListener{
        fun onItemClicked(idProduct: Int)
    }

    private var productList = mutableListOf<ProductEntity>()

    fun setProductData(dataProductEntity:MutableList<ProductEntity>){
        productList = dataProductEntity
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(ctx).inflate(R.layout.product_item,parent,false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bindView(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        private var ivProduct       : ImageView ?= null
        private var tvName          : TextView  ?= null
        private var tvPrice         : TextView  ?= null
        private var tvDescription   : TextView  ?= null

        init {
            ivProduct       = itemView.iv_product
            tvName          = itemView.tv_name
            tvPrice         = itemView.tv_price
            tvDescription   = itemView.tv_description
        }

        fun bindView(productEntity: ProductEntity){
            ivProduct?.setImageResource(productEntity.image)
            tvName?.text        = productEntity.name
            tvPrice?.text       = productEntity.price.toString()
            itemView.setOnClickListener { itemClickListener.onItemClicked(productEntity.idProduct) }

        }
    }

}