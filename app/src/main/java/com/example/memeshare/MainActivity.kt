package com.example.memeshare

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var currentimageurl:String="https://meme-api.herokuapp.com/gimme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        loadMeme()
    
    }
private fun loadMeme(){
    progressbar.visibility=View.VISIBLE
//https://meme-api.herokuapp.com/gimme
// Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(this)
    val url =currentimageurl

// Request a json string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
        Response.Listener { response ->
var url=response.getString("url")

            Glide.with(this)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                 progressbar.visibility=View.VISIBLE

                    return false

                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                progressbar.visibility=View.GONE

                    return false
                }

            } )
                .into(memeimageView)
        },
        Response.ErrorListener { error ->
            // TODO: Handle error
        })
// Add the request to the RequestQueue.

    queue.add(jsonObjectRequest)
}
    fun ShareMeme(view: View) {
val intent= Intent(Intent.ACTION_SEND)
        val drawable: Drawable = memeimageView.drawable
        var bmp: Bitmap? = null
        bmp = (memeimageView.drawable as BitmapDrawable).bitmap
//        val bmpUri: Uri = bmp
val image =memeimageView.drawable
        intent.type = "image/*";
        intent.putExtra(Intent.EXTRA_STREAM,"reddit ki api se authaya hua meme share kar raha hu  $image")
val chooser= Intent.createChooser(intent,"konse app se share karna pasand karega.......")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {

        loadMeme()
    }
}
