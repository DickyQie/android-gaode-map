package com.zhangqie.gaode.util;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteRailwayItem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AMapUtil {

    public static String getFriendlyLength(int lenMeter) {
        if (lenMeter > 10000) // 10 km
        {
            int dis = lenMeter / 1000;
            return dis + "";
        }

        if (lenMeter > 1000) {
            float dis = (float) lenMeter / 1000;
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String dstr = fnum.format(dis);
            return dstr;
        }

        if (lenMeter > 100) {
            int dis = lenMeter / 50 * 50;
            return dis + "";
        }

        int dis = lenMeter / 10 * 10;
        if (dis == 0) {
            dis = 10;
        }

        return dis + "";
    }

    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**
     * 把集合体的LatLonPoint转化为集合体的LatLng
     */
    public static ArrayList<LatLng> convertArrList(List<LatLonPoint> shapes) {
        ArrayList<LatLng> lineShapes = new ArrayList<LatLng>();
        for (LatLonPoint point : shapes) {
            LatLng latLngTemp = AMapUtil.convertToLatLng(point);
            lineShapes.add(latLngTemp);
        }
        return lineShapes;
    }

    /**
     * long类型时间格式化
     */
    public static String convertToTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return df.format(date);
    }

    public static final String HtmlBlack = "#000000";
    public static final String HtmlGray = "#808080";

    public static String getFriendlyTime(int second) {
        if (second > 3600) {
            int hour = second / 3600;
            int miniate = (second % 3600) / 60;
            return hour + "小时" + miniate + "分钟";
        }
        if (second >= 60) {
            int miniate = second / 60;
            return miniate + "分钟";
        }
        return second + "秒";
    }


    public static String getBusPathTitle(BusPath busPath) {
        if (busPath == null) {
            return String.valueOf("");
        }
        List<BusStep> busSetps = busPath.getSteps();
        if (busSetps == null) {
            return String.valueOf("");
        }
        StringBuffer sb = new StringBuffer();
        for (BusStep busStep : busSetps) {
            if (busStep.getBusLines().size() > 0) {
                RouteBusLineItem busline = busStep.getBusLines().get(0);
                if (busline == null) {
                    continue;
                }
                String buslineName = getSimpleBusLineName(busline.getBusLineName());
                sb.append(buslineName);
                sb.append(" > ");
            }
            if (busStep.getRailway() != null) {
                RouteRailwayItem railway = busStep.getRailway();
                sb.append(railway.getTrip() + "(" + railway.getDeparturestop().getName()
                        + " - " + railway.getArrivalstop().getName() + ")");
                sb.append(" > ");
            }
        }
        return sb.substring(0, sb.length() - 3);
    }

    public static String getBusPathDes(BusPath busPath) {
        if (busPath == null) {
            return String.valueOf("");
        }
        long second = busPath.getDuration();
        String time = getFriendlyTime((int) second);
        float subDistance = busPath.getDistance();
        String subDis = getFriendlyLength((int) subDistance);
        float walkDistance = busPath.getWalkDistance();
        String walkDis = getFriendlyLength((int) walkDistance);
        return String.valueOf(time + " | " + subDis + " | 步行" + walkDis);
    }

    public static String getSimpleBusLineName(String busLineName) {
        if (busLineName == null) {
            return String.valueOf("");
        }
        return busLineName.replaceAll("\\(.*?\\)", "");
    }


}
