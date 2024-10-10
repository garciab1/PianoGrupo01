package sv.edu.catolica.pianogrupo01;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends BaseActivity {

    private Button[] noteButtons;
    private int[] noteSounds;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteButtons = new Button[]{
                findViewById(R.id.button_do),
                findViewById(R.id.button_re),
                findViewById(R.id.button_mi),
                findViewById(R.id.button_fa),
                findViewById(R.id.button_sol),
                findViewById(R.id.button_la),
                findViewById(R.id.button_si)
        };

        noteSounds = new int[]{
            R.raw.nota,  // DO
            R.raw.re,    // RE
            R.raw.mi,    // MI
            R.raw.fa,    // FA
            R.raw.sol,   // SOL
            R.raw.la,    // LA
            R.raw.si     // SI
        };

        String[] noteNames = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si"};

        for (int i = 0; i < noteButtons.length; i++) {
            final int noteIndex = i;
            noteButtons[i].setOnClickListener(view -> {
                reproducirSonido(noteIndex);
                NotaSonando(noteNames[noteIndex]);
            });
        }
    }

    private void NotaSonando(String nota){
        Toast.makeText(this, "Esta Sonando la tecla " + nota, Toast.LENGTH_SHORT).show();
    }

    private void reproducirSonido(int noteIndex) {
        if (mediaPlayer != null) {
            mediaPlayer.release();  // Liberar recursos de MediaPlayer antes de crear uno nuevo
        }

        mediaPlayer = MediaPlayer.create(this, noteSounds[noteIndex]);
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> mp.release());  // Liberar al finalizar
        } else {
            Toast.makeText(this, "Error al reproducir sonido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();  // Liberar recursos al destruir la actividad
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}