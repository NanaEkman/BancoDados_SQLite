package nana.ddm.bancodados_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DoisActivity_Musicas extends AppCompatActivity {

    // ##### ATRIBUTOS ##### //

    // atributos relativos aos objetos gráficos da interface
    private TextView lblArtista;
    private EditText txtTitulo;
    private Button btnAdiciona;
    private ListView listaMusicas;

    // cursor com os dados recuperados do BD
    private Cursor cursorMusicas;

    // adapter da lista de musicas
    private AdapterMusicas adapterMusicas;

    // referência para o banco de dados
    private SQLiteDatabase bd;

    // id do artista cujas musicas são exibidas nesta activity
    private int idArtista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dois_musicas);

        // ligando atributos com os IDs dos objetos gráficos
        lblArtista = findViewById( R.id.lblArtista );
        txtTitulo = findViewById( R.id.txtTitulo );
        btnAdiciona = findViewById( R.id.btnAdicionaMusica );
        listaMusicas = findViewById( R.id.listaMusicas);


        // configurando o escutador do botao adiciona...
        btnAdiciona.setOnClickListener( new EscutadorAdicionaMusicas() );


        // ##### banco de dados ##### //

        // abrindo ou criando o banco de dados
        bd = openOrCreateDatabase( "artistasmusicas", MODE_PRIVATE, null );

        // recuperando o id e o nome do artista do intent...

        // recuperando o objeto Intent que criou esta activity
        Intent i = getIntent();

        // recuperando o id do artista
        idArtista = i.getIntExtra("id",0);

        // recuperando o nome do artista
        lblArtista.setText( i.getStringExtra("nome") );

        // ##### configurando a lista ##### //

        // criando cursor com os dados vindos do banco
        String cmd = "SELECT _rowid_ _id, titulo FROM musicas WHERE idArtista = " + idArtista;
        cursorMusicas = bd.rawQuery( cmd, null );

        // criando o objeto adapter, passando o cursor
        adapterMusicas = new AdapterMusicas( this, cursorMusicas );

        // associando o adapter a lista de artistas
        listaMusicas.setAdapter(adapterMusicas);
    }

    // ##### classe interna, escutador do botão Adiciona ##### //
    private class EscutadorAdicionaMusicas implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // variável para pegar o titulo da musica
            String titulo;

            // pegando os dados na interface
            titulo = txtTitulo.getText().toString();

            // montando SQL para inserir dados
            String cmd = "INSERT INTO musicas (idArtista, titulo) VALUES (" + idArtista + ", '" + titulo + "')";

            // executando comando
            bd.execSQL( cmd );

            // limpando a interface
            txtTitulo.setText("");

            // renovando o cursor do adapter, já que temos novos dados no bd
            cmd = "SELECT _rowid_ _id, titulo FROM musicas WHERE idArtista = " + idArtista;
            cursorMusicas = bd.rawQuery( cmd, null );

            adapterMusicas.changeCursor(cursorMusicas);
        }
    }
}