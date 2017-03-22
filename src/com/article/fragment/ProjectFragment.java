package com.article.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.article.R;
import com.article.activity.LineChartActivity;
import com.article.activity.ProjectLocationMapActivity;
import com.article.activity.SearchActivity;
import com.x.listview.XListView;
import com.x.listview.XListView.IXListViewListener;
import com.z.utils.LogUtils;


/**
 * @ClassName:     ProjectFragment.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月20日 上午10:06:36 
 * @Description:   项目
 */
public class ProjectFragment extends BaseFragment implements IXListViewListener{
	
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	
	private int start;
	private int refreshCnt;
	private ProjectAdapter adapter;
	private String TAG = "ProjectFragment";

	@Override
	public int createView() {
		return R.layout.fragment_pro;
		
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
				startActivity(new Intent(getActivity(), ProjectLocationMapActivity.class));
				
			}
		});
		TextView txtSearch = findViewById(R.id.et_search);
		txtSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SearchActivity.class));
				
			}
		});
		View btn = findViewById(R.id.btn_click);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), LineChartActivity.class));
				
			}
		});;
//		editText = (EditText) view.findViewById(R.id.editText);
//		txtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener()
//		{
//		        public void onFocusChange(View v, boolean hasFocus)
//		        {
//		                if (hasFocus)
//		                {
//		                	System.out.println("----------onEditorAction");
//		                }
//		        }
//		});
//		txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				System.out.println("----------onEditorAction");
//				return false;
//			}
//		});
	}


	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	
	private void geneItems() {
//		for (int i = 0; i != 20; ++i) {
//			items.add("refresh cnt " + (++start));
//		}
	}
	@Override
	public void onRefresh() {
		LogUtils.d(TAG , "onRefresh...");
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
	

}
