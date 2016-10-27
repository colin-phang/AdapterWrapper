# AdapterWrapper
A AdapterWrapper for RecyclerView which can add Header/Footer or set EmptyView without modifying existed Adapter. 

Usage
-----
sample codes is shown below:

```java
	//init your adatper
        MyAdapter adapter = new MyAdapter();

	//init a WrapperAdapter
        WrapperAdapter wrapper = new WrapperAdapter(adapter);
        TextView header = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_header_1, null);
        //add a headerView
        wrapper.addHeaderView(header);
        //set a emptyView
        wrapper.setEmptyView(R.layout.layout_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));        
	//set a wrapper instead of adapter to recyclerView
        recyclerView.setAdapter(wrapper);
```

then when data in RecyclerView is modified, just invoke `adapter.notifyDataSetChangd();`

```java
    private void addData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("Item " + i);
        }
        adapter.addData(datas);
        adapter.notifyDataSetChanged();
    }

    private void removeData() {
        adapter.setData(new ArrayList<String>());
        adapter.notifyDataSetChanged();
    }
```
