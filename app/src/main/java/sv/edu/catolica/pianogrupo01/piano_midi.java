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

public class piano_midi extends BaseActivity {

    private static final int SAMPLE_RATE = 44100;
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
                findViewById(R.id.button_re),
                findViewById(R.id.button_mi),
                findViewById(R.id.button_fa),
                findViewById(R.id.button_sol),
                findViewById(R.id.button_la),
                findViewById(R.id.button_si)
        };

        noteFrequencies = new double[]{
                261.63,  // DO
                293.66,  // RE
                329.63,  // MI
                349.23,  // FA
                392.00,  // SOL
                440.00,  // LA
                493.88   // SI
        };

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

    // Generar tono basado en la frecuencia
    public static byte[] generateTone(double frequency, int durationSecs) {
        int numSamples = durationSecs * SAMPLE_RATE;
        byte[] generatedSound = new byte[2 * numSamples]; // 16-bit PCM (2 bytes por muestra)

        for (int i = 0; i < numSamples; i++) {
            double sample = Math.sin(2 * Math.PI * i / (SAMPLE_RATE / frequency));
            short val = (short) (sample * 32767); // Convertir a 16-bit PCM
            generatedSound[2 * i] = (byte) (val & 0x00ff);
            generatedSound[2 * i + 1] = (byte) ((val & 0xff00) >>> 8);
        }

        return generatedSound;
    }

    // Reproducir el tono generado
    public void playTone(double frequency) {
        byte[] tone = generateTone(frequency, 3);  // Duración fija de 1 segundo (puedes ajustar)

        currentAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
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