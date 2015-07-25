package com.example.songjiwon.navermap;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

/*
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
//import com.nhn.android.mapviewer.R;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
*/


public class MainActivity extends NMapActivity implements OnMapStateChangeListener, OnMapViewTouchEventListener, NMapOverlayManager.OnCalloutOverlayListener {

    public static final String API_KEY="93894deafcca212145b17713e13697bd";
    NMapView mMapView = null;
    mMapView.setScalingFactor(1.5f);
    NMapController nMapController = null;
    LinearLayout MapContainer;
    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager;
//    NMapPOIdataOverlay.OnStateChangeListener  onPOIdataStateChangeListener ;

    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {
//        @Override
//        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
//
//        }
//
//        @Override
//        public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
//
//        }




        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item)
        {
            // [[TEMP]] handle a click event of the callout
            ///////////////////////////////////////////////////////////////////Toast.makeText(MainActivity.this, "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), NewActivity.class);
            startActivity(intent);
        }
        //// public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) 끝

        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item)
        {
            if (item != null) {
                Log.i("NMAP", "onFocusChanged: " + item.toString());
            } else {
                Log.i("NMAP", "onFocusChanged: ");
            }
        }
        //// public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) 끝





    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MapContainer =  (LinearLayout)findViewById(R.id.map);

        // create map view
        mMapView = new NMapView(this);

        // set a registered API key for Open MapViewer Library
        mMapView.setApiKey(API_KEY);

        // set the activity content to the map view
        setContentView(mMapView);

        // initialize map view
        mMapView.setClickable(true);

        // register listener for map state changes
        mMapView.setOnMapStateChangeListener(this);
        mMapView.setOnMapViewTouchEventListener(this);

        /// use built in voom controls
        mMapView.setBuiltInZoomControls(true, null);

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        nMapController = mMapView.getMapController();

        ///7월 21일 새로운 추가(Overlay부분)
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);



        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(127.0717071, 37.5430388, "헤헤 우마이도 - 건대 인본 라멘 집", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // show all POI data
        poiDataOverlay.showAllPOIdata(0);


        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);


        // register callout overlay listener to customize it.
        mOverlayManager.setOnCalloutOverlayListener((NMapOverlayManager.OnCalloutOverlayListener) this);

    }
    ////protected void onCreate(Bundle savedInstanceState) 끝






























    @Override
    public void onMapInitHandler(NMapView mapView, NMapError errorInfo)
    {
        if (errorInfo == null) { // success
            //nMapController.setMapCenter(new NGeoPoint(127.0717071, 37.5430388), 11);
        } else { // fail
            android.util.Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
        }
        // use built in zoom controls
      // mMapView.setBuiltInZoomControls(true, null);
    }
    //// public void onMapInitHandler(NMapView mapView, NMapError errorInfo) 끝






    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //// public boolean onCreateOptionsMenu(Menu menu) 끝






    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //// public boolean onOptionsItemSelected(MenuItem item) 끝






    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0, NMapOverlayItem arg1, Rect arg2)
    {
        // set your callout overlay
        return new NMapCalloutBasicOverlay(arg0, arg1, arg2);
    }
    //// public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0, NMapOverlayItem arg1, Rect arg2) 끝















    ////안쓰는 부분!!

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }
}
