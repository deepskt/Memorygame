package phonepe.deepak_memorygame.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import phonepe.deepak_memorygame.R;
import phonepe.deepak_memorygame.callback.PlayerMoveListner;
import phonepe.deepak_memorygame.constant.GameMode;
import phonepe.deepak_memorygame.database.GridEntity;
import phonepe.deepak_memorygame.view.adapter.PlayerViewAdapter;

public class PlayGame extends Activity {
    private GridView mGvPlayerView;
    private TextView mTvLevel, mTvScore, mTvTime;
    private ProgressBar mPBTimer;
    private GridEntity mLastSelectedEntity;
    private int curerntLevel,currentScore;
    private String currentMode;
    private int attemptCount =0;
    private CountDownTimer countDownTimer;
    private int timeSpend=1;
    private long countDownInterval=30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgame);
        curerntLevel  = getIntent().getIntExtra("game_level",0);
        currentScore =  getIntent().getIntExtra("game_score",0);
        currentMode  =  getIntent().getStringExtra("game_mode");
        mGvPlayerView = (GridView)findViewById(R.id.gv_player_grid);
        mTvLevel = (TextView)findViewById(R.id.tv_level);
        mTvScore = (TextView)findViewById(R.id.tv_score);
        mTvTime = (TextView)findViewById(R.id.tv_time);
        mPBTimer = (ProgressBar)findViewById(R.id.pb_timer);
        PlayerViewAdapter playerViewAdapter = new PlayerViewAdapter(this,playerMoveListner);
        playerViewAdapter.setData(getDummyData());
        mGvPlayerView.setAdapter(playerViewAdapter);
        countDownStart();
        checkForLevelAndSetView();
    }

    private List<GridEntity> getDummyData() {
        //Dummy list of grid items
        return new ArrayList<>();
    }

    private void countDownStart() {
        if(countDownTimer != null){
             countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(countDownInterval,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("GAME",String.valueOf(millisUntilFinished));
                timeSpend+=(millisUntilFinished/1000);
                mTvTime.setText(String.valueOf(millisUntilFinished/1000)+" sec");
                int progress = (int) (millisUntilFinished/1000);
                mPBTimer.setProgress(progress);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Game Over!",Toast.LENGTH_SHORT).show();
                checkForLevelAndSetView();
            }
        };
        countDownTimer.start();
    }

    private void checkForLevelAndSetView(){
        Log.d("GAME","checkForLevelAndSetView");
        mTvLevel.setText(String.format(getString(R.string.level),String.valueOf(curerntLevel)));
        mTvScore.setText(String.format(getString(R.string.score),String.valueOf(currentScore)));

        if(currentMode.equalsIgnoreCase(GameMode.mode_easy)){
            if(curerntLevel <=5){
                mGvPlayerView.setNumColumns(2);
            }else{
                mGvPlayerView.setNumColumns(4);
            }
        }else if(currentMode.equalsIgnoreCase(GameMode.mode_medium)){
            if(curerntLevel <=5){
                mGvPlayerView.setNumColumns(4);
            }else{
                mGvPlayerView.setNumColumns(6);
            }

        }else if(currentMode.equalsIgnoreCase(GameMode.mode_hard)){
            if(curerntLevel <=5){
                mGvPlayerView.setNumColumns(6);
            }else{
                mGvPlayerView.setNumColumns(8);
            }
        }
    }

    PlayerMoveListner playerMoveListner = new PlayerMoveListner() {
        @Override
        public void onPlayed(GridEntity gridEntity, Boolean allSelected) {
            if(mLastSelectedEntity != null && mLastSelectedEntity.getName().equalsIgnoreCase(gridEntity.getName())){
                mLastSelectedEntity = gridEntity;
                if(allSelected){
                    moveToNextLevel();
                }else{
                    updateScoreAndView(true);
                }
            }else{
                updateScoreAndView(false);
            }
        }
    };

    private void moveToNextLevel(){
        curerntLevel++;
        checkForLevelAndSetView();
    }

    public void updateScoreAndView(Boolean matched){
        attemptCount++;
        if(matched){
            currentScore+=(currentScore/(attemptCount*timeSpend));
            attemptCount =0;
            timeSpend = 1;
        }
    }

    @Override
    protected void onPause() {
        countDownInterval = countDownInterval-timeSpend;
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onRestart() {
        countDownStart();
        super.onRestart();
    }
}
