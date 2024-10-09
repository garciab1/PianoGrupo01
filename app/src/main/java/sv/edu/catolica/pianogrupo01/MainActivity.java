package sv.edu.catolica.pianogrupo01;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button about, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_piano_instrumentos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intrument), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()){
            case "Cambiar Tipo de Piano":
                showPianoOptionsDialog();
                return true;
            case "Acerca de nosotros":
                Intent intent = new Intent(this, About_us.class);
                startActivity(intent);
                return true;
            case "Salir":
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPianoOptionsDialog() {
        // Lista de opciones de pianos
        final String[] pianoOptions = {"Piano", "Piano de la jungla", "Piano de instrumentos", "Piano MIDI"};

        // Crear un AlertDialog con la lista de opciones
        new AlertDialog.Builder(this)
                .setTitle("Selecciona un tipo de piano")
                .setItems(pianoOptions, (dialog, which) -> {
                    // Aquí puedes manejar la acción dependiendo de la selección del usuario
                    switch (which) {
                        case 0:
                            // Acción para "Piano"
                            cambiarPiano("Piano");
                            break;
                        case 1:
                            // Acción para "Piano de la jungla"
                            cambiarPiano("Piano de la jungla");
                            break;
                        case 2:
                            // Acción para "Piano de instrumentos"
                            cambiarPiano("Piano de instrumentos");
                            break;
                        case 3:
                            // Acción para "Piano MIDI"
                            cambiarPiano("Piano MIDI");
                            break;
                    }
                })
                .show();
    }

    private void cambiarPiano(String pianoTipo) {
        Toast.makeText(this, "Piano cambiado a: " + pianoTipo, Toast.LENGTH_SHORT).show();
    }
}