package com.ruchanokal.carinstructions.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.ruchanokal.carinstructions.R
import com.ruchanokal.carinstructions.model.PagerModel

class ViewPagerAdapter(context: Context, var item : List<PagerModel>) :
    PagerAdapter() {

    var  mContext : Context = context

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutScreen = inflater.inflate(R.layout.layout_viewpager,null)

        val talimatText : TextView = layoutScreen.findViewById(R.id.talimatText)
        val talimatImageView : ImageView = layoutScreen.findViewById(R.id.talimatImageView)


        talimatText.text = item.get(position).talimatText


        val circularProgressDrawable = CircularProgressDrawable(mContext)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()


        if (item.get(position).talimatImage.isNullOrEmpty())
            talimatImageView.visibility = View.GONE
        else {
            Glide.with(mContext)
                .load(item.get(position).talimatImage)
                .placeholder(circularProgressDrawable)
                .into(talimatImageView)
        }


        container.addView(layoutScreen)

        return layoutScreen

    }


    override fun getCount(): Int {
        return  item.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View?)
    }
}