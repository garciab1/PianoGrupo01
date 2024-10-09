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

public class piano_jungla extends BaseActivity {

    private Button[] noteButtons;
    private ImageButton[] noteimageButtons;
    private int[] noteSounds;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_piano_jungla);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.jungla), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteButtons = new Button[]{
                findViewById(R.id.button_doo),
                findViewById(R.id.button_ree),
                findViewById(R.id.button_mii),
                findViewById(R.id.button_faa),
                findViewById(R.id.button_soll),
                findViewById(R.id.button_laa),
                findViewById(R.id.button_sii)
        };

        noteimageButtons = new ImageButton[]{
                findViewById(R.id.button_doo_image),
                findViewById(R.id.button_ree_image),
                findViewById(R.id.button_mii_image),
                findViewById(R.id.button_faa_image),
                findViewById(R.id.button_sol_image),
                findViewById(R.id.button_laa_image),
                findViewById(R.id.button_sii_image)
        };

        noteSounds = new int[]{
                R.raw.dog,  // DO
                R.raw.rhino,    // RE
                R.raw.chicken,    // MI
                R.raw.pig,    // FA
                R.raw.sheep,   // SOL
                R.raw.crocodile,    // LA
                R.raw.monkey     // SI
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