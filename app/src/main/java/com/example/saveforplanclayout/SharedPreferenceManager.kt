package com.example.saveforplanclayout

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceManager(context: Context) {
    companion object {
        private val PREFS_NAME = "MyPrefs"
        private val SAVINGS_KEY = "savings_key"
        private val EXPENSES_KEY = "expenses_key"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSavingsAmount(amount: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(SAVINGS_KEY, amount)
        editor.apply()
    }

    fun getSavingsAmount(): Float {
        return sharedPreferences.getFloat(SAVINGS_KEY, 0.0f)
    }

    fun saveExpensesAmount(amount: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(EXPENSES_KEY, amount)
        editor.apply()
    }

    fun clearAll(context: Context) {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun savePlansList(plansList: List<AddPlan.Plan>) {
        // Use Gson to convert the plans list to a JSON string
        val gson = Gson()
        val plansJson = gson.toJson(plansList)

        // Save the JSON string to SharedPreferences
        sharedPreferences.edit().putString("plansList", plansJson).apply()
    }

    fun getPlansList(): List<AddPlan.Plan> {
        // Retrieve the JSON string from SharedPreferences
        val plansJson = sharedPreferences.getString("plansList", "")

        // Use Gson to convert the JSON string back to a list of plans
        val gson = Gson()
        return gson.fromJson(plansJson, object : TypeToken<List<AddPlan.Plan>>() {}.type)
    }
}