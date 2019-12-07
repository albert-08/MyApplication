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

public class EdicionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edUser, edPassword, edNombre, edApellidos;
    Button edit, cancel;
    int id=0;
    Usuario u;
    UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);
        edUser=(EditText)findViewById(R.id.edUser);
        edPassword=(EditText)findViewById(R.id.edPassword);
        edNombre=(EditText)findViewById(R.id.edNombre);
        edApellidos=(EditText)findViewById(R.id.edApellidos);
        edit=(Button)findViewById(R.id.edit);
        cancel=(Button)findViewById(R.id.cancel);
        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getInt("Id");
        dao=new UsuarioDAO(this);
        u=dao.getUserById(id);
        edUser.setText(u.getUsuario());
        edPassword.setText(u.getPassword());
        edNombre.setText(u.getNombre());
        edApellidos.setText(u.getApellidos());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                u.setUsuario(edUser.getText().toString());
                u.setPassword(edPassword.getText().toString());
                u.setNombre(edNombre.getText().toString());
                u.setApellidos(edApellidos.getText().toString());
                if (!u.isNull()) {
                    Toast.makeText(this, "ERROR: Campos vacios!", Toast.LENGTH_LONG).show();
                }
                else if (dao.updateUsuario(u)){
                    Toast.makeText(this, "Actualizacion correcta!", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(EdicionActivity.this, InicioActivity.class);
                    i2.putExtra("Id",u.getId());
                    startActivity(i2);
                    finish();
                }
                else {
                    Toast.makeText(this, "No se puede actualizar!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.cancel:
                Intent i = new Intent(EdicionActivity.this, InicioActivity.class);
                i.putExtra("Id",u.getId());
                startActivity(i);
                finish();
                break;
        }
    }
}
