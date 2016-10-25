package colin.com.adapterwrapper;

import android.support.v7.widget.RecyclerView;

/**
 * 通过 mInnerAdapter.registerAdapterDataObserver(InnerAdapterObserver dataObserver);
 * 使得WrapperAdapter可以观察到内部真实数据源 InnerAdapter的变化
 *
 * Created by pengguolin on 2016/10/9.
 */

public class InnerAdapterObserver extends RecyclerView.AdapterDataObserver {
    private RecyclerView.Adapter mWrapperAdapter;

    /**使得WrapperAdapter可以观察到内部真实数据源 InnerAdapter的变化*/
    public InnerAdapterObserver(RecyclerView.Adapter adapterWrapper) {
        super();
        this.mWrapperAdapter = adapterWrapper;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        mWrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);

    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        mWrapperAdapter.notifyItemRangeRemoved(fromPosition, itemCount);
    }
}
