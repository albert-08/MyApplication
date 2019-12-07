package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.database.Usuario;
import com.example.myapplication.database.UsuarioDAO;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bienvenido;
    Button editar, eliminar, mostrar, salir;
    int id=0;
    Usuario u;
    UsuarioDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bienvenido = (TextView)findViewById(R.id.bienvenido);
        editar = (Button)findViewById(R.id.editar);
        eliminar = (Button)findViewById(R.id.eliminar);
        mostrar = (Button)findViewById(R.id.mostrar);
        salir = (Button)findViewById(R.id.salir);
        editar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        mostrar.setOnClickListener(this);
        salir.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getInt("Id");
        dao=new UsuarioDAO(this);
        u=dao.getUserById(id);
        bienvenido.setText(u.getNombre()+" "+u.getApellidos());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editar:
                Intent a = new Intent(InicioActivity.this, EdicionActivity.class);
                a.putExtra("Id", id);
                startActivity(a);
                break;

            case R.id.eliminar:
                AlertDialog.Builder b=new AlertDialog.Builder(this);
                b.setMessage("Seguro que quieres eliminar tu cuenta?");
                b.setCancelable(false);
                b.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(dao.deleteUsuario(id)){
                            Toast.makeText(InicioActivity.this, "Cuenta Eliminada!", Toast.LENGTH_LONG).show();
                            Intent d = new Intent(InicioActivity.this, MainActivity.class);
                            //d.putExtra("Id",u.getId());
                            startActivity(d);
                            finish();
                        }else
                            Toast.makeText(InicioActivity.this, "ERROR: No fue posible eliminar la cuenta!", Toast.LENGTH_LONG).show();
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                b.show();
                break;

            case R.id.mostrar:
                Intent c = new Intent(InicioActivity.this, MostrarActivity.class);
                startActivity(c);
                break;

            case R.id.salir:
                Intent i = new Intent(InicioActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
