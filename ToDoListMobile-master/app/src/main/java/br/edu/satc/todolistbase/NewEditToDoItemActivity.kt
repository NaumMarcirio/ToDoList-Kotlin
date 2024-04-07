package br.edu.satc.todolistbase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import br.edu.satc.todolistbase.roomdatabase.AppDatabase
import br.edu.satc.todolistbase.roomdatabase.ToDoItem

private lateinit var db: AppDatabase

class NewEditToDoItemActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDescricao: EditText
    private lateinit var btSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_edit_to_do_item)

        initDatabase()

        etTitle = findViewById(R.id.et_titulo)
        etDescricao = findViewById(R.id.et_descricao)
        btSalvar = findViewById(R.id.bt_salvar)


        btSalvar.setOnClickListener {
            save()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //Criar o obj do To Do Item
    //Salvar no DB
    //Dar retorno
    //Fechar a tela
    private fun save(){
        val toDoItem = ToDoItem(
            null,
            etDescricao.text.toString(),
            etTitle.text.toString(),
            false
        )
        //Salva no DB
        db.toDoItemDao().insertAll(toDoItem)

        Toast.makeText(this, "Salvou chefia!", Toast.LENGTH_SHORT).show()

        //Finaliza a tela
        finish()
    }

    private fun initDatabase() {
        // Inicializar nosso banco de dados Android Room Persistence com SQLITE
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-todolist"
        )
            .allowMainThreadQueries()
            .build()

    }

}