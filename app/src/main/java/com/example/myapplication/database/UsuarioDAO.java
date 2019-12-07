package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UsuarioDAO {

    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    String db = "Usuarios";
    String tabla = "CREATE TABLE IF NOT EXISTS usuario(id integer primary key autoincrement, usuario text, pass text, nombre text, apellidos text)";

    public UsuarioDAO(Context c) {
        this.c = c;
        sql = c.openOrCreateDatabase(db, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u=new Usuario();
    }

    public boolean insertUsuario(Usuario u) {
        if(buscar(u.getUsuario())==0){
            ContentValues cv = new ContentValues();
            cv.put("usuario", u.getUsuario());
            cv.put("pass", u.getPassword());
            cv.put("nombre", u.getNombre());
            cv.put("apellidos", u.getApellidos());
            return (sql.insert("usuario",null,cv)>0);
        }
        else
            return false;
    }

    public boolean updateUsuario(Usuario u) {
            ContentValues cv = new ContentValues();
            cv.put("usuario", u.getUsuario());
            cv.put("pass", u.getPassword());
            cv.put("nombre", u.getNombre());
            cv.put("apellidos", u.getApellidos());
            return (sql.update("usuario",cv,"id="+u.getId(),null)>0);
    }

    public boolean deleteUsuario(int id){
        return (sql.delete("usuario", "id=" + id, null)>0);
    }

    public int buscar(String u) {
        int x=0;
        lista = selectUsuarios();
        for (Usuario us:lista) {
            if(us.getUsuario().equals(u)){
                x++;
            }
        }
        return x;
    }

    public ArrayList<Usuario> selectUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista.clear();
        Cursor cr = sql.rawQuery("SELECT * FROM usuario",null);
        if(cr!=null&&cr.moveToFirst()){
            do{
                Usuario u = new Usuario();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setPassword(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setApellidos(cr.getString(4));
                lista.add(u);
            }while(cr.moveToNext());
        }
        return lista;
    }

    public int login(String u, String p) {
        int i=0;
        Cursor cr = sql.rawQuery("SELECT * FROM usuario",null);
        if(cr!=null&&cr.moveToFirst()){
            do{
                if(cr.getString(1).equals(u)&&cr.getString(2).equals(p))
                    i++;
            }while(cr.moveToNext());
        }
        return i;
    }

    public Usuario getUsuario(String u, String p){
        lista=selectUsuarios();
        for (Usuario us:lista) {
            if(us.getUsuario().equals(u)&&us.getPassword().equals(p))
                return us;
        }
        return null;
    }

    public Usuario getUserById(int id){
        lista=selectUsuarios();
        for (Usuario us:lista) {
            if(us.getId()==id)
                return us;
        }
        return null;
    }
}
