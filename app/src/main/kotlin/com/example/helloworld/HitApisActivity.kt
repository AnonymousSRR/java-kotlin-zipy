package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class HitApisActivity : BaseActivity() {
    
    private lateinit var backButton: MaterialButton
    private lateinit var statusTextView: TextView
    private lateinit var dioGetSuccessButton: MaterialButton
    private lateinit var dioGetFailButton: MaterialButton
    private lateinit var dioPostSuccessButton: MaterialButton
    private lateinit var dioPostFailButton: MaterialButton
    private lateinit var httpGetSuccessButton: MaterialButton
    private lateinit var httpGetFailButton: MaterialButton
    private lateinit var httpPostSuccessButton: MaterialButton
    private lateinit var httpPostFailButton: MaterialButton
    private lateinit var graphqlGetSuccessButton: MaterialButton
    private lateinit var graphqlGetFailButton: MaterialButton
    private lateinit var graphqlPostSuccessButton: MaterialButton
    private lateinit var graphqlPostFailButton: MaterialButton
    private lateinit var multipartGetSuccessButton: MaterialButton
    private lateinit var multipartGetFailButton: MaterialButton
    private lateinit var multipartPostSuccessButton: MaterialButton
    private lateinit var multipartPostFailButton: MaterialButton
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Coroutine scope for async operations
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_hit_apis
    }
    
    override fun initializeActivity() {
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        backButton = findViewById(R.id.backButton)
        statusTextView = findViewById(R.id.statusTextView)
        
        dioGetSuccessButton = findViewById(R.id.dioGetSuccessButton)
        dioGetFailButton = findViewById(R.id.dioGetFailButton)
        dioPostSuccessButton = findViewById(R.id.dioPostSuccessButton)
        dioPostFailButton = findViewById(R.id.dioPostFailButton)
        httpGetSuccessButton = findViewById(R.id.httpGetSuccessButton)
        httpGetFailButton = findViewById(R.id.httpGetFailButton)
        httpPostSuccessButton = findViewById(R.id.httpPostSuccessButton)
        httpPostFailButton = findViewById(R.id.httpPostFailButton)
        graphqlGetSuccessButton = findViewById(R.id.graphqlGetSuccessButton)
        graphqlGetFailButton = findViewById(R.id.graphqlGetFailButton)
        graphqlPostSuccessButton = findViewById(R.id.graphqlPostSuccessButton)
        graphqlPostFailButton = findViewById(R.id.graphqlPostFailButton)
        multipartGetSuccessButton = findViewById(R.id.multipartGetSuccessButton)
        multipartGetFailButton = findViewById(R.id.multipartGetFailButton)
        multipartPostSuccessButton = findViewById(R.id.multipartPostSuccessButton)
        multipartPostFailButton = findViewById(R.id.multipartPostFailButton)
    }
    
    private fun setupClickListeners() {
        // Back button
        backButton.setOnClickListener {
            finish()
        }
        
        // Dio Section Buttons
        dioGetSuccessButton.setOnClickListener {
            makeDioGetSuccessCall()
        }
        
        dioGetFailButton.setOnClickListener {
            makeDioGetFailCall()
        }
        
        dioPostSuccessButton.setOnClickListener {
            makeDioPostSuccessCall()
        }
        
        dioPostFailButton.setOnClickListener {
            makeDioPostFailCall()
        }
        
        // HTTP Section Buttons
        httpGetSuccessButton.setOnClickListener {
            makeHttpGetSuccessCall()
        }
        
        httpGetFailButton.setOnClickListener {
            makeHttpGetFailCall()
        }
        
        httpPostSuccessButton.setOnClickListener {
            makeHttpPostSuccessCall()
        }
        
        httpPostFailButton.setOnClickListener {
            makeHttpPostFailCall()
        }
        
        // GraphQL Section Buttons
        graphqlGetSuccessButton.setOnClickListener {
            makeGraphQLGetSuccessCall()
        }
        
        graphqlGetFailButton.setOnClickListener {
            makeGraphQLGetFailCall()
        }
        
        graphqlPostSuccessButton.setOnClickListener {
            makeGraphQLPostSuccessCall()
        }
        
        graphqlPostFailButton.setOnClickListener {
            makeGraphQLPostFailCall()
        }
        
        // Multipart Form Section Buttons
        multipartGetSuccessButton.setOnClickListener {
            makeMultipartGetSuccessCall()
        }
        
        multipartGetFailButton.setOnClickListener {
            makeMultipartGetFailCall()
        }
        
        multipartPostSuccessButton.setOnClickListener {
            makeMultipartPostSuccessCall()
        }
        
        multipartPostFailButton.setOnClickListener {
            makeMultipartPostFailCall()
        }
    }
    
    // Dio API Calls
    private fun makeDioGetSuccessCall() {
        updateStatus("Making Dio GET request...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/posts/1")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    updateStatus("✅ Dio GET Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Dio GET Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ Dio GET Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Dio GET Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ Dio GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Dio GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeDioGetFailCall() {
        updateStatus("Making Dio GET request to invalid URL...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://invalid-url-that-will-fail.com/api/test")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ Dio GET Failed: Expected failure")
                Toast.makeText(this@HitApisActivity, "Dio GET Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ Dio GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Dio GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeDioPostSuccessCall() {
        updateStatus("Making Dio POST request...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("title", "Test Post")
                    put("body", "This is a test post from Hit APIs")
                    put("userId", 1)
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/posts")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    updateStatus("✅ Dio POST Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Dio POST Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ Dio POST Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Dio POST Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ Dio POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Dio POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeDioPostFailCall() {
        updateStatus("Making Dio POST request to invalid endpoint...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("invalid", "data")
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/invalid-endpoint")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ Dio POST Failed: ${response.code}")
                Toast.makeText(this@HitApisActivity, "Dio POST Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ Dio POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Dio POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // HTTP API Calls
    private fun makeHttpGetSuccessCall() {
        updateStatus("Making HTTP GET request...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/get")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ HTTP GET Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "HTTP GET Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ HTTP GET Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "HTTP GET Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ HTTP GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "HTTP GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeHttpGetFailCall() {
        updateStatus("Making HTTP GET request to invalid URL...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/status/404")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ HTTP GET Failed: ${response.code}")
                Toast.makeText(this@HitApisActivity, "HTTP GET Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ HTTP GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "HTTP GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeHttpPostSuccessCall() {
        updateStatus("Making HTTP POST request...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("name", "Test User")
                    put("email", "test@example.com")
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/post")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ HTTP POST Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "HTTP POST Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ HTTP POST Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "HTTP POST Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ HTTP POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "HTTP POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeHttpPostFailCall() {
        updateStatus("Making HTTP POST request to invalid endpoint...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("invalid", "data")
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/status/500")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ HTTP POST Failed: ${response.code}")
                Toast.makeText(this@HitApisActivity, "HTTP POST Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ HTTP POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "HTTP POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // GraphQL API Calls
    private fun makeGraphQLGetSuccessCall() {
        updateStatus("Making GraphQL GET request...")
        coroutineScope.launch {
            try {
                val query = "query { user(id: 1) { id name email } }"
                val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://graphql.org/swapi-graphql/?query=$encodedQuery")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ GraphQL GET Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "GraphQL GET Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ GraphQL GET Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "GraphQL GET Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ GraphQL GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "GraphQL GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeGraphQLGetFailCall() {
        updateStatus("Making GraphQL GET request with invalid query...")
        coroutineScope.launch {
            try {
                val query = "query { invalidField { id } }"
                val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://graphql.org/swapi-graphql/?query=$encodedQuery")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ GraphQL GET Failed: Invalid query")
                Toast.makeText(this@HitApisActivity, "GraphQL GET Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ GraphQL GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "GraphQL GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeGraphQLPostSuccessCall() {
        updateStatus("Making GraphQL POST request...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("query", "query { __schema { types { name } } }")
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://graphql.org/swapi-graphql/")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ GraphQL POST Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "GraphQL POST Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ GraphQL POST Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "GraphQL POST Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ GraphQL POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "GraphQL POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeGraphQLPostFailCall() {
        updateStatus("Making GraphQL POST request with invalid mutation...")
        coroutineScope.launch {
            try {
                val jsonBody = JSONObject().apply {
                    put("query", "mutation { invalidMutation { id } }")
                }
                
                val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://graphql.org/swapi-graphql/")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ GraphQL POST Failed: Invalid mutation")
                Toast.makeText(this@HitApisActivity, "GraphQL POST Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ GraphQL POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "GraphQL POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // Multipart Form API Calls
    private fun makeMultipartGetSuccessCall() {
        updateStatus("Making Multipart GET request...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/headers")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ Multipart GET Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Multipart GET Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ Multipart GET Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Multipart GET Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ Multipart GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Multipart GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeMultipartGetFailCall() {
        updateStatus("Making Multipart GET request to invalid endpoint...")
        coroutineScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/status/403")
                        .get()
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ Multipart GET Failed: ${response.code}")
                Toast.makeText(this@HitApisActivity, "Multipart GET Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ Multipart GET Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Multipart GET Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeMultipartPostSuccessCall() {
        updateStatus("Making Multipart POST request...")
        coroutineScope.launch {
            try {
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", "Test User")
                    .addFormDataPart("email", "test@example.com")
                    .addFormDataPart("file", "test.txt", 
                        "This is a test file content".toRequestBody("text/plain".toMediaType()))
                    .build()
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/post")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                if (response.isSuccessful) {
                    updateStatus("✅ Multipart POST Success: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Multipart POST Success!", Toast.LENGTH_SHORT).show()
                } else {
                    updateStatus("❌ Multipart POST Failed: ${response.code}")
                    Toast.makeText(this@HitApisActivity, "Multipart POST Failed!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                updateStatus("❌ Multipart POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Multipart POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun makeMultipartPostFailCall() {
        updateStatus("Making Multipart POST request to invalid endpoint...")
        coroutineScope.launch {
            try {
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("invalid", "data")
                    .build()
                
                val response = withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://httpbin.org/status/400")
                        .post(requestBody)
                        .build()
                    
                    client.newCall(request).execute()
                }
                
                updateStatus("❌ Multipart POST Failed: ${response.code}")
                Toast.makeText(this@HitApisActivity, "Multipart POST Failed as expected!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                updateStatus("❌ Multipart POST Error: ${e.message}")
                Toast.makeText(this@HitApisActivity, "Multipart POST Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun updateStatus(message: String) {
        runOnUiThread {
            statusTextView.text = message
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
} 