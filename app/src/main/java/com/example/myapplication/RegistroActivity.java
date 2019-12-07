package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.Usuario;
import com.example.myapplication.database.UsuarioDAO;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText regUser, regPassword, nombre, apellidos;
    Button crear, canc;
    UsuarioDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        regUser = (EditText)findViewById(R.id.regUser);
        regPassword = (EditText)findViewById(R.id.regPassword);
        nombre = (EditText)findViewById(R.id.nombre);
        apellidos = (EditText)findViewById(R.id.apellidos);
        crear = (Button)findViewById(R.id.crear);
        canc = (Button)findViewById(R.id.canc);
        crear.setOnClickListener(this);
        canc.setOnClickListener(this);
        dao = new UsuarioDAO(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.crear:
                Usuario u = new Usuario();
                u.setUsuario(regUser.getText().toString());
                u.setPassword(regPassword.getText().toString());
                u.setNombre(nombre.getText().toString());
                u.setApellidos(apellidos.getText().toString());
                if (!u.isNull()) {
                    Toast.makeText(this, "ERROR: Campos vacios!", Toast.LENGTH_LONG).show();
                }
                else if (dao.insertUsuario(u)){
                    Toast.makeText(this, "Datos capturados correctamente!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(i2);
                    finish();
                }
                else {
                    Toast.makeText(this, "Usuario ya registrado!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.canc:
                Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }


    }
}
