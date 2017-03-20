package com.x.listview;

import com.article.R;


/**
 * @ClassName:     InitView.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2015年6月4日 下午2:10:39 
 * @Description:   XListView的控件初始化
 */
public interface XListViewValues {
	int toTTtime=2000;//刷新时停留时间
	int botTime=2000;//加载数据时停留的时间 
	int xlistview_header=R.layout.xlistview_header;
	int xlistview_footer = R.layout.xlistview_footer;
	int xlistview_header_arrow=R.id.xlistview_header_arrow;
	int xlistview_header_content_id = R.id.xlistview_header_content;
	int xlistview_header_time = R.id.xlistview_header_time;
	int xlistview_footer_content=R.id.xlistview_footer_content;
	int xlistview_footer_progressbar=R.id.xlistview_footer_progressbar;
	int xlistview_footer_hint_textview=R.id.xlistview_footer_hint_textview;
	
	int xlistview_header_hint_textview = R.id.xlistview_header_hint_textview;
	int xlistview_header_progressbar = R.id.xlistview_header_progressbar;
	/*String xlistview_footer_hint_ready= "松开载入更多";
	String xlistview_footer_hint_normal="查看更多";
	String xlistview_header_hint_normal="下拉刷新";
	String xlistview_header_hint_ready="松开刷新数据";
	String xlistview_header_hint_loading="正在加载...";*/
	int xlistview_footer_hint_ready= R.string.xlistview_footer_hint_ready;//"松开载入更多";
	int xlistview_footer_hint_normal=R.string.xlistview_footer_hint_normal;//"查看更多";
	int xlistview_header_hint_normal=R.string.xlistview_header_hint_normal;//"下拉刷新";
	int xlistview_header_hint_ready=R.string.xlistview_header_hint_normal;//"松开刷新数据";
	int xlistview_header_hint_loading=R.string.xlistview_header_hint_loading;//"正在加载...";
	
	
}
