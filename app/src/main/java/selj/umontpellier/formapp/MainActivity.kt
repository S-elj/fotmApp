package selj.umontpellier.formapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

import selj.umontpellier.formapp.ui.theme.FormAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    FormAppTheme {
        var nom by remember { mutableStateOf("") }
        var prenom by remember { mutableStateOf("") }
        var telephone by remember { mutableStateOf("") }
        var domaineActivite by remember { mutableStateOf("") }
        var showConfirmationDialog by remember { mutableStateOf(false) }

        val context = LocalContext.current


        fun showDisplayInfoActivity(
            context: Context,
            nom: String,
            prenom: String,
            telephone: String,
            domaineActivite: String
        ) {
            val intent = Intent(context, DisplayInfoActivity::class.java).apply {
                putExtra("NOM", nom)
                putExtra("PRENOM", prenom)
                putExtra("TELEPHONE", telephone)
                putExtra("DOMAINE_ACTIVITE", domaineActivite)
            }
            context.startActivity(intent)
        }


        Column(modifier = Modifier.padding(16.dp)) {
            NomTextField(nom, onValueChange = { nom = it; println("appel a nom()") })
            PrenomTextField(prenom, onValueChange = { prenom = it })
            TelephoneTextField(telephone, onValueChange = { telephone = it })
            DomaineActiviteSelection(
                domainesActivite = listOf(
                    "Agroalimentaire",
                    "Banque / Assurance",
                    "Bois / Papier / Carton / Imprimerie",
                    "BTP / Matériaux de construction",
                    "Chimie / Parachimie",
                    "Commerce / Négoce / Distribution",
                    "Édition / Communication / Multimédia",
                    "Électronique / Électricité",
                    "Études et conseils",
                    "Industrie pharmaceutique",
                    "Informatique / Télécoms"
                ),
                selectedDomaine = domaineActivite,
                onDomaineSelected = { domaineActivite = it }
            )
            ConfirmButton(onClick = { showConfirmationDialog = true })

            if (showConfirmationDialog) {
                ConfirmationDialog(
                    onConfirm = {
                        showDisplayInfoActivity(context, nom, prenom, telephone, domaineActivite)
                        showConfirmationDialog = false
                    },
                    onDismiss = { showConfirmationDialog = false }
                )
            }
        }
    }
}

@Composable
fun NomTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Nom") }
    )
}

@Composable
fun PrenomTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Prenom") }
    )
}

@Composable
fun TelephoneTextField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Téléphone") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
    )
}


@Composable
fun ConfirmButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Confirmer")
    }
}

@Composable
fun DomaineActiviteSelection(
    domainesActivite: List<String>,
    selectedDomaine: String,
    onDomaineSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedDomaine,
            onValueChange = { /* Lecture seule */ },
            readOnly = true,
            enabled = false,
            label = { Text("Domaine d'Activité") },
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "Menu déroulant"
                )
            },
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            domainesActivite.forEach { domaine ->
                DropdownMenuItem(
                    text = { Text(domaine) },
                    onClick = {
                        onDomaineSelected(domaine)
                        expanded = false

                    },
                )
            }
        }
    }
}


@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirmer", Modifier)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        },
        title = { Text("Confirmation") },
        text = { Text("Voulez-vous confirmer les informations ?") }
    )
}
