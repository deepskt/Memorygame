package phonepe.deepak_memorygame.callback;

import phonepe.deepak_memorygame.database.GridEntity;

public interface PlayerMoveListner {
    void onPlayed(GridEntity gridEntity, Boolean allSelected);
}
