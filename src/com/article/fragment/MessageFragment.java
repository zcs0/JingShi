package com.article.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.article.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.x.listview.XListView.IXListViewListener;
import com.z.utils.LogUtils;



/**
 * @ClassName:     ProjectFragment.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月20日 上午10:06:36 
 * @Description:   消息
 */
public class MessageFragment extends BaseFragment implements IXListViewListener{
	private SwipeMenuListView mListView;
	private String TAG = "MessageFragment";
	private ProjectAdapter adapter;

	@Override
	public int createView() {
		// TODO Auto-generated method stub
		return R.layout.fragment_msg;
	}

	@Override
	public void initView(Bundle bundle, View view) {
		mListView = (SwipeMenuListView) getView(R.id.xListView);
//		mListView.setPullLoadEnable(true);
		//mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, items);
//		mAdapter = new ProjectAdapter();
		adapter = new ProjectAdapter();;
		mListView.setAdapter(adapter);;
//		mListView.setPullLoadEnable(false);//是否启用加载
//		mListView.setPullRefreshEnable(false);//是否启用刷新
//		mListView.setXListViewListener(this);//设置刷新监听
//		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
////				startActivity(new Intent(getActivity(), ProjectLocationMapActivity.class));
//				
//			}
//		});
		
		
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						context);
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
//						openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						context);
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
//						deleteItem.setWidth(dp2px(90));
				// set a icon
//						deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//						ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
//							open(item);
					break;
				case 1:
					// delete
//							delete(item);
//							mAppList.remove(position);
//							mAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
		
		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// swipe end
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
