package colin.com.adapterwrappersample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pengguolin on 2016/10/26.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<String> mDatas;


    public void addData(List<String> datas) {
        if (mDatas == null)
            setData(datas);
        else
            mDatas.addAll(datas);
    }

    public void setData(List<String> datas) {
        this.mDatas = datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;


        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
