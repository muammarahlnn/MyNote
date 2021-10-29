package com.ardnn.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ardnn.mynote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnNew -> {
                newFile()
            }
            R.id.btnOpen -> {
                showList()
            }
            R.id.btnSave -> {
                saveFile()
            }
        }
    }

    private fun newFile() {
        binding.etTitle.setText("")
        binding.etTextField.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val items = fileList()
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Choose file you want to open")
            setItems(items) { _, item ->
                loadData(items[item].toString())
            }
        }

        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val file = FileHelper.readFromFile(this, title)
        binding.etTitle.setText(file.filename)
        binding.etTextField.setText(file.data)
        Toast.makeText(this,"Loading ${file.filename} data", Toast.LENGTH_SHORT).show()
    }
    
    private fun saveFile() {
        when {
            binding.etTitle.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please fill the title field", Toast.LENGTH_SHORT).show()
            }
            binding.etTextField.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please fill the content", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val title = binding.etTitle.text.toString()
                val text = binding.etTextField.text.toString()

                val file = FileModel()
                file.filename = title
                file.data = text

                FileHelper.writeToFile(this, file)
                Toast.makeText(this, "Saving ${file.filename} file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}