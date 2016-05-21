package shehan.com.migrainetrigger.controller;

import android.text.format.DateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBActivityDAO;
import shehan.com.migrainetrigger.data.dao.DBBodyAreaDAO;
import shehan.com.migrainetrigger.data.dao.DBLocationDAO;
import shehan.com.migrainetrigger.data.dao.DBMedicineDAO;
import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.dao.DBReliefDAO;
import shehan.com.migrainetrigger.data.dao.DBSymptomDAO;
import shehan.com.migrainetrigger.data.dao.DBTriggerDAO;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.AppUtil;

/**
 * Created by Shehan on 4/13/2016.
 */
public class ReportController {

    public static String getAverage(Timestamp from, Timestamp to) {
        ArrayList<Record> averageRecords = DBRecordDAO.getAverageReportRecords(from, to);
        String strTotal = "-";
        if (averageRecords.size() > 0) {

            long total = 0;
            int count = 0;
            for (int i = 0; i < averageRecords.size(); i++) {
                Record record = averageRecords.get(i);

                if (record.getStartTime() != null && record.getEndTime() != null) {

                    Timestamp startTime = record.getStartTime();
                    Timestamp endTime = record.getEndTime();

                    if (startTime != null && endTime != null) {
                        long difference = endTime.getTime() - startTime.getTime();
                        long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                        total += differenceInSeconds;
                        count++;
                    }
                }
            }
            if (count != 0) {
                long average = total / count;
                strTotal = AppUtil.getFriendlyDuration(average);
            }
        }
        return strTotal;
    }

    public static double getIntensity(Timestamp from, Timestamp to) {
        ArrayList<Record> averageRecords = DBRecordDAO.getAverageReportRecords(from, to);
        double intensity = 0;

        if (averageRecords.size() > 0) {

            long total = 0;
            int count = 0;
            for (int i = 0; i < averageRecords.size(); i++) {
                Record record = averageRecords.get(i);

                if (record != null && record.getIntensity() > 0) {
                    total += record.getIntensity();
                    count++;

                }
            }
            if (count != 0) {
                intensity = (double) total / count;
            }
        }
        return intensity;
    }

    public static ArrayList<String> getTopActivities(Timestamp from, Timestamp to, int limit) {
        return DBActivityDAO.getTopActivities(from, to, limit);
    }

    public static ArrayList<String> getTopBodyAreas(Timestamp from, Timestamp to, int limit) {
        return DBBodyAreaDAO.getTopBodyAreas(from, to, limit);
    }

    public static ArrayList<String> getTopEffectiveMedicines(Timestamp from, Timestamp to, int limit) {
        return DBMedicineDAO.getTopEffectiveMedicines(from, to, limit);
    }

    public static ArrayList<String> getTopEffectiveReliefs(Timestamp from, Timestamp to, int limit) {
        return DBReliefDAO.getTopEffectiveReliefs(from, to, limit);
    }

    public static ArrayList<String> getTopLocations(Timestamp from, Timestamp to, int limit) {
        return DBLocationDAO.getTopLocations(from, to, limit);
    }

    public static ArrayList<String> getTopMedicines(Timestamp from, Timestamp to, int limit) {
        return DBMedicineDAO.getTopMedicines(from, to, limit);
    }

    public static ArrayList<String> getTopReliefs(Timestamp from, Timestamp to, int limit) {
        return DBReliefDAO.getTopReliefs(from, to, limit);
    }

    public static ArrayList<String> getTopSymptoms(Timestamp from, Timestamp to, int limit) {
        return DBSymptomDAO.getTopSymptoms(from, to, limit);
    }

    public static ArrayList<String> getTopTriggers(Timestamp from, Timestamp to, int limit) {
        return DBTriggerDAO.getTopTriggers(from, to, limit);
    }

    public static int getTotalRecords(Timestamp from, Timestamp to) {
        return DBRecordDAO.getTotalRecords(from, to);
    }
}
