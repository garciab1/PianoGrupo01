package sv.edu.catolica.pianogrupo01;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class piano_instrumentos extends AppCompatActivity {

    private Button[] noteButtons;
    private ImageButton[] noteimageButtons;
    private int[] noteSounds;
    private MediaPlayer mediaPlayer;

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

        noteButtons = new Button[]{
                findViewById(R.id.button_dooinstru),
                findViewById(R.id.button_reeinstru),
                findViewById(R.id.button_miinstru),
                findViewById(R.id.button_fainstru),
                findViewById(R.id.button_solinstru),
                findViewById(R.id.button_lainstru),
                findViewById(R.id.button_siinstru)
        };

        noteimageButtons = new ImageButton[]{
                findViewById(R.id.button_dooinstru_image),
                findViewById(R.id.button_reeinstru_image),
                findViewById(R.id.button_miinstru_image),
                findViewById(R.id.button_fainstru_image),
                findViewById(R.id.button_solinstru_image),
                findViewById(R.id.button_lainstru_image),
                findViewById(R.id.button_siinstru_image)
        };

        noteSounds = new int[]{
                R.raw.flauta,  // DO
                R.raw.bateria,    // RE
                R.raw.arpa,    // MI
                R.raw.guitarra,    // FA
                R.raw.maracas,   // SOL
                R.raw.conga,    // LA
                R.raw.trompeta     // SI
        };

        for (int i = 0; i < noteButtons.length; i++){
            final int index = i;
            noteButtons[i].setOnClickListener(view -> reproducirSonido(index));
        }
        for (int i = 0; i < noteimageButtons.length; i++){
            final int index = i;
            noteimageButtons[i].setOnClickListener(view -> reproducirSonido(index));
        }
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