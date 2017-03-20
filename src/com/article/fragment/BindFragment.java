package com.article.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.article.R;
import com.x.listview.XListView;
import com.x.listview.XListView.IXListViewListener;
import com.z.utils.LogUtils;



/**
 * @ClassName:     ProjectFragment.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月20日 上午10:06:36 
 * @Description:   绑定
 */
public class BindFragment extends BaseFragment implements IXListViewListener{
	private XListView mListView;
	private ProjectAdapter adapter;
	private String TAG="BindFragment";
	@Override
	public int createView() {
		// TODO Auto-generated method stub
		return R.layout.fragment_bind;
	}

	@Override
	public void initView(Bundle bundle, View view) {
		mListView = (XListView) getView(R.id.xListView);
		mListView.setPullLoadEnable(true);
		//mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, items);
//		mAdapter = new ProjectAdapter();
		adapter = new ProjectAdapter();;
		mListView.setAdapter(adapter);;
//		mListView.setPullLoadEnable(false);//是否启用加载
//		mListView.setPullRefreshEnable(false);//是否启用刷新
		mListView.setXListViewListener(this);//设置刷新监听
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				startActivity(new Intent(getActivity(), ProjectLocationMapActivity.class));
				
			}
		});
		
	}
	class ProjectAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 50;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = View.inflate(getActivity(), R.layout.adapter_item_pro, null);
			}
			
			return convertView;
		}
	}
	@Override
	public void onRefresh() {
		LogUtils.d(TAG  , "onRefresh...");
//		start = ++refreshCnt;
//		items.clear();
//		geneItems();
//		// mAdapter.notifyDataSetChanged();
//		mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, items);
//		mListView.setAdapter(mAdapter);
		//onLoad();
	}
	@Override
	public void onLoadMore() {
		LogUtils.d(TAG , "onRefresh...");
//		geneItems();
//		mAdapter.notifyDataSetChanged();
		//onLoad();
	}

	

}
