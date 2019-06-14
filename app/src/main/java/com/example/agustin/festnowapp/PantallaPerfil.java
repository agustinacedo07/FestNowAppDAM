package com.example.agustin.festnowapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PantallaPerfil extends AppCompatActivity {
    private Button btnPantallaPral;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);


        btnPantallaPral = (Button)findViewById(R.id.btnPantallaPral);


        btnPantallaPral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaPral = new Intent(getApplicationContext(),PantallaPrincipal.class);
                startActivity(pantallaPral);
            }
        });
    }
}
