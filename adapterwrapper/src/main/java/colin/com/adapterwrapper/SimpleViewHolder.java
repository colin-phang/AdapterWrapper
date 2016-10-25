package colin.com.adapterwrapper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * SimpleViewHolder without anything;
 * Created by pengguolin on 2016/10/3.
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder {

    public SimpleViewHolder(View itemView) {
        super(itemView);
    }


    public static SimpleViewHolder createViewHolder(Context context, View itemView) {
        SimpleViewHolder holder = new SimpleViewHolder(itemView);
        return holder;
    }

    public static SimpleViewHolder createViewHolder(Context context,
                                                    ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        SimpleViewHolder holder = new SimpleViewHolder(itemView);
        return holder;
    }
}
