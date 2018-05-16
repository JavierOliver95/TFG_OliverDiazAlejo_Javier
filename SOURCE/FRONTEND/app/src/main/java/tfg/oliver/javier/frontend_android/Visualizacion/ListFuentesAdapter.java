package tfg.oliver.javier.frontend_android.Visualizacion;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import tfg.oliver.javier.frontend_android.R;

public class ListFuentesAdapter extends BaseAdapter{
    private final Context context;
    private final Bitmap[] values;

    public ListFuentesAdapter(Context context, Bitmap[] values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = inflater.inflate(R.layout.fuente_lista, null);
        }

        ImageView image = convertView.findViewById(R.id.imageView7);
        image.setImageBitmap(values[position]);

        return convertView;
    }
}
