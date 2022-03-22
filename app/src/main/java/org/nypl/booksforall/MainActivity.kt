package org.nypl.booksforall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.newrelic.agent.android.NewRelic
import com.newrelic.agent.android.logging.AgentLog
import org.nypl.booksforall.databinding.ActivityMainBinding
import java.util.HashMap

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NewRelic.withApplicationToken("AA970ef9af78594c87083ddd85e35f8e34e299cbef-NRMA")
            .withLogLevel(AgentLog.AUDIT)
            .start(this.application)
        NewRelic.setUserId("malcolm")

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            breadcrumb.setOnClickListener {
                val attributes: MutableMap<String, Any> = HashMap<String, Any>()
                attributes["screen"] = "main"
                NewRelic.recordBreadcrumb("pressed breadcrumb button", attributes)
                Toast.makeText(applicationContext, "breadcrumb", Toast.LENGTH_SHORT).show()
            }

            crash.setOnClickListener {
                NewRelic.crashNow()
            }

            interaction.setOnClickListener {
                NewRelic.startInteraction("interaction button pressed")
                Toast.makeText(applicationContext, "interaction", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
