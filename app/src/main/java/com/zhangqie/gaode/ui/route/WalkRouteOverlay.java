package com.zhangqie.gaode.ui.route;

import android.content.Context;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;

import java.util.List;


public class WalkRouteOverlay extends RouteOverlay {

    private PolylineOptions mPolylineOptions;

    private BitmapDescriptor walkStationDescriptor= null;

    private WalkPath walkPath;
	public WalkRouteOverlay(Context context, AMap amap, WalkPath path,
                            LatLonPoint start, LatLonPoint end) {
		super(context);
		this.mAMap = amap;
		this.walkPath = path;
		startPoint = AMapServicesUtil.convertToLatLng(start);
		endPoint = AMapServicesUtil.convertToLatLng(end);
	}
	/**
	 * 添加路线到地图中。
	 * @since V2.1.0
	 */
    public void addToMap() {

        initPolylineOptions();
        try {
            List<WalkStep> walkPaths = walkPath.getSteps();
            mPolylineOptions.add(startPoint);
            for (int i = 0; i < walkPaths.size(); i++) {
                WalkStep walkStep = walkPaths.get(i);
                LatLng latLng = AMapServicesUtil.convertToLatLng(walkStep
                        .getPolyline().get(0));
                
				addWalkStationMarkers(walkStep, latLng);
                addWalkPolyLines(walkStep);
               
            }
            mPolylineOptions.add(endPoint);
            addStartAndEndMarker();

            showPolyline();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
	

    /**
     * @param walkStep
     */
    private void addWalkPolyLines(WalkStep walkStep) {
        mPolylineOptions.addAll(AMapServicesUtil.convertArrList(walkStep.getPolyline()));
    }

    /**
     * @param walkStep
     * @param position
     */
    private void addWalkStationMarkers(WalkStep walkStep, LatLng position) {
        addStationMarker(new MarkerOptions()
                .position(position)
                .title("\u65B9\u5411:" + walkStep.getAction()
                        + "\n\u9053\u8DEF:" + walkStep.getRoad())
                .snippet(walkStep.getInstruction()).visible(nodeIconVisible)
                .anchor(0.5f, 0.5f).icon(walkStationDescriptor));
    }

    /**
     * 初始化线段属性
     */
    private void initPolylineOptions() {

        if(walkStationDescriptor == null) {
            walkStationDescriptor = getWalkBitmapDescriptor();
        }
        mPolylineOptions = null;
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.setDottedLine(false);//设置是否为虚线
        mPolylineOptions.geodesic(false);//是否为大地曲线
        mPolylineOptions.visible(true);//线段是否可见
        mPolylineOptions.useGradient(false);//设置线段是否使用渐变色
        //设置线颜色，宽度
        mPolylineOptions.color(getWalkColor()).width(getRouteWidth());

    }


    private void showPolyline() {
        addPolyLine(mPolylineOptions);
    }

	/***
	 * 轨迹颜色修改
	 * 默认颜色在父类中
	 * @return
	 */
	public int getWalkColor() {
		return Color.parseColor("#E62BCA");
	}
}
