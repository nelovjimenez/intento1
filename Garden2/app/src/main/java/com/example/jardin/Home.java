package com.example.jardin;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.Locale;*/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {

    ArrayList<Municipio> listaDeMunicipio = new ArrayList<>();

    RecyclerView listadoGrafico;
FirebaseFirestore baseDatos=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listadoGrafico=findViewById(R.id.listado);
        listadoGrafico.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        crearLista();

    }

    //ENCARGADA DE CAMBIAR LA CONFIGURACION DE IDIOMA DEL TELEFONO
    public void cambiarIdioma(String lenguaje){

        Locale idioma=new Locale(lenguaje);
        Locale.setDefault(idioma);



    Configuration configuraciontelefono=getResources().getConfiguration();
    configuraciontelefono.locale=idioma;
    getBaseContext().getResources().updateConfiguration(configuraciontelefono,getBaseContext().getResources().getDisplayMetrics());
    }

    //METODO ENCARGADO DE PINTAR EL MENU DE OPCIONES

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //METODO PARA CONTROLAR LA ACCION (LO QUE QUIERO HACER CON CADA ELEMENTO DEL MENU)

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){

            case(R.id.opcion1):
                Intent intent1=new Intent(Home.this,Acercade.class);
                startActivity(intent1);
                break;

            case(R.id.opcion2):
                cambiarIdioma("en");
                Intent intent2=new Intent(Home.this,Home.class);
                startActivity(intent2);
                break;

            case(R.id.opcion3):
                cambiarIdioma("es");
                Intent intent3=new Intent(Home.this,Home.class);
                startActivity(intent3);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void crearLista(){


        baseDatos.collection("pueblo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String titulo=document.get("titulo").toString();
                                String descripcion=document.get("descripcion").toString();
                                String fotoUrl=document.get("fotoUrl").toString();

                                listaDeMunicipio.add(new Municipio(titulo,descripcion,fotoUrl));

                        }
                        ListaAdaptador adaptador= new ListaAdaptador(listaDeMunicipio) {

                        };
                        listadoGrafico.setAdapter(adaptador);

                    }else{
                            Toast.makeText(Home.this, "ERROR EN LA CONSULTA", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}