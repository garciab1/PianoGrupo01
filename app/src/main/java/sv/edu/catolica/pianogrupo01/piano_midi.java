package sv.edu.catolica.pianogrupo01;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class piano_midi extends BaseActivity {

    private static final int sampleRate = 44100;
    private Button[] noteButtons;
    private double[] noteFrequencies;
    private AudioTrack currentAudioTrack;
    private boolean isNotePlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_piano_midi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteButtons = new Button[]{
                findViewById(R.id.button_do),
                findViewById(R.id.button_do_s),
                findViewById(R.id.button_re),
                findViewById(R.id.button_re_s),
                findViewById(R.id.button_mi),
                findViewById(R.id.button_fa),
                findViewById(R.id.button_fa_s),
                findViewById(R.id.button_sol),
                findViewById(R.id.button_sol_s),
                findViewById(R.id.button_la),
                findViewById(R.id.button_la_s),
                findViewById(R.id.button_si)
        };

        noteFrequencies = new double[]{
                261.63,  // DO
                277.18,  // DO#
                293.66,  // RE
                311.13,  // RE#
                329.63,  // MI
                349.23,  // FA
                369.99,  // FA#
                392.00,  // SOL
                415.30,  // SOL#
                440.00,  // LA
                466.16,  // LA#
                493.88   // SI
        };

        String[] notas = {"Do", "DO#", "RE", "RE#", "MI", "FA", "FA#", "SOL", "SOL#", "LA", "LA#", "SI"};

        for (int i = 0; i < noteButtons.length; i++) {
            final int noteIndex = i;

            noteButtons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (!isNotePlaying){
                                isNotePlaying = true;
                                playTone(noteFrequencies[noteIndex]);
                                notasonando(notas[noteIndex]);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            // Cuando se suelta la tecla, detener el sonido
                            stopTone();
                            isNotePlaying = false;
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void notasonando (String nota){
        Toast.makeText(this, "Esta sonando: " + nota, Toast.LENGTH_SHORT).show();
    }

    // Generar tono basado en la frecuencia
    public static byte[] generateTone(double frequency, int durationSecs) {
        int numSamples = durationSecs * sampleRate;
        byte[] generatedSound = new byte[2 * numSamples];
        double envelopeFactor;

        for (int i = 0; i < numSamples; i++) {
            // Generación básica de onda senoidal
            double sample = Math.sin(2 * Math.PI * i / (sampleRate / frequency));

            envelopeFactor = Math.exp(-i / (double)(sampleRate / 4));
            sample *= envelopeFactor;

            short val = (short) (sample * 32767);
            generatedSound[2 * i] = (byte) (val & 0x00ff);
            generatedSound[2 * i + 1] = (byte) ((val & 0xff00) >>> 8);
        }

        return generatedSound;
    }

    // Reproducir el tono generado
    public void playTone(double frequency) {
        byte[] tone = generateTone(frequency, 1);

        currentAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, tone.length,
                AudioTrack.MODE_STATIC);

        currentAudioTrack.write(tone, 0, tone.length);
        currentAudioTrack.play();
    }

    public void stopTone() {
        if (currentAudioTrack != null) {
            currentAudioTrack.stop();
            currentAudioTrack.release();
            currentAudioTrack = null;
        }
    }
}