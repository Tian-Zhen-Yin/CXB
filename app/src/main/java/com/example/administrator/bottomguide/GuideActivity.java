package com.example.administrator.bottomguide;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.CheckPermissionsActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;

public class GuideActivity extends CheckPermissionsActivity implements INaviInfoCallback {

    private MapView mapView;
    private AMap aMap;//地图实体.
    private TextView mLocationErrText;
    private static final int FILL_COLOR=Color.argb(10,0,0,180);

    @Override
    public Resources getResources() {
        return getBaseContext().getResources();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onBackPressed();
        Poi start = new Poi("金陵科技学院", new LatLng(31.90387,118.89972), "");
        /**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
        Poi end = new Poi("北京站", new LatLng(39.904556, 116.427231), "B000A83M61");
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), GuideActivity.this);

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏*/
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        /*AMap aMap = mapView.getMap();*/
        init();

    }
    private void init() {
        if(aMap==null)//如果amap为空的话,则创建map.然后设置map的信息
        {
            aMap=mapView.getMap();
            setUpMap();
        }
        mLocationErrText=(TextView)findViewById(R.id.location_errInfo_text);//定位错误的文本域
        mLocationErrText.setVisibility(View.GONE);//不可见
        setupLocationStyle();//设置style
    }
    private void setupLocationStyle() {
        //自定义系统定位蓝点
        MyLocationStyle myLocationStyle=new MyLocationStyle();

        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        //设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        //将自定义的 mylocationStyle 对象添加的地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    /**
     * 设置一些amap 的信息
     */
    private void setUpMap() {
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);//设置true表示显示定位层并触发定位,false标识隐藏定位层并不可触发定位,默认是false

    }

    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
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

}
