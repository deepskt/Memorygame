package phonepe.deepak_memorygame.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import phonepe.deepak_memorygame.R;
import phonepe.deepak_memorygame.callback.PlayerMoveListner;
import phonepe.deepak_memorygame.database.GridEntity;

public class PlayerViewAdapter extends BaseAdapter implements View.OnClickListener {
    private PlayerMoveListner moveListner;
    private List<GridEntity> mGridItems;
    private Context mContext;

    public PlayerViewAdapter(Context context, PlayerMoveListner playerMoveListner) {
        moveListner = playerMoveListner;
        mContext = context;
        mGridItems = new ArrayList<>();
    }

    public void setData(List<GridEntity> list){
        mGridItems.clear();
        mGridItems  = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GridEntity entity = mGridItems.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_grid_entity, null);
        }
        TextView ivName = (TextView) convertView.findViewById(R.id.tv_name);
        ivName.setText(entity.getName());
        ivName.setTag(position);
        if(entity.getSelected()){
            ivName.setBackgroundColor(Color.RED);
        }else{
            ivName.setBackgroundColor(Color.WHITE);
        }
        ivName.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        GridEntity gridEntity = mGridItems.get(Integer.parseInt(v.getTag().toString()));
        gridEntity.setSelected(true);
        mGridItems.add(Integer.parseInt(v.getTag().toString()),gridEntity);
        setData(mGridItems);
        moveListner.onPlayed(gridEntity,false);
    }
}
