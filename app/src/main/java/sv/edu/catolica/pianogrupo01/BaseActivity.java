package sv.edu.catolica.pianogrupo01;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()) {
            case "Cambiar Tipo de Piano":
                showPianoOptionsDialog();
                return true;
            case "Acerca de nosotros":
                Intent intent = new Intent(this, About_us.class);
                startActivity(intent);
                return true;
            case "Salir":
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showPianoOptionsDialog() {
        final String[] pianoOptions = {
                "Piano Tradicional",
                "Piano Infantil de la Selva",
                "Piano de instrumentos",
                "Piano MIDI"
        };

        new AlertDialog.Builder(this).setTitle("Selecciona un tipo de piano")
                .setItems(pianoOptions, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            cambiarPiano("Piano Tradicional");
                            Intent intent = new Intent( this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            cambiarPiano("Piano Infantil de la Selva");
                            Intent intent2 = new Intent( this, piano_jungla.class);
                            startActivity(intent2);
                            break;
                        case 2:
                            cambiarPiano("Piano de instrumentos");
                            Intent intent3 = new Intent( this, piano_instrumentos.class);
                            startActivity(intent3);
                            break;
                        case 3:
                            cambiarPiano("Piano MIDI");
                            Intent inten4 = new Intent( this, piano_midi.class);
                            startActivity(inten4);
                            break;
                    }
                }).show();
    }

    private void cambiarPiano(String pianoTipo) {
        Toast.makeText(this, "Piano cambiado a: " + pianoTipo, Toast.LENGTH_SHORT).show();
    }
}
