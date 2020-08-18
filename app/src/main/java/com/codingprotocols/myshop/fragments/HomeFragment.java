package com.codingprotocols.myshop.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.adapters.BannerSliderAdapter;
import com.codingprotocols.myshop.adapters.DealsOfTheDayAdapter;
import com.codingprotocols.myshop.adapters.TopPicksLayoutAdapter;
import com.codingprotocols.myshop.models.BannerSliderModel;
import com.codingprotocols.myshop.models.BasicProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {


    private FirebaseFirestore db;
    final private long DELAY_TIME= 3000;
    final private long PERIOD_TIME=3000;

    // Banner start -- start
    private ViewPager bannerSliderViewPager;
    private List<BannerSliderModel> bannerSliderModelList;
    private int currentPage=1;
    private Timer timer;
    // Banner end -- end

    // Deal of the day -- start
    private TextView dealOfTheDayTitle;
    private Button dealOfTheDayButton;
    private RecyclerView dealsOfTheDayRecyclerView;
    // deal of the data -- end

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        // banner
        bannerSliderViewPager=view.findViewById(R.id.banner_slide_view_page);

        bannerSliderModelList= new ArrayList<>();
        BannerSliderAdapter bannerSliderAdapter=new BannerSliderAdapter(bannerSliderModelList);
        bannerSliderViewPager.setAdapter(bannerSliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        db.collection("offer_zone")
                .document("Banners").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    for (Map.Entry<String,Object> entry : Objects.requireNonNull(document.getData()).entrySet())
                        bannerSliderModelList.add(new BannerSliderModel((String) entry.getValue()));
                        bannerSliderAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

//        ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };

        startBannerShow();

        bannerSliderViewPager.setOnTouchListener((View v, MotionEvent event) -> {
            stopBannerShow();
            if(event.getAction()==MotionEvent.ACTION_UP){
                startBannerShow();
            }
            return false;
        });
        // banner

        // Deal of the day
        dealOfTheDayTitle=view.findViewById(R.id.deals_of_the_day_title);
        dealOfTheDayButton=view.findViewById(R.id.more_deals_button);
        dealsOfTheDayRecyclerView=view.findViewById(R.id.deals_of_the_day_recyclerview);
        List<BasicProductModel> basicProductModelList=new ArrayList<>();
        basicProductModelList.add(new BasicProductModel("product 1","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 2","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 3","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 4","Rs. xyz",R.drawable.simple_logo));     basicProductModelList.add(new BasicProductModel("product 1","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 5","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 6","Rs. xyz",R.drawable.simple_logo));
        basicProductModelList.add(new BasicProductModel("product 7","Rs. xyz",R.drawable.simple_logo));

        DealsOfTheDayAdapter dealsOfTheDayAdapter=new DealsOfTheDayAdapter(basicProductModelList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        dealsOfTheDayRecyclerView.setLayoutManager(linearLayoutManager);

        dealsOfTheDayRecyclerView.setAdapter(dealsOfTheDayAdapter);
        dealsOfTheDayAdapter.notifyDataSetChanged();

        // deal of the day

        // Top picks for you
        TextView topPicksTitle=view.findViewById(R.id.top_picks_title);
        Button topPicksMoreButton=view.findViewById(R.id.top_picks_more_button);
        GridView topPicksGridView=view.findViewById(R.id.top_picks_grid_view);

        topPicksGridView.setAdapter(new TopPicksLayoutAdapter(basicProductModelList));

        // top picks for you

        return view;
    }

    private void startBannerShow(){
        Handler handler=new Handler();
        Runnable update= () -> {
            if(bannerSliderModelList.size()!=0){
                if(currentPage>= bannerSliderModelList.size()){
                    currentPage=0;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            handler.post(update);
            }
        },DELAY_TIME, PERIOD_TIME);
    }

    private void stopBannerShow(){
        timer.cancel();
    }
}