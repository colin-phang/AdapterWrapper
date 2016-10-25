package colin.com.adapterwrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pengguolin on 2016/10/3.
 */
public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEADER = 100000;
    private static final int ITEM_TYPE_FOOTER = 200000;
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;
    private RecyclerView.AdapterDataObserver mDataObserver;

    public WrapperAdapter(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
        mDataObserver = new InnerAdapterObserver(this);
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {
            SimpleViewHolder holder;
            if (mEmptyView != null) {
                holder = SimpleViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = SimpleViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        } else if (mHeaderViews.get(viewType) != null) {
            SimpleViewHolder holder = SimpleViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));

            return holder;

        } else if (mFootViews.get(viewType) != null) {
            SimpleViewHolder holder = SimpleViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        } else if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterPosition(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        if (isHeaderPosition(position)) {
            return;
        }
        if (isFooterPosition(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? 1 : getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        //GridLayoutManager时的头部需要设置正确的SpanSize
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (isEmpty()) {
                    return layoutManager.getSpanCount();
                } else if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            //StaggeredGridLayoutManager时的头部需要设置正确的SpanSize
            WrapperUtils.setFullSpan(holder);
        }
    }

    private boolean isHeaderPosition(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterPosition(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

    public void addHeaderView(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mFootViews.put(mFootViews.size() + ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }


}
