package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentMemeUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme() {
        progressbar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val url = "https://meme-api.com/gimme"

        val jsonObject = JsonObjectRequest(Request.Method.GET,url,null,
            {
                currentMemeUrl = it.getString("url")
                Glide.with(this).load(currentMemeUrl).listener(object: RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                }).into(memeImage)
            }
        ) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObject)
    }

        fun nextMeme(view: View) {
            loadMeme()
        }

        fun shareMeme(view: View) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type ="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this Cool meme $currentMemeUrl")
            val chooser = Intent.createChooser(intent,"Share Using")
            startActivity(chooser)
        }

}
