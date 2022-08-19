package nana.ddm.bancodados_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdapterMusicas extends CursorAdapter {

    public AdapterMusicas(Context context, Cursor c) {
        super(context, c, 0);
    }

    // O método newView apenas "infla" o xml do item da lista e retorna.
    // Não há preenchimento de dados neste momento.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemDaLista = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return itemDaLista;
    }

    // O método bindView pega os dados lidos do bd no cursor, e coloca no item da lista
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView text1 = view.findViewById( android.R.id.text1 );

        // extrair as informações da posição atual do cursor

        String titulo = cursor.getString( cursor.getColumnIndex("titulo") );

        text1.setText( titulo );
    }
}
