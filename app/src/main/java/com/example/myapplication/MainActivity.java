
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;

import com.example.myapplication.database.Usuario;
import com.example.myapplication.database.UsuarioDAO;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText user, password;
    Button inicio, registro;
    UsuarioDAO dao;
    private Handler handler = new Handler();

    private Executor executor = new Executor() {
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        inicio = (Button)findViewById(R.id.inicio);
        registro = (Button)findViewById(R.id.registro);
        inicio.setOnClickListener(this);
        registro.setOnClickListener(this);
        dao = new UsuarioDAO(this);

        FragmentActivity activity = this;

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                    Log.d("Hola","Cancelado");
                    Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(getApplicationContext(), MostrarActivity.class);
                startActivity(intent);
                //TODO: Called when a biometric is recognized.
                Log.d("Hola","Datos reconocidos");
                Toast.makeText(getApplicationContext(),"Datos reconocidos",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
                Log.d("Hola","Datos no reconocidos");
                Toast.makeText(getApplicationContext(),"Datos no reconocidos",Toast.LENGTH_LONG).show();

            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Validacion con datos biometricos")
                .setSubtitle("Autenticacion en proceso.")
                .setDescription("Muestre sus datos biometricos para poder continuar")
                .setNegativeButtonText("Cancelar")
                .build();

        findViewById(R.id.touchId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.inicio:
                String u=user.getText().toString();
                String p=password.getText().toString();
                if(u.equals("")&&p.equals(""))
                    Toast.makeText(this, "ERROR:Campos vacios!",Toast.LENGTH_LONG).show();
                else if(dao.login(u,p)==1){
                    Usuario us=dao.getUsuario(u,p);
                    Toast.makeText(this, "Datos correctos!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(MainActivity.this, InicioActivity.class);
                    i2.putExtra("Id", us.getId());
                    startActivity(i2);
                    finish();
                }else
                    Toast.makeText(this, "Datos incorrectos!", Toast.LENGTH_LONG).show();
                break;

            case R.id.registro:
                Intent i = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i);
                break;
        }
    }
}
