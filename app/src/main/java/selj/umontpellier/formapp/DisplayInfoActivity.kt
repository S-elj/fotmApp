package selj.umontpellier.formapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DisplayInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nom = intent.getStringExtra("NOM") ?: ""
        val prenom = intent.getStringExtra("PRENOM") ?: ""
        val telephone = intent.getStringExtra("TELEPHONE") ?: ""
        val domaineActivite = intent.getStringExtra("DOMAINE_ACTIVITE") ?: ""

        setContent {
            MaterialTheme {
                DisplayInfoScreen(nom, prenom, telephone, domaineActivite)
            }
        }
    }
}

@Composable
fun DisplayInfoScreen(nom: String, prenom: String, telephone: String, domaineActivite: String) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoText(label = "Nom", value = nom)
            InfoText(label = "Prénom", value = prenom)
            InfoText(label = "Téléphone", value = telephone)
            InfoText(label = "Domaine d'Activité", value = domaineActivite)
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}
