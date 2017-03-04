package com.projet.e4fi.notlate;

import java.util.Calendar;

/**
 * Created by Kiros on 02/03/2017.
 */

public class AlarmModel {

    public int id = -1;
    public int Hour;
    public int Minutes;
    public int Date;
    public int MusiqueId;

    public AlarmModel() {

    }

    public void BuildAlarmModel(Calendar calendar, int A_id, int musique_id) {
        this.Hour = calendar.get(Calendar.HOUR);
        this.Minutes = calendar.get(Calendar.MINUTE);
        this.Date = calendar.get(Calendar.DATE);
        this.id = A_id;
        this.MusiqueId = musique_id;
    }
}
