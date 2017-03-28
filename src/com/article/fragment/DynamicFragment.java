package com.article.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.article.R;
import com.article.activity.ArticleInfoActivity;
import com.article.view.DeleteListViewMainActivity.MessageItem;
import com.article.view.ListViewCompat;
import com.article.view.SlideView;
import com.x.listview.XListView.IXListViewListener;
import com.z.utils.LogUtils;

/**
 * @ClassName: ProjectFragment.java
 * @author zcs
 * @version V1.0
 * @Date 2017年3月20日 上午10:06:36
 * @Description: 首页-今日动态
 */
public class DynamicFragment extends BaseFragment implements IXListViewListener {

	private ListViewCompat mListView;
	private ArrayAdapter<String> mAdapter;

	private int start;
	private int refreshCnt;
	private ProjectAdapter adapter;
	private String TAG = "ProjectFragment";
	private List<MessageItem> result;

	@Override
	public int createView() {
		return R.layout.fragment_pro;

	}

	@Override
	public void initView(Bundle bundle, View view) {
		mListView = (ListViewCompat) getView(R.id.listView);
		// mAdapter = new ProjectAdapter();
		adapter = new ProjectAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(context, position + " long click", 0).show();
				return false;
			}
		});
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(context, ArticleInfoActivity.class));
				
			}
			
		});
	}

	@Override
	public void onRefresh() {
		LogUtils.d(TAG, "onRefresh...");
	}

	@Override
	public void onLoadMore() {
		LogUtils.d(TAG, "onRefresh...");
	}
	class ProjectAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public Object getItem(int position) {
			
			return result.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SlideView slideView;
			if(convertView==null){
				View itemView = View.inflate(getActivity(), R.layout.adapter_item_pro, null);
                slideView = new SlideView(getActivity());
                slideView.setContentView(itemView);
			}else{
				slideView = (SlideView) convertView;
			}
			return slideView;
		}

	}

}
