package phonepe.deepak_memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import phonepe.deepak_memorygame.constant.GameMode;
import phonepe.deepak_memorygame.view.activity.PlayGame;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton rbEasy, rbHard, rbMedium;
    private RadioGroup rgLevelGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgLevelGroup = (RadioGroup)findViewById(R.id.rg_level);
        rgLevelGroup.setOnCheckedChangeListener(this);
        rbEasy = (RadioButton)findViewById(R.id.rb_easy);
        rbMedium = (RadioButton)findViewById(R.id.rb_medium);
        rbHard = (RadioButton)findViewById(R.id.rb_hard);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_easy:
                startGame(GameMode.mode_easy);
                break;
            case R.id.rb_medium:
                startGame(GameMode.mode_medium);
                break;
            case R.id.rb_hard:
                startGame(GameMode.mode_hard);
                break;
        }
    }

    private void startGame(String gameMode) {
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtra("game_level", 1);
        intent.putExtra("game_score", 0);
        intent.putExtra("game_mode",gameMode);
        startActivity(intent);
    }
}
