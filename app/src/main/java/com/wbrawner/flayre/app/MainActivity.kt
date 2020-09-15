package com.wbrawner.flayre.app

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.init((application as FlayreApplication).appComponent)
        viewModel.events.observe(this, { events ->
            if (events.isEmpty()) {
                helloWorld.text = "No events"
            } else {
                helloWorld.visibility = View.GONE
                eventsTable.visibility = View.VISIBLE
                events.asSequence()
                    .filter { !it.userAgent.contains("Googlebot") }
                    .groupBy { it.data }
                    .map {
                        Pair(it.key, it.value.size)
                    }
                    .sortedByDescending {
                        it.second
                    }
                    .forEach { pair ->
                        eventsTable.addView(TableRow(this@MainActivity).apply {
                            addView(TextView(this@MainActivity).apply {
                                text = pair.first
                            })
                            addView(TextView(this@MainActivity).apply {
                                text = pair.second.toString()
                            })
                        })
                    }
            }
        })
        viewModel.loadEvents()
    }
}
