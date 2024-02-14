package com.epn.mykotlinapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var botonOperacion : Button
    lateinit var editTextA: EditText
    lateinit var editTextB: EditText
    lateinit var TextViewResultado : TextView
    lateinit var checkBoxSuma : CheckBox
    lateinit var checkBoxResta : CheckBox
    lateinit var spinnerColor : Spinner
    lateinit var historialListView : ListView
    val historialCalculos = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextA = findViewById(R.id.editTextA)
        editTextB = findViewById(R.id.editTextB)
        TextViewResultado = findViewById(R.id.textView)
        checkBoxSuma = findViewById(R.id.checkBoxSuma)
        checkBoxResta = findViewById(R.id.checkBoxResta)
        botonOperacion = findViewById(R.id.buttonSuma)
        spinnerColor = findViewById(R.id.spinner)
        historialListView = findViewById(R.id.listView)

        botonOperacion.setOnClickListener(this)

        // Definir opciones del Spinner
        val opcionesColores = arrayOf("Negro", "Rojo", "Azul")

        // Configurar el adaptador para el ListView
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, historialCalculos)
        historialListView.adapter = adapter2

        // Acción al hacer clic en un elemento del ListView
        historialListView.setOnItemClickListener { _, _, position, _ ->
            // Mostrar el elemento seleccionado en un Toast (puedes cambiar esta acción según tus necesidades)
            Toast.makeText(this, "Cálculo seleccionado: ${historialCalculos[position]}", Toast.LENGTH_SHORT).show()
        }

        // Crear ArrayAdapter para el Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesColores)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar ArrayAdapter al Spinner
        spinnerColor.adapter = adapter

        // Configurar acción cuando se selecciona un elemento del Spinner
        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Obtener el color seleccionado
                val colorSeleccionado = parent.getItemAtPosition(position).toString()

                // Cambiar color del texto según la opción seleccionada en el Spinner
                when (colorSeleccionado) {
                    "Negro" -> TextViewResultado.setTextColor(Color.BLACK)
                    "Rojo" -> TextViewResultado.setTextColor(Color.RED)
                    "Azul" -> TextViewResultado.setTextColor(Color.BLUE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se necesita implementación aquí
            }
        }
    }

    override fun onClick(v: View?) {
        val numeroA = editTextA.text.toString().toDoubleOrNull() ?: 0.0
        val numeroB = editTextB.text.toString().toDoubleOrNull() ?: 0.0
        var resultado = 0.0
        val operacion = if (checkBoxSuma.isChecked) {
            resultado = numeroA + numeroB
            "Suma"
        } else if (checkBoxResta.isChecked) {
            resultado = numeroA - numeroB
            "Resta"
        } else {
            "Operación no seleccionada"
        }

        // Guardar el cálculo en el historial
        historialCalculos.add("$operacion: $numeroA ${if (checkBoxSuma.isChecked) "+" else "-"} $numeroB = $resultado")

        // Actualizar el adaptador del ListView
        (historialListView.adapter as ArrayAdapter<String>).notifyDataSetChanged()

        TextViewResultado.text = "Resultado es: $resultado"
    }

}