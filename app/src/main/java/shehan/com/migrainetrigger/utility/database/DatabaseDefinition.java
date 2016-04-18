package shehan.com.migrainetrigger.utility.database;

/**
 * This interface contains important database definitions
 * Created by Shehan on 4/13/2016.
 */
public interface DatabaseDefinition {

    //region Definitions
    // Database Name
    String DATABASE_NAME = "MigraineTrigger";

    //Database Version (Increase one if want to also upgrade your database)
    int DATABASE_VERSION = 1;// started at 1
    //endregion
    //
    //region Table names
    //------------
    //
    String ACTIVITY_TABLE = "activity";
    String BODY_AREA_TABLE = "body_area";
    String LOCATION_TABLE = "location";
    String MEDICINE_TABLE = "medicine";
    String RECORD_TABLE = "migraine_record";
    String RELIEF_TABLE = "relief";
    String SYMPTOM_TABLE = "symptom";
    String TRIGGER_TABLE = "migraine_trigger";
    String WEATHER_DATA_TABLE = "weather_data";
    //
    //
    String ACTIVITY_RECORD_TABLE = "activity_record";
    String BODY_AREA_RECORD_TABLE = "body_area_record";
    //    String LOCATION_RECORD_TABLE = "location_record";
    String MEDICINE_RECORD_TABLE = "medicine_record";
    String RELIEF_RECORD_TABLE = "relief_record";
    String SYMPTOM_RECORD_TABLE = "symptom_record";
    String TRIGGER_RECORD_TABLE = "trigger_record";
    //
    //
    //endregion
    //
    //region Table column names
    //------------
    //
    // ACTIVITY_TABLE Columns names
    String ACTIVITY_ID_KEY = "activity_id";
    String ACTIVITY_NAME_KEY = "activity_name";
    String ACTIVITY_PRIORITY_KEY = "priority";

    // BODY_AREA_TABLE  Columns names
    String BODY_AREA_ID_KEY = "area_id";
    String BODY_AREA_NAME_KEY = "area_name";

    // LOCATION_TABLE  Columns names
    String LOCATION_ID_KEY = "location_id";
    String LOCATION_NAME_KEY = "location_name";

    // MEDICINE_TABLE Columns names
    String MEDICINE_ID_KEY = "medicine_id";
    String MEDICINE_NAME_KEY = "medicine_name";
    String MEDICINE_PRIORITY_KEY = "priority";

    // RECORD_TABLE Columns names
    String RECORD_ID_KEY = "record_id";
    String RECORD_START_TIME_KEY = "start_time";
    String RECORD_END_TIME_KEY = "end_time";
    String RECORD_INTENSITY_KEY = "intensity";
    String RECORD_LOCATION_ID_KEY = "location_id";

    // RELIEF_TABLE Columns names
    String RELIEF_ID_KEY = "relief_id";
    String RELIEF_NAME_KEY = "relief_name";
    String RELIEF_PRIORITY_KEY = "priority";

    // SYMPTOM_TABLE Columns names
    String SYMPTOM_ID_KEY = "symptom_id";
    String SYMPTOM_NAME_KEY = "symptom_name";
    String SYMPTOM_PRIORITY_KEY = "priority";

    // TRIGGER_TABLE Columns names
    String TRIGGER_ID_KEY = "trigger_id";
    String TRIGGER_NAME_KEY = "trigger_name";
    String TRIGGER_PRIORITY_KEY = "priority";

    // WEATHER_DATA_TABLE Columns names
    String WEATHER_DATA_ID_KEY = "weather_id";
    String WEATHER_DATA_HUMIDITY_KEY = "humidity";
    String WEATHER_DATA_PRESSURE_KEY = "pressure";
    String WEATHER_DATA_TEMPERATURE_KEY = "temperature";
    String WEATHER_DATA_RECORD_ID_KEY = "record_id";
    //
    //
    // ACTIVITY_RECORD_TABLE  Columns names
    String ACTIVITY_RECORD_ACTIVITY_ID_KEY = "activity_id";
    String ACTIVITY_RECORD_RECORD_ID_KEY = "record_id";

    // BODY_AREA_RECORD_TABLE  Columns names
    String BODY_AREA_RECORD_AREA_ID_KEY = "area_id";
    String BODY_AREA_RECORD_RECORD_ID_KEY = "record_id";

//    // LOCATION_RECORD_TABLE  Columns names
//    String LOCATION_RECORD_LOCATION_ID_KEY = "location_id";
//    String LOCATION_RECORD_RECORD_ID_KEY = "record_id";

    // MEDICINE_RECORD_TABLE  Columns names
    String MEDICINE_RECORD_MEDICINE_ID_KEY = "medicine_id";
    String MEDICINE_RECORD_RECORD_ID_KEY = "record_id";
    String MEDICINE_RECORD_EFFECTIVE_KEY = "effective";

    // RELIEF_RECORD_TABLE  Columns names
    String RELIEF_RECORD_RELIEF_ID_KEY = "relief_id";
    String RELIEF_RECORD_RECORD_ID_KEY = "record_id";
    String RELIEF_RECORD_EFFECTIVE_KEY = "effective";

    // SYMPTOM_RECORD_TABLE  Columns names
    String SYMPTOM_RECORD_SYMPTOM_ID_KEY = "symptom_id";
    String SYMPTOM_RECORD_RECORD_ID_KEY = "record_id";

    // TRIGGER_RECORD_TABLE  Columns names
    String TRIGGER_RECORD_TRIGGER_ID_KEY = "trigger_id";
    String TRIGGER_RECORD_RECORD_ID_KEY = "record_id";
    //
    //endregion
    //
    //region Create table syntax
    //-------------------
    //Create ACTIVITY_TABLE
    String ACTIVITY_CREATE =
            "CREATE TABLE activity(\n" +
                    "\n" +
                    "  activity_id INT AUTO INCREMENT,\n" +
                    "  activity_name TEXT UNIQUE NOT NULL,\n" +
                    "  priority INT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (activity_id)\n" +
                    "  \n" +
                    ");";

    //Create BODY_AREA_TABLE
    String BODY_AREA_CREATE =
            "CREATE TABLE body_area(\n" +
                    "\n" +
                    "  area_id INT AUTO INCREMENT,\n" +
                    "  area_name TEXT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (area_id)\n" +
                    "  \n" +
                    ");";

    //Create LOCATION_TABLE
    String LOCATION_CREATE =
            "CREATE TABLE location(\n" +
                    "\n" +
                    "  location_id INT AUTO INCREMENT,\n" +
                    "  location_name TEXT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (location_id)\n" +
                    "  \n" +
                    ");";

    //Create MEDICINE_TABLE
    String MEDICINE_CREATE =
            "CREATE TABLE medicine(\n" +
                    "\n" +
                    "  medicine_id INT AUTO INCREMENT,\n" +
                    "  medicine_name TEXT UNIQUE NOT NULL,\n" +
                    "  priority INT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (medicine_id)\n" +
                    "  \n" +
                    ");";

    //Create RECORD_TABLE
    String RECORD_CREATE =
            "CREATE TABLE migraine_record(\n" +
                    "\n" +
                    "  record_id INT AUTO INCREMENT,\n" +
                    "  start_time TEXT DEFAULT NULL,\n" +
                    "  end_time TEXT DEFAULT NULL,\n" +
                    "  intensity INT DEFAULT NULL,\n" +
                    "  location_id INT DEFAULT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (record_id),\n" +
                    "  CONSTRAINT fk_migraine_record_location_id FOREIGN KEY (location_id) REFERENCES location(location_id)\n" +
                    "  \n" +
                    ");";

    //Create RELIEF_TABLE
    String RELIEF_CREATE =
            "CREATE TABLE relief(\n" +
                    "\n" +
                    "  relief_id INT AUTO INCREMENT,\n" +
                    "  relief_name TEXT UNIQUE NOT NULL,\n" +
                    "  priority INT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (relief_id)\n" +
                    "  \n" +
                    ");";

    //Create SYMPTOM_TABLE
    String SYMPTOM_CREATE =
            "CREATE TABLE symptom(\n" +
                    "\n" +
                    "  symptom_id INT AUTO INCREMENT,\n" +
                    "  symptom_name TEXT UNIQUE NOT NULL,\n" +
                    "  priority INT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (symptom_id)\n" +
                    "  \n" +
                    ");";

    //Create TRIGGER_TABLE
    String TRIGGER_CREATE =
            "CREATE TABLE migraine_trigger(\n" +
                    "\n" +
                    "  trigger_id INT AUTO INCREMENT,\n" +
                    "  trigger_name TEXT UNIQUE NOT NULL,\n" +
                    "  priority INT UNIQUE NOT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (trigger_id)\n" +
                    "  \n" +
                    ");";

    //Create WEATHER_DATA_TABLE
    String WEATHER_DATA_CREATE =
            "CREATE TABLE weather_data(\n" +
                    "\n" +
                    "  weather_id INT AUTO INCREMENT,\n" +
                    "  record_id INT,\n" +
                    "  humidity REAL DEFAULT NULL,\n" +
                    "  pressure REAL DEFAULT NULL,\n" +
                    "  temperature REAL DEFAULT NULL,\n" +
                    "  \n" +
                    "  PRIMARY KEY (weather_id,record_id),\n" +
                    "  CONSTRAINT fk_weather_data_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";
    //
    //
    //Create ACTIVITY_RECORD_TABLE
    String ACTIVITY_RECORD_CREATE =
            "CREATE TABLE activity_record(\n" +
                    "\n" +
                    "  activity_id INT,\n" +
                    "  record_id INT,  \n" +
                    "  \n" +
                    "  PRIMARY KEY (activity_id,record_id),\n" +
                    "  CONSTRAINT fk_activity_record_activity_id FOREIGN KEY (activity_id) REFERENCES activity(activity_id),\n" +
                    "  CONSTRAINT fk_activity_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";

    //Create BODY_AREA_RECORD_TABLE
    String BODY_AREA_RECORD_CREATE =
            "CREATE TABLE body_area_record(\n" +
                    "\n" +
                    "  area_id INT,\n" +
                    "  record_id INT,\n" +
                    "  \n" +
                    "  PRIMARY KEY (area_id,record_id),\n" +
                    "  CONSTRAINT fk_body_area_record_area_id FOREIGN KEY (area_id) REFERENCES body_area(area_id),\n" +
                    "  CONSTRAINT fk_body_area_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";

//    //Create LOCATION_RECORD_TABLE
//    String LOCATION_RECORD_CREATE =
//            "CREATE TABLE location_record(\n" +
//                    "\n" +
//                    "  location_id INT,\n" +
//                    "  record_id INT,\n" +
//                    "  \n" +
//                    "  PRIMARY KEY (location_id,record_id),\n" +
//                    "  CONSTRAINT fk__location_record_location_id FOREIGN KEY (location_id) REFERENCES location(location_id) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
//                    "  CONSTRAINT fk_location_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
//                    "  \n" +
//                    ");";

    //Create MEDICINE_RECORD_TABLE
    String MEDICINE_RECORD_CREATE =
            "CREATE TABLE medicine_record(\n" +
                    "\n" +
                    "  medicine_id INT,\n" +
                    "  record_id INT, \n" +
                    "  effective TEXT NOT NULL DEFAULT 'f',\n" +
                    "  \n" +
                    "  PRIMARY KEY (medicine_id,record_id),\n" +
                    "  CONSTRAINT fk_medicine_record_medicine_id FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id),\n" +
                    "  CONSTRAINT fk_medicine_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";

    //Create RELIEF_RECORD_TABLE
    String RELIEF_RECORD_CREATE =
            "CREATE TABLE relief_record(\n" +
                    " \n" +
                    "  relief_id INT,\n" +
                    "  record_id INT,\n" +
                    "  effective TEXT NOT NULL DEFAULT 'f',\n" +
                    "  \n" +
                    "  PRIMARY KEY (relief_id,record_id),\n" +
                    "  CONSTRAINT fk_relief_record_relief_id FOREIGN KEY (relief_id) REFERENCES relief(relief_id),\n" +
                    "  CONSTRAINT fk_relief_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";

    //Create SYMPTOM_RECORD_TABLE
    String SYMPTOM_RECORD_CREATE =
            "CREATE TABLE symptom_record(\n" +
                    " \n" +
                    "  symptom_id INT,\n" +
                    "  record_id INT,\n" +
                    "  \n" +
                    "  PRIMARY KEY (symptom_id,record_id),\n" +
                    "  CONSTRAINT fk_symptom_record_symptom_id FOREIGN KEY (symptom_id) REFERENCES symptom(symptom_id),\n" +
                    "  CONSTRAINT fk_symptom_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";

    //Create TRIGGER_RECORD_TABLE
    String TRIGGER_RECORD_CREATE =
            "CREATE TABLE trigger_record(\n" +
                    "  \n" +
                    "  trigger_id INT,\n" +
                    "  record_id INT,\n" +
                    "  \n" +
                    "  PRIMARY KEY (trigger_id,record_id),\n" +
                    "  CONSTRAINT fk_trigger_record_trigger_id FOREIGN KEY (trigger_id) REFERENCES migraine_trigger(trigger_id),\n" +
                    "  CONSTRAINT fk_trigger_record_record_id FOREIGN KEY (record_id) REFERENCES migraine_record(record_id) ON DELETE CASCADE ON UPDATE CASCADE \n" +
                    "  \n" +
                    ");";
    //endregion
    //
    //region Insert table syntax
    //-------------------

    //Insert ACTIVITY_TABLE
    String ACTIVITY_INSERT =
            "INSERT INTO activity VALUES \n" +
                    "(1,'Could not use device',0),\n" +
                    "(2,'Woke during sleep',1),\n" +
                    "(3,'Could not fall asleep',2),\n" +
                    "(4,'Missed social activity',3),\n" +
                    "(5,'Slow to work',4),\n" +
                    "(6,'Not affected',5)\n" +
                    ";";

    //Insert BODY_AREA_TABLE
    String BODY_AREA_INSERT =
            "INSERT INTO body_area VALUES \n" +
                    "(1,'Right temple'),\n" +
                    "(2,'Left temple'),\n" +
                    "(3,'Right neck'),\n" +
                    "(4,'Left neck'),\n" +
                    "(5,'Right cheek'),\n" +
                    "(6,'Left cheek'),\n" +
                    "(7,'Right jaw'),\n" +
                    "(8,'Left jaw'),\n" +
                    "(9,'Right eye'),\n" +
                    "(10,'Left eye'),\n" +
                    "(11,'Between eyes'),\n" +
                    "(12,'Right front head'),\n" +
                    "(13,'Left front head'),\n" +
                    "(14,'Teeth'),\n" +
                    "(15,'Back of head-Lower right'),\n" +
                    "(16,'Back of head-Lower left'),\n" +
                    "(17,'Back of head-Upper right'),\n" +
                    "(18,'Back of head-Upper left'),\n" +
                    "(19,'Back of head-Right neck'),\n" +
                    "(20,'Back of head-Left neck')\n" +
                    ";";

    //Insert LOCATION_TABLE
    String LOCATION_INSERT =
            "INSERT INTO location VALUES \n" +
                    "(1,'Out'),\n" +
                    "(2,'Home'),\n" +
                    "(3,'Bed'),\n" +
                    "(4,'In transit'),\n" +
                    "(5,'School'),\n" +
                    "(6,'Work')\n" +
                    ";";

    //Insert MEDICINE_TABLE
    String MEDICINE_INSERT =
            "INSERT INTO medicine VALUES \n" +
                    "(1,'Advil',0),\n" +
                    "(2,'Maxalt',1),\n" +
                    "(3,'Paracetamol',2),\n" +
                    "(4,'Excedrin',3),\n" +
                    "(5,'Tylenol',4),\n" +
                    "(6,'Ibuprofen',5),\n" +
                    "(7,'Topiramate',6),\n" +
                    "(8,'Propranolol',7),\n" +
                    "(9,'Zomig',8),\n" +
                    "(10,'Sumatriptan',9)\n" +
                    ";";

    //Insert RELIEF_TABLE
    String RELIEF_INSERT =
            "INSERT INTO relief VALUES \n" +
                    "(1,'Heat pad',0),\n" +
                    "(2,'Drink water',1),\n" +
                    "(3,'Cold shower',2),\n" +
                    "(4,'Hot shower',3),\n" +
                    "(5,'Caffeine',4),\n" +
                    "(6,'Dark room rest',5),\n" +
                    "(7,'Ice packs',6),\n" +
                    "(8,'Stray indoor',7),\n" +
                    "(9,'Meditate',8),\n" +
                    "(10,'Sleep',9)\n" +
                    ";";

    //Insert SYMPTOM_TABLE
    String SYMPTOM_INSERT =
            "INSERT INTO symptom VALUES \n" +
                    "(1,'Confused',0),\n" +
                    "(2,'Diarrhea',1),\n" +
                    "(3,'Blurred vision',2),\n" +
                    "(4,'Fatigue',3),\n" +
                    "(5,'Ringing in ears',4),\n" +
                    "(6,'Sensitive to smell',5),\n" +
                    "(7,'Sensitive to noise',6),\n" +
                    "(8,'Sensitive to light',7),\n" +
                    "(9,'Nasal congestion',8),\n" +
                    "(10,'Neck pain',9),\n" +
                    "(11,'Vomiting',10),\n" +
                    "(12,'Nausea',11),\n" +
                    "(13,'No Symptoms',12)\n" +
                    ";";

    //Insert TRIGGER_TABLE
    String TRIGGER_INSERT =
            "INSERT INTO migraine_trigger VALUES \n" +
                    "(1,'Irregular sleep',0),\n" +
                    "(2,'Headache',1),\n" +
                    "(3,'Sinus',2),\n" +
                    "(4,'Allergy',3),\n" +
                    "(5,'Odd smell',4),\n" +
                    "(6,'Bright light',5),\n" +
                    "(7,'Weather',6),\n" +
                    "(8,'Dehydration',7),\n" +
                    "(9,'Processed food',8),\n" +
                    "(10,'Caffeine',9),\n" +
                    "(11,'Physical exertion',10),\n" +
                    "(12,'Stress',11)\n" +
                    ";";

    //endregion
    //
    //region All table names
    String[] ALL_TABLES = {
            ACTIVITY_RECORD_TABLE, BODY_AREA_RECORD_TABLE, MEDICINE_RECORD_TABLE, RELIEF_RECORD_TABLE, SYMPTOM_RECORD_TABLE, TRIGGER_RECORD_TABLE,
            ACTIVITY_TABLE, BODY_AREA_TABLE, MEDICINE_TABLE, RELIEF_TABLE, SYMPTOM_TABLE, TRIGGER_TABLE, WEATHER_DATA_TABLE,
            RECORD_TABLE,
            LOCATION_TABLE};
    //endregion
}
