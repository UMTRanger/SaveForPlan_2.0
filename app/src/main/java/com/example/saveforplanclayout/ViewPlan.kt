package com.example.saveforplanclayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class ViewPlan :AppCompatActivity(){

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_plan)

        sharedPreferenceManager = SharedPreferenceManager(this)

        // Back Button to return to home page
        val backButton: Button = findViewById(R.id.btnBack)

        backButton.setOnClickListener {
            // Create an intent to go back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        displayPlansAndDays()
    }

    private fun displayPlansAndDays() {
        // LinearLayout to add plans dynamically
        val plansContainer: LinearLayout = findViewById(R.id.plansContainer)

        // Retrieve the list of plans from SharedPreferences
        val plansList = sharedPreferenceManager.getPlansList()

        // Iterate through the plans and add them to the layout
        for (plan in plansList) {
            val planView = createPlanView(plan)
            plansContainer.addView(planView)
        }
    }

    private fun createPlanView(plan: AddPlan.Plan): View {
        // Create a TextView to display plan details
        val planTextView = TextView(this)
        val daysNeeded = calculateDaysNeeded(plan.totalAmount)

        // Set text with plan name and days needed
        planTextView.text = "Plan: ${plan.name}, Days Needed: $daysNeeded"
        planTextView.textSize = 18f

        // Set additional properties if needed
        // ...

        return planTextView
    }

    private fun calculateDaysNeeded(totalAmount: Double): Int {
        // Assuming you have a function to get bank account amount
        val bankAccountAmount = getCurrentBankAccountAmount()

        // Assuming daily expenses are hardcoded for now
        val dailyExpenses = 10.0 // Replace with your logic to get daily expenses

        // Calculate days needed using the provided formula
        val daysNeeded = ((bankAccountAmount - (dailyExpenses * 365)) / totalAmount).toInt()

        return if (daysNeeded >= 0) daysNeeded else -1
    }

    private fun getCurrentBankAccountAmount(): Double {
        // Assuming you have a function to get the initial bank account amount
        val initialBankAccountAmount = getInitialBankAccountAmount()

        // Assuming you have a function to get the total cost of plans
        val totalCostOfPlans = getTotalCostOfPlans()

        // Calculate the remaining amount by subtracting the total cost of plans
        return initialBankAccountAmount - totalCostOfPlans
    }

    private fun getTotalCostOfPlans(): Double {
        // Retrieve the list of plans from SharedPreferences
        val plansList = sharedPreferenceManager.getPlansList()

        // Calculate the total cost of all plans
        return plansList.sumOf { it.totalAmount }
    }

    // Dummy function to get the initial bank account amount
    private fun getInitialBankAccountAmount(): Float {
        // Replace this with your logic to get the initial bank account amount
        return sharedPreferenceManager.getSavingsAmount()
    }
}