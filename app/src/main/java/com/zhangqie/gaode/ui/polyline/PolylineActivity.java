package com.zhangqie.gaode.ui.polyline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.zhangqie.gaode.R;
import com.zhangqie.gaode.util.AMapUtil;

import java.util.ArrayList;
import java.util.List;



/***
 * 参考文档
 *
 * http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html
 */

public class PolylineActivity extends AppCompatActivity {

	private AMap aMap;
	private MapView mapView;
	private GeocodeSearch geocoderSearch;

	private List<LatLng> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			geocoderSearch = new GeocodeSearch(this);
			setUpMap();
		}
	}

	private void setUpMap() {
		list = showListLat();
		//起点位置和  地图界面大小控制
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 7));
		aMap.setMapTextZIndex(2);
		// 绘制一条带有纹理的直线
		aMap.addPolyline((new PolylineOptions())
				//手动数据测试
				//.add(new LatLng(26.57, 106.71),new LatLng(26.14,105.55),new LatLng(26.58, 104.82), new LatLng(30.67, 104.06))
				//集合数据
				.addAll(list)
				//线的宽度
				.width(10).setDottedLine(true).geodesic(true)
				//颜色
				.color(Color.argb(255,255,20,147)));

		LatLonPoint latLonPoint = new LatLonPoint(30.67,104.06);

		//起点图标
		aMap.addMarker(new MarkerOptions()
				.position(AMapUtil.convertToLatLng(latLonPoint))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));

		//终点坐标
		LatLonPoint latLonPointEnd = new LatLonPoint(29.89, 107.7);
		aMap.addMarker(new MarkerOptions()
				.position(AMapUtil.convertToLatLng(latLonPointEnd))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	/***
	 *经纬度集合
	 */
	private List<LatLng> showListLat(){
		List<LatLng> points = new ArrayList<LatLng>();
		for (int i = 0; i < coords.length; i += 2) {
			points.add(new LatLng(coords[i+1], coords[i]));
		}
		return points;
	}

	private double[] coords = {
			104.06, 30.67,
			104.32, 30.88,
			104.94, 30.57,
			103.29, 30.2,
			103.81, 30.97,
			104.73, 31.48,
			106.06, 30.8,
			107.7, 29.89
		};

}
