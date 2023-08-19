package com.example.lab4
/*--------------------------------------------------------
* UNIVERSIDAD DEL VALLE DE GUATEMALA
* FACULTAD DE INGENIERÍA
* DEPARTAMENTO DE CIENCIA DE LA COMPUTACIÓN
* Programación de Plataformas Móviles
* Modificado por: Michelle Mejía 22596
* --------------------------------------------------------*/

//Imports necesarios
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lab4.ui.theme.Lab4Theme

//Clase main
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Llamamos método mainScreen
                    mainScreen()
                }
            }
        }
    }
}

//mainScreen: manejo de textFields, botón y lógica LazyColumn
@OptIn(ExperimentalMaterial3Api ::class)
@Composable
fun mainScreen(modifier: Modifier = Modifier) {
    Column(
        //Diseño
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),

        modifier = Modifier.fillMaxSize()

    ) {
        Text(text = " ", modifier = modifier)
        Text(text = "¡Bienvenido!", modifier = modifier)

        //Declaración de variables
        var comida by rememberSaveable { mutableStateOf("") }
        var imagen by rememberSaveable { mutableStateOf("") }
        var recipes by rememberSaveable { mutableStateOf(emptyList<Recipe>()) }

        //Inputs
        TextField(
            value = comida,
            onValueChange = { comida = it },
            label = { Text("Receta") }
        )

        TextField(
            value = imagen,
            onValueChange = { imagen = it },
            label = { Text("URL") }
        )

        //Manejo del evento (OnClick)
        Button(onClick = {
            if (comida.isNotEmpty() && imagen.isNotEmpty()) {
                val newRecipe = Recipe(comida, imagen)

                val recipeExists = recipes.any { it.name == newRecipe.name }

                if (!recipeExists) {
                    recipes = recipes + newRecipe
                }

                comida = ""
                imagen = ""
            }

        }) {
            Text(text = "Ingresar al recetario")
        }

        LazyColumn(
            modifier = modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Recetas ingresadas:",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            //Recorremos lista y mandamos parametro para eliminar elementos
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe) {
                    recipes = recipes.filterNot { it == recipe }
                }
            }
        }
    }
}

//Función para dibujar cards
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipe, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onDelete


    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = recipe.name)
        }
    }
}


//Preview para Design
@Preview(showBackground = true)
@Composable
fun mainPreview() {
    Lab4Theme {

            mainScreen()

    }
}
