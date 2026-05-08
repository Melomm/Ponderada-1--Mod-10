package carvalho.zanini.ponderada1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carvalho.zanini.ponderada1.ui.theme.Ponderada1Theme
import kotlin.random.Random

data class DiceOption(
    val label: String,
    val sides: Int,
    val drawableRes: Int
)

fun tamanhoFonteResultado(valor: Int): Int {
    return when (valor.toString().length) {
        1 -> 64
        2 -> 52
        else -> 42
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ponderada1Theme {
                LancadorDeDadosApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LancadorDeDadosApp() {
    val dados = listOf(
        DiceOption("D6", 6, R.drawable.d6_quadrado),
        DiceOption("D10", 10, R.drawable.d10_diamante),
        DiceOption("D20", 20, R.drawable.d20_hexagonal),
        DiceOption("D100", 100, R.drawable.d100_circulo)
    )

    var dadoSelecionado by remember { mutableStateOf(dados.first()) }
    var resultado by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Lançador de Dados",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Escolha o tipo de dado:")

        dados.forEach { dado ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = dadoSelecionado == dado,
                    onClick = { dadoSelecionado = dado }
                )
                Text(text = dado.label)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Faces do ${dadoSelecionado.label}: 1 a ${dadoSelecionado.sides}",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                resultado = Random.nextInt(1, dadoSelecionado.sides + 1)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Lançar dado")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (resultado == null) {
                "Clique no botão para lançar o dado"
            } else {
                "Resultado do ${dadoSelecionado.label}: $resultado"
            },
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = dadoSelecionado.drawableRes),
                contentDescription = "Imagem do ${dadoSelecionado.label}",
                modifier = Modifier.size(220.dp)
            )
            Text(
                text = resultado?.toString() ?: "?",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = tamanhoFonteResultado(resultado ?: 100).sp
            )
        }
    }
}