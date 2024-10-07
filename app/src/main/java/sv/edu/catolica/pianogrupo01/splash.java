package sv.edu.catolica.pianogrupo01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    boolean Boton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Handler manejador = new Handler();

        manejador.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!Boton){
                    Intent ventana2 = new Intent(splash.this,MainActivity.class);
                    startActivity(ventana2);
                    finish();
                }

            }
        }, 3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Boton = true;
    }
}
