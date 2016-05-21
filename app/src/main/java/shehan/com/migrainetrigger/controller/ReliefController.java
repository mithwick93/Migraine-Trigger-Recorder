package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBReliefDAO;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class ReliefController {

    public static void addNewRelief(Relief relief) {

    }

    public static long addReliefRecord(int reliefId, int recordId, boolean effective) {
        Log.d("ReliefController", " addReliefRecord ");
        return DBReliefDAO.addReliefRecord(reliefId, recordId, effective);
    }

    public static long addReliefs(ArrayList<Relief> lst) {
        for (Relief itm : lst) {
            long result = addRelief(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addRelief(Relief relief) {
        return DBReliefDAO.addRelief(relief);
    }

    public static long deleteRelief(int id) {
        return DBReliefDAO.deleteRelief(id);
    }

    public static AnswerSectionViewData[] getAnswerSectionViewData() {
        ArrayList<Relief> lst = getAllReliefs();
        AnswerSectionViewData[] answerSectionViewData = new AnswerSectionViewData[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Relief relief = lst.get(i);

            answerSectionViewData[i] = new AnswerSectionViewData(relief.getReliefId(), relief.getReliefName(), relief.getPriority());
        }
        return answerSectionViewData;
    }

    public static ArrayList<Relief> getAllReliefs() {
        Log.d("ReliefController", " getAllReliefs ");
        ArrayList<Relief> reliefArrayList = DBReliefDAO.getAllReliefs();
        Collections.sort(reliefArrayList);
        return reliefArrayList;
    }

    public static int getLastRecordId() {
        return DBReliefDAO.getLastRecordId();
    }

    public static Relief getReliefById(int id) {
        return null;
    }

    public static ArrayList<Relief> getReliefsForRecord(int recordId) {
        return DBReliefDAO.getReliefsForRecord(recordId);
    }

    public static void reorderPriority(Relief relief) {

    }

    public static long updateReliefRecord(Relief relief) {
        return DBReliefDAO.updateReliefRecord(relief);
    }
}
